(ns advent-of-code.password-philosophy-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.password-philosophy :refer :all]))


(deftest occurrences-test
  (is (= 0 (occurrences "" "a")))
  (is (= 0 (occurrences "b" "a")))
  (is (= 1 (occurrences "a" "a")))
  (is (= 2 (occurrences "caba" "a")))
  )

(deftest occurences-test
  (is (= false (valid-by-occurrence? "foo" 1 2 "a")))
  (is (= true (valid-by-occurrence? "foo" 1 2 "o")))
  )

(deftest min-occurences-test
  (is (= 1 (min-occurrences "1-13 r: gqdrspndrpsrjfjx")))
  )

(deftest max-occurences-test
  (is (= 13 (max-occurrences "1-13 r: gqdrspndrpsrjfjx")))
  )

(deftest char-to-check-test
  (is (= "r" (char-to-check "1-13 r: gqdrspndrpsrjfjx")))
  )

(deftest get-password-test
  (is (= "gqdrspndrpsrjfjx" (get-password "1-13 r: gqdrspndrpsrjfjx")))
  )

(deftest read-password-line-test
  (is (= {:min 1, :max 13, :chr "r", :password "gqdrspndrpsrjfjx"}
         (read-password-line-with-occurrences "1-13 r: gqdrspndrpsrjfjx")))
  )

(deftest valid-password-line?-test
  (is (= true (valid-password-line-by-occurrence? {:min 1, :max 13, :chr "r", :password "gqdrspndrpsrjfjx"})))
  )

(deftest valid?-test
  (is (= true (valid-by-occurrence? "abc" 1 2 "a")))
  (is (= false (valid-by-occurrence? "cbc" 1 2 "a")))
  (is (= true (valid-by-occurrence? "cbc" 0 2 "a")))
  (is (= false (valid-by-occurrence? "vjkxbrfwnj" 2 6 "x")))
  )

(deftest read-password-line-with-positions-test
  (is (= {:first 1 :last 13 :chr "r" :password "gqdrspndrpsrjfjx"} (read-password-line-with-positions "1-13 r: gqdrspndrpsrjfjx")))
  )

(deftest get-positions-test
  (is (= [] (get-positions "cbc" "a")))
  (is (= [1] (get-positions "abc" "a")))
  (is (= [1 2] (get-positions "aac" "a")))
  (is (= [1 3] (get-positions "aca" "a")))
  )

(deftest valid-by-position?-test
  (is (= true (valid-by-position? "abcde" 1 3 "a")))
  (is (= false (valid-by-position? "cdefg" 1 3 "b")))
  (is (= false (valid-by-position? "ccccccccc" 2 9 "b")))
  )

(deftest occurs?-test
  (is (= 1 (occurs? [33 37] 33)))
  (is (= 0 (occurs? [33 37] 39)))
  )
