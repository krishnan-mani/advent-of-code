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

(defn get-rows [_string]
  (loop [idx 0
         range [0 127]
         ]
    (if-not (< idx (count _string))
      range
      (recur
        (inc idx)
        (if (= "B" (str (nth _string idx)))
          (bisect-and-upper range)
          (bisect-and-lower range))
        )))
  )
