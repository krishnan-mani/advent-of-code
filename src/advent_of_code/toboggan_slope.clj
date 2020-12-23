(ns advent-of-code.toboggan-slope
  (:require [clojure.string :as str]))

(defn read-basic-grid [filename]
  (str/split-lines (slurp filename)))

(defn landed-on [line idx]
  (let [adjusted-idx (mod idx (count line))]
    (subs line adjusted-idx (inc adjusted-idx))
    )
  )

(defn landed_on_tree? [line idx]
  (= "#" (landed-on line idx))
  )

(defn jump [y]
  (let [next-y (inc y)]
    {:y next-y :x (* 3 next-y)})
  )

(defn bin-truth [_boolean]
  (if (identity _boolean) 1 0))

(defn navigate [basic_grid]
  (let [_length (dec (count basic_grid))]
    (loop [idx 0
           trees 0
           {:keys [y x]} (jump idx)]
      (if-not (< idx _length)
        trees
        (recur (inc idx)
               (+ trees (bin-truth (landed_on_tree? (nth basic_grid y) x)))
               (jump (inc idx))))
      ))
  )

(defn -main [& args]
  (println "Number of trees encountered:" (navigate (read-basic-grid "test/resources/toboggan_slope.txt")))
  )