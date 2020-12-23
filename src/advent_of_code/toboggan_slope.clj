(ns advent-of-code.toboggan-slope
  (:require [clojure.string :as str]))

(defn read-basic-grid [filename]
  (str/split-lines (slurp filename)))

(defn landed-on [line idx]
  (subs line idx (inc idx))
  )

(defn landed_on_tree? [line idx]
  (= "#" (landed-on line idx))
  )

(defn jump [y]
  {:y (inc y) :x (dec (* 3 (inc y)))}
  )

(defn binary-truth [_boolean]
  (if (identity _boolean) 1 0))

(defn navigate [basic_grid]
  (let [_length (dec (count basic_grid))]
    (loop [idx 0
           trees 0
           {y :y x :x} (jump idx)]
      (if-not (< idx _length)
        trees
        (recur (inc idx) (+ trees (binary-truth (landed_on_tree? (nth basic_grid y) x))) (jump (inc idx))))
      ))
  )

(defn -main [& args]
  (println "Basic grid: " (read-basic-grid "test/resources/toboggan_slope.txt")))