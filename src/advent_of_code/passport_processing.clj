(ns advent-of-code.passport-processing
  (:require [clojure.string :as str]))

(def optional-fields #{"cid"})
(def required-fields #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})
(def colon-pattern #":")

(defn get-data-items [_passport]
  (str/split _passport #"[ \n]"))

(defn get-fields [_passport-data]
  (map #(-> % (str/split,,, colon-pattern) (first)) _passport-data))

(defn has-required-fields? [_passport]
  (let [passport-data-fields (get-fields (get-data-items _passport))
        optional-fields-removed (remove optional-fields passport-data-fields)]
    (every? (set optional-fields-removed) required-fields)))

(defn basically-valid-passport? [_passport]
  (has-required-fields? _passport))

(defn valid-year? [year no-earlier-than no-later-than]
  (and (<= no-earlier-than year) (<= year no-later-than)))

(defn valid-birth-year? [_string]
  (let [birth-year (-> _string (str/split,,, colon-pattern) (last) (read-string))]
    (valid-year? birth-year 1920 2002))
  )

(defn valid-issue-year? [_string]
  (let [issue-year (-> _string (str/split,,, colon-pattern) (last) (read-string))]
    (valid-year? issue-year 2010 2020))
  )

(defn valid-expiration-year? [_string]
  (let [expiration-year (-> _string (str/split,,, colon-pattern) (last) (read-string))]
    (valid-year? expiration-year 2020 2030))
  )

(defn within-range? [num no-less-than no-more-than]
  (and (<= no-less-than num)
       (<= num no-more-than)))

(defn valid-height? [_string]
  (let [height (-> _string (str/split colon-pattern) (last))
        num (read-string (re-find #"[\d]*" height))]
    (or (if (.endsWith height "cm") (within-range? num 150 193))
        (if (.endsWith height "in") (within-range? num 59 76))
        false)
    ))

(defn valid-hair-color? [_string]
  (let [hair-color (-> _string (str/split,,, colon-pattern) (last))]
    (and (= 7 (count hair-color))
         (boolean (re-find #"#[0-9a-f]{6}" hair-color)))
    ))

(def valid-eye-colors #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(defn valid-eye-color? [_string]
  (let [eye-color (-> _string (str/split,,, colon-pattern) (last))]
    (contains? valid-eye-colors eye-color)))

(defn valid-passport-id? [_string]
  (let [passport-id (last (str/split _string colon-pattern))]
    (and (= 9 (count passport-id))
         (boolean (re-find #"[\d]{9}" passport-id)))
    ))

(defn valid-country-id? [_string]
  true)

(def validations {
                  "byr" valid-birth-year?
                  "iyr" valid-issue-year?
                  "eyr" valid-expiration-year?
                  "hgt" valid-height?
                  "hcl" valid-hair-color?
                  "ecl" valid-eye-color?
                  "pid" valid-passport-id?
                  "cid" valid-country-id?
                  })

(defn validations-by-field [_string]
  (let [key (-> _string (str/split colon-pattern) (first))]
    ((validations key) _string))
  )

(defn valid-data-items? [_passport]
  (every? #(validations-by-field %) (get-data-items _passport)))

(defn fully-valid-passport? [_passport]
  (and (basically-valid-passport? _passport)
       (valid-data-items? _passport)))

(defn read-passports [filename]
  (str/split (slurp filename) #"\n\n"))

(def passports-to-validate (read-passports "test/resources/passport_batch.txt"))
(defn -main [& args]
  (println "Number of basic valid passports:"
           (count (filter identity (map basically-valid-passport? passports-to-validate))))
  (println "Number of fully valid passports:"
           (count (filter identity (map fully-valid-passport? passports-to-validate))))
  )