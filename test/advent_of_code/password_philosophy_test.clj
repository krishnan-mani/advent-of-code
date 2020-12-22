(ns advent-of-code.password-philosophy-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.password-philosophy :refer :all]))

(testing "occurences"
  (is (= 0 (occurences "" "a")))
  (is (= 0 (occurences "b" "a")))
  (is (= 1 (occurences "a" "a")))
  (is (= 2 (occurences "caba" "a")))
  )
