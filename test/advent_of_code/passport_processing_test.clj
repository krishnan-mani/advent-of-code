(ns advent-of-code.passport-processing-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.passport-processing :refer :all]))

(def invalid-test-passport
  "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\nhcl:#cfa07d byr:1929")

(def another-invalid-test-passport
  "hcl:#cfa07d eyr:2025 pid:166559648\niyr:2011 ecl:brn hgt:59in")

(def valid-test-passport
  "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm")

(def another-valid-test-passport
  "hcl:#ae17e1 iyr:2013\neyr:2024\necl:brn pid:760753108 byr:1931\nhgt:179cm")

(def valid-test-passport-data-items
  ["ecl:gry" "pid:860033327" "eyr:2020" "hcl:#fffffd" "byr:1937" "iyr:2017" "cid:147" "hgt:183cm"])

(def valid-test-passport-fields
  ["ecl" "pid" "eyr" "hcl" "byr" "iyr" "cid" "hgt"])

(deftest valid-passport?-test
  (is (= true (valid-passport? valid-test-passport)))
  (is (= false (valid-passport? invalid-test-passport)))
  (is (= true (valid-passport? another-valid-test-passport)))
  (is (= false (valid-passport? another-invalid-test-passport)))
  )

(deftest get-data-items-test
  (is (= valid-test-passport-data-items (get-data-items valid-test-passport)))
  )

(deftest get-fields-test
  (is (= valid-test-passport-fields (get-fields valid-test-passport-data-items)))
  )

(deftest has-required-fields?-test
  (is (= true (has-required-fields? valid-test-passport)))
  )
