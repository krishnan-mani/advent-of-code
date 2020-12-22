(ns advent-of-code.password-philosophy
  (:require [clojure.string :as str])
  (:require [clojure.test :refer :all]))

(defn read-passwords-from-file [filename]
  (str/split-lines (slurp filename)))

(defn occurences
  [_string chr]
  (loop [idx (dec (count _string))
         ans 0]
    (if-not (>= idx 0)
      (+ 0 ans)
      (recur
        (dec idx)
        (if (= chr (subs _string idx (inc idx))) (inc ans) (+ 0 ans)))
      )))

(defn valid? [password min max chr]
  (let [occurences (occurences password chr)]
    (and
      (>= occurences min)
      (<= occurences max))
    ))

(defn -main
  [& args]
  (println "foo"))


