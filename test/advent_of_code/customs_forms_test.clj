(ns advent-of-code.customs-forms-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.customs-forms :refer :all]))

(deftest count-yes-test
  (is (= 3 (count-yes "abc")))
  (is (= 3 (count-yes "a\nb\nc")))
  (is (= 3 (count-yes "ab\nac")))
  (is (= 1 (count-yes "a\na\na\na")))
  (is (= 1 (count-yes "b")))
  )

(deftest count-common-test
  (is (= 0 (count-common "")))
  (is (= 0 (count-common "a\nb\nc")))
  (is (= 3 (count-common "abc")))
  (is (= 1 (count-common "ab\nac")))
  (is (= 1 (count-common "a\na\na\na")))
  )

(deftest count-common-acc-test
  (is (= 7 (count-common "lagwjpd\ndpglwja\nqgdawplj")))
  (is (= 1 (count-common "gw\nbg\ng")))
  (is (= 0 (count-common "dlfbyscp\noruaxtg\nrowqu\ngze\ni")))
  (is (= 0 (count-common "rbwdsfcgute\noa\nlqizp\nkxvjyz")))
  )