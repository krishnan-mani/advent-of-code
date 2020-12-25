(ns advent-of-code.binary-boarding
  (:require [clojure.string :as str]))

(defn bisect [range]
  (let [[lower upper] range
        half-difference (unchecked-divide-int (- upper lower) 2)]
    (+ lower half-difference)
    )
  )

(defn bisect-and-upper [range]
  (let [mid-point (bisect range)
        [_ upper] range]
    [(inc mid-point) upper]
    )
  )

(defn bisect-and-lower [range]
  (let [mid-point (bisect range)
        [lower _] range]
    [lower mid-point]
    ))

(def choose-range {"F" :lower "B" :upper "L" :lower "R" :upper})

(defn bisect-and-choose-range [range back-or-front]
  (let [upper-or-lower (get choose-range back-or-front)]
    (if (= :upper upper-or-lower)
      (bisect-and-upper range)
      (bisect-and-lower range))
    ))



(defn bisect-by-identifier [_string start-range]
  (loop [idx 0
         range start-range]
    (if-not (< idx (count _string))
      range
      (recur
        (inc idx)
        (bisect-and-choose-range range (str (nth _string idx))))))
  )

(def rows-range [0 127])
(defn get-rows [_boarding-pass]
  (let [rows-string (subs _boarding-pass 0 7)]
    (first (bisect-by-identifier rows-string rows-range)))
  )

(def columns-range [0 7])
(defn get-columns [_boarding-pass]
  (let [cols-string (subs _boarding-pass 7)]
    (first (bisect-by-identifier cols-string columns-range)))
  )

(defn seat-id [_boarding-pass]
  (+ (* 8 (get-rows _boarding-pass)) (get-columns _boarding-pass)))
