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

(deftest basically-valid-passport?-test
  (is (= true (basically-valid-passport? valid-test-passport)))
  (is (= false (basically-valid-passport? invalid-test-passport)))
  (is (= true (basically-valid-passport? another-valid-test-passport)))
  (is (= false (basically-valid-passport? another-invalid-test-passport)))
  )

(deftest get-data-items-test
  (is (= valid-test-passport-data-items (get-data-items valid-test-passport)))
  )

(deftest get-fields-test
  (is (= valid-test-passport-fields (get-fields valid-test-passport-data-items)))
  )

(deftest has-required-fields?-test
  (is (= true (has-required-fields? valid-test-passport)))
  (is (= false (has-required-fields? invalid-test-passport)))
  )

(def invalid-full-test-passports
  {
   1 "iyr:2019\nhcl:#602927 eyr:1967 hgt:170cm\necl:grn pid:012533040 byr:1946"
   2 "hcl:dab227 iyr:2012\necl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277"
   3 "hgt:59cm ecl:zzz\neyr:2038 hcl:74454a iyr:2023\npid:3556412378 byr:2007"
   })

(def invalid-full-test-passport
  "eyr:1972 cid:100\nhcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926")

(def valid-full-test-passports
  {
   1 "eyr:2029 ecl:blu cid:129 byr:1989\niyr:2014 pid:896056539 hcl:#a97842 hgt:165cm"
   2 "hcl:#888785\nhgt:164cm byr:2001 iyr:2015 cid:88\npid:545766238 ecl:hzl\neyr:2022"
   3 "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719"
   }
  )

(def valid-full-test-passport
  "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980\nhcl:#623a2f")

(deftest fully-valid-passport?-test
  (is (= true (fully-valid-passport? valid-full-test-passport)))
  (is (= true (fully-valid-passport? (get valid-full-test-passports 1))))
  (is (= true (fully-valid-passport? (get valid-full-test-passports 2))))
  (is (= true (fully-valid-passport? (get valid-full-test-passports 3))))

  (is (= false (fully-valid-passport? invalid-full-test-passport)))
  (is (= false (fully-valid-passport? (get invalid-full-test-passports 1))))
  (is (= false (fully-valid-passport? (get invalid-full-test-passports 2))))
  (is (= false (fully-valid-passport? (get invalid-full-test-passports 3))))
  )

(deftest valid-birth-year?-test
  (is (= true (valid-birth-year? "byr:2002")))
  (is (= false (valid-birth-year? "byr:2003")))
  )

(deftest valid-issue-year?-test
  (is (= true (valid-issue-year? "iyr:2012")))
  (is (= false (valid-issue-year? "iyr:2022")))
  )

(deftest valid-expiration-year?-test
  (is (= true (valid-expiration-year? "iyr:2020")))
  (is (= false (valid-expiration-year? "iyr:2032")))
  )

(deftest valid-eye-color?-test
  (is (= true (valid-eye-color? "ecl:amb")))
  (is (= false (valid-eye-color? "ecl:foo")))
  )

;hgt (Height) - a number followed by either cm or in:
;If cm, the number must be at least 150 and at most 193.
;If in, the number must be at least 59 and at most 76.
(deftest valid-height?-test
  (is (= true (valid-height? "hgt:170cm")))
  (is (= false (valid-height? "hgt:194cm")))
  (is (= true (valid-height? "hgt:70in")))
  (is (= false (valid-height? "hgt:77in")))
  (is (= false (valid-height? "hgt:70xy")))
  )

;hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
(deftest valid-hair-color?-test
  (is (= true (valid-hair-color? "hcl:#123456")))
  (is (= true (valid-hair-color? "hcl:#12ac56")))
  (is (= false (valid-hair-color? "hcl:#12gh56")))
  )

;pid (Passport ID) - a nine-digit number, including leading zeroes
(deftest valid-passport-id?-test
  (is (= true (valid-passport-id? "pid:123456789")))
  (is (= true (valid-passport-id? "pid:000000000")))
  (is (= false (valid-passport-id? "pid:12345678")))
  (is (= false (valid-passport-id? "pid:a23456789")))
  )
