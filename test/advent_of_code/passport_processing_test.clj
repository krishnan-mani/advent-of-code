(ns advent-of-code.passport-processing-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.passport-processing :refer :all]))


(def test-passport
  "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm"
  )

(deftest valid-passport?-test
  (is (= true (valid-passport? test-passport)))
  )

(deftest get-data-items-test
  (is (= ["ecl:gry" "pid:860033327" "eyr:2020" "hcl:#fffffd" "byr:1937" "iyr:2017" "cid:147" "hgt:183cm"] (get-data-items test-passport)))
  )
