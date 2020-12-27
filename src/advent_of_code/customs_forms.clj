(ns advent-of-code.customs-forms
  (:require [clojure.string :as str]))

(defn newline? [chr]
  (= chr \newline))

(defn count-yes [_responses]
  (let [_string (remove newline? _responses)
        length (count _string)]
    (loop [idx 0
           unique-responses #{}]
      (if-not (< idx length)
        (count unique-responses)
        (do (println _string unique-responses)
            (recur (inc idx) (set (cons (nth _string idx) unique-responses)))))
      )
    )
  )
