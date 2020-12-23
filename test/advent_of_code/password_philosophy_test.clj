(ns advent-of-code.password-philosophy-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.password-philosophy :refer :all]))


(deftest valid?-test
  (is (= 0 (occurences "" "a")))
  (is (= 0 (occurences "b" "a")))
  (is (= 1 (occurences "a" "a")))
  (is (= 2 (occurences "caba" "a")))
  )

(deftest occurences-test
  (is (= false (valid? "foo" 1 2 "a")))
  (is (= true (valid? "foo" 1 2 "o")))
  )

(deftest min-occurences-test
  (is (= 1 (min-occurences "1-13 r: gqdrspndrpsrjfjx")))
  )

(deftest max-occurences-test
  (is (= 13 (max-occurences "1-13 r: gqdrspndrpsrjfjx")))
  )

(deftest char-to-test-test
  (is (= "r" (char-to-test "1-13 r: gqdrspndrpsrjfjx")))
  )

(deftest get-password-test
  (is (= "gqdrspndrpsrjfjx" (get-password "1-13 r: gqdrspndrpsrjfjx")))
  )

(deftest read-password-line-test
  (is (= {:min 1, :max 13, :chr "r", :password "gqdrspndrpsrjfjx"}
         (read-password-line "1-13 r: gqdrspndrpsrjfjx")))
  )

(deftest valid-password-line?-test
  (is (= true (valid-password-line? {:min 1, :max 13, :chr "r", :password "gqdrspndrpsrjfjx"})))
  )
