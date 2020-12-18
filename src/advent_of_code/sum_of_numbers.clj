(ns advent-of-code.sum-of-numbers
  (:require [clojure.string :as str])
  )

(defn read-numbers-from-file [filename]
  (map read-string (str/split (slurp filename) #"\n")))

(def numbers (read-numbers-from-file "test/resources/sum_to_2020.txt"))

(defn lazy-contains?
  [coll key]
  (boolean (some #(= % key) coll)))

(defn -main
  [& args]
  (print (first (filter #(lazy-contains? numbers (- 2020 %)) numbers))))














