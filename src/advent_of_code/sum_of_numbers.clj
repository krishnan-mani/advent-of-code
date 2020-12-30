(ns advent-of-code.sum-of-numbers
  (:require [clojure.string :as str]))

(defn read-numbers-from-file [filename]
  (map read-string (str/split (slurp filename) #"\n")))

(defn lazy-contains? [coll key]
  (boolean (some #{key} coll)))

(defn find-two-numbers
  [sum numbers]
  (filter #(lazy-contains? numbers (- sum %)) numbers))

(defn find-three-numbers
  [sum numbers]
  (loop [idx 0
         num (nth numbers idx)
         tail (rest numbers)]
    (if (not-empty (find-two-numbers (- sum num) tail))
      (flatten (list num (find-two-numbers (- sum num) tail)))
      (recur (inc idx) (nth numbers idx) (rest numbers)))))

(def sum 2020)
(def filename "test/resources/sum_to_2020.txt")
(def numbers (read-numbers-from-file filename))

(defn -main
  [& args]
  (println (find-two-numbers sum numbers))
  (println (find-three-numbers sum numbers))
  )
