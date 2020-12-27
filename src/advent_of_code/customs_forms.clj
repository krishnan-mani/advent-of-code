(ns advent-of-code.customs-forms
  (:require [clojure.string :as str])
  (:require [clojure.set :as set]))

(defn newline? [chr]
  (= chr \newline))

(defn count-common [_responses]
  (let [responses (str/split-lines _responses)
        _partitions (partition 2 1 responses)
        length (count _partitions)]
    (if (> (count responses) 1)
      (loop [idx 0
             common #{}]
        (if-not (< idx length)
          (count common)
          (recur (inc idx)
                 (let [_part (nth _partitions idx)]
                   (set/intersection (set (first _part)) (set (last _part)))))
          )
        )
      (if (= (count responses) 1)
        (count (set (first responses)))
        0)
      ))
  )

(defn count-yes [_responses]
  (let [_string (remove newline? _responses)
        length (count _string)]
    (loop [idx 0
           unique-responses #{}]
      (if-not (< idx length)
        (count unique-responses)
        (recur (inc idx) (conj unique-responses #{(nth _string idx)})))))
  )

(defn read-customs-forms-responses [filename]
  (str/split (slurp filename) #"\n\n"))

(def customs-forms-responses (read-customs-forms-responses "test/resources/customs_forms_responses.txt"))
(defn -main [& args]
  (println "Sum of counts of customs forms group responses:" (reduce + (map count-yes customs-forms-responses))))