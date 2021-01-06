(ns advent-of-code.password-philosophy
  (:require [clojure.string :as str]))

(defn first-before-delimiter [_string _delimiter]
  (let [pattern (re-pattern _delimiter)]
    (-> _string
        (str/split pattern)
        (first))))

(defn last-after-delimiter [_string _delimiter]
  (let [pattern (re-pattern _delimiter)]
    (-> _string
        (str/split pattern)
        (last))))

(defn min-occurrences [_string]
  (-> _string
      (first-before-delimiter "-")
      (read-string)))

(defn max-occurrences [_string]
  (-> _string
      (first-before-delimiter " ")
      (last-after-delimiter "-")
      (read-string)))

(defn char-to-check [_string]
  (-> _string
      (first-before-delimiter ":")
      (last-after-delimiter " "))
  )

(defn get-password [_string]
  (-> _string
      (last-after-delimiter ":")
      (str/trim))
  )

(defn read-password-line-with-occurrences [_line]
  {:min      (min-occurrences _line)
   :max      (max-occurrences _line)
   :chr      (char-to-check _line)
   :password (get-password _line)})

(defn read-passwords-from-file [f filename]
  (map f (str/split-lines (slurp filename))))

(defn occurrences [_string chr]
  (let [length (count _string)]
    (if (zero? length)
      0
      (if (= 1 length)
        (if (= chr (subs _string 0 1)) 1 0)
        (+ (occurrences (subs _string 0 1) chr) (occurrences (subs _string 1) chr)))
      )
    )
  )

(defn valid-by-occurrence? [password min max chr]
  (let [occurrences (occurrences password chr)]
    (and
      (>= occurrences min)
      (<= occurrences max))
    ))

(defn valid-password-line-by-occurrence? [_password-line]
  (let [password (:password _password-line)
        min (:min _password-line)
        max (:max _password-line)
        chr (:chr _password-line)]
    (valid-by-occurrence? password min max chr)))

(defn get-positions [_string chr]
  (let [_length (count _string)]
    (loop [idx 0
           positions '()]
      (if-not (< idx _length)
        (sort (flatten positions))
        (recur (inc idx) (if (= chr (subs _string idx (inc idx)))
                           (cons (inc idx) positions)
                           (list positions))))
      )))

(defn occurs? [coll elm]
  (if (some #{elm} coll) 1 0))

(defn valid-by-position? [password first-position last-position chr]
  (let [positions (get-positions password chr)]
    (= 1 (+ (occurs? positions first-position) (occurs? positions last-position)))))

(defn read-password-line-with-positions [_line]
  {:first    (min-occurrences _line)
   :last     (max-occurrences _line)
   :chr      (char-to-check _line)
   :password (get-password _line)})

(defn valid-password-line-by-position? [_password_line]
  (let [password (:password _password_line)
        first (:first _password_line)
        last (:last _password_line)
        chr (:chr _password_line)]
    (valid-by-position? password first last chr)))

(defn count-true [pred coll]
  (count (filter identity (map pred coll))))

(defn -main [& args]
  (println "Number of valid passwords (old job):"
           (let [password-lines-by-occurrence (read-passwords-from-file read-password-line-with-occurrences "test/resources/password_policies.txt")]
             (count-true valid-password-line-by-occurrence? password-lines-by-occurrence)
             ))

  (println "Number of valid passwords (by position):"
           (let [password-lines-by-position (read-passwords-from-file read-password-line-with-positions "test/resources/password_policies.txt")]
             (count-true valid-password-line-by-position? password-lines-by-position)
             ))
  )