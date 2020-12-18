(ns advent-of-code.sum-of-numbers
  (:require [clojure.string :as str]))

(defn read-numbers-from-file [filename]
  (map read-string (str/split (slurp filename) #"\n")))

(defn lazy-contains?
  [coll key]
  (boolean (some #(= % key) coll)))

(def sum 2020)
(def filename "test/resources/sum_to_2020.txt")
(def numbers (read-numbers-from-file filename))
(defn -main
  [& args]
  (def first-value (first (filter #(lazy-contains? numbers (- sum %)) numbers)))
  (def second-value (- sum first-value))
  (def solution [first-value second-value])
  (println (format "%d, %d" first-value second-value)))














