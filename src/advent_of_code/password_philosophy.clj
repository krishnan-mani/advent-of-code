(ns advent-of-code.password-philosophy
  (:require [clojure.string :as str]))

(defn read-passwords-from-file [filename]
  (str/split-lines (slurp filename)))

(defn min-occurences [_string]
  (read-string (subs _string 0 1)))

(defn max-occurences [_string]
  (read-string (last (str/split (first (str/split _string #" ")) #"-"))))

(defn char-to-test [_string]
  (last (str/split (first (str/split _string #":")) #" ")))

(defn get-password [_string]
  (subs (last (str/split _string #":")) 1))

(defn read-password-line [_line]
  {:min      (min-occurences _line)
   :max      (max-occurences _line)
   :chr      (char-to-test _line)
   :password (get-password _line)}
  )

(defn occurences [_string chr]
  (loop [idx (dec (count _string))
         ans 0]
    (if-not (>= idx 0)
      (+ 0 ans)
      (recur
        (dec idx)
        (if (= chr (subs _string idx (inc idx))) (inc ans) (+ 0 ans))))))

(defn valid? [password min max chr]
  (let [occurences (occurences password chr)]
    (and
      (>= occurences min)
      (<= occurences max))
    ))

(defn valid-password-line? [_line]
  (let [password (get-password _line)
        min (min-occurences _line)
        max (max-occurences _line)
        chr (char-to-test _line)]
    (valid? password min max chr)))

(def password-lines (read-passwords-from-file "test/resources/password_policies.txt"))
(defn -main [& args]
  (println "Number of valid passwords:"
           (count
             (remove #(if (valid-password-line? %) false true) password-lines)))
  )