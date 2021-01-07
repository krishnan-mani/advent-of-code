(ns advent-of-code.customs-forms
  (:require [clojure.string :as str])
  (:require [clojure.set :as set]))

(defn newline? [chr]
  (= chr \newline))


(defn count-common [group-responses]
  (let [responses (map set (str/split-lines group-responses))]
    (count (apply set/intersection responses)))
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

(defn sum-count-yes [responses]
  (reduce + (map count-yes responses)))

(defn sum-count-common [responses]
  (reduce + (map count-common responses)))

(def customs-forms-responses (read-customs-forms-responses "test/resources/customs_forms_responses.txt"))
(defn -main [& args]
  (println "Sum of counts of customs forms group responses:" (sum-count-yes customs-forms-responses))
  (println "Sum of common responses within groups:" (sum-count-common customs-forms-responses))
  )