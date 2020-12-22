(ns advent-of-code.password-philosophy
  (:require [clojure.string :as str])
  (:require [clojure.test :refer :all]))

(defn read-passwords-from-file [filename]
  (str/split-lines (slurp filename)))

(defn min-occurences
  [_string]
  (read-string (subs _string 0 1)))

(defn max-occurences
  [_string]
  (read-string (last (str/split (first (str/split _string #" ")) #"-"))))

(defn char-to-test
  [_string]
  (last (str/split (first (str/split _string #":")) #" "))
  )

(defn get-password
  [_string]
  (subs (last (str/split _string #":")) 1)
  )

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


