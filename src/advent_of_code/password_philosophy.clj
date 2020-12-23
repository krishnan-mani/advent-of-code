(ns advent-of-code.password-philosophy
  (:require [clojure.string :as str]))

(defn first-before-delimiter [_string _delimiter]
  (first (str/split _string (re-pattern _delimiter))))

(defn last-after-delimiter [_string _delimiter]
  (last (str/split _string (re-pattern _delimiter))))

(defn min-occurrences [_string]
  (read-string (first-before-delimiter _string "-")))

(defn max-occurrences [_string]
  (read-string (last-after-delimiter (first-before-delimiter _string " ") "-")))

(defn char-to-check [_string]
  (last-after-delimiter (first-before-delimiter _string ":") " "))

(defn get-password [_string]
  (str/trim (last-after-delimiter _string ":")))

(defn read-password-line [_line]
  {:min      (min-occurrences _line)
   :max      (max-occurrences _line)
   :chr      (char-to-check _line)
   :password (get-password _line)}
  )

(defn read-passwords-from-file [filename]
  (map read-password-line (str/split-lines (slurp filename))))

(defn occurrences [_string chr]
  (loop [idx (dec (count _string))
         ans 0]
    (if-not (>= idx 0)
      (+ 0 ans)
      (recur
        (dec idx)
        (if (= chr (subs _string idx (inc idx))) (inc ans) (+ 0 ans))))))

(defn valid? [password min max chr]
  (let [occurrences (occurrences password chr)]
    (and
      (>= occurrences min)
      (<= occurrences max))
    ))

(defn valid-password-line? [_password-line]
  (let [password (:password _password-line)
        min (:min _password-line)
        max (:max _password-line)
        chr (:chr _password-line)]
    (valid? password min max chr)))

(def password-lines (read-passwords-from-file "test/resources/password_policies.txt"))
(defn -main [& args]
  (println "Number of valid passwords:"
           (count (filter identity (map valid-password-line? password-lines))
                  ))
  )