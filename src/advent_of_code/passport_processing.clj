(ns advent-of-code.passport-processing
  (:require [clojure.string :as str]))

(def optional-fields #{"cid"})
(def required-fields #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})

(defn get-data-items [_passport]
  (str/split _passport #"[ \n]")
  )

(defn get-fields [_passport-data]
  (map #(first (str/split % #":")) _passport-data)
  )

(defn has-required-fields? [_passport]
  (every? (set (remove optional-fields (get-fields (get-data-items _passport)))) required-fields)
  )

(defn basically-valid-passport? [_passport]
  (has-required-fields? _passport))

(defn valid-birth-year? [_string]
  (let [birth-year (read-string (last (str/split _string #":")))]
    (and (<= 1920 birth-year)
         (<= birth-year 2002)))
  )

(defn valid-issue-year? [_string]
  true)

(defn valid-expiration-year? [_string]
  true)

(defn valid-height? [_string]
  true)

(defn valid-hair-color? [_string]
  true)

(defn valid-eye-color? [_string]
  true)

(defn valid-passport-id? [_string]
  true)

(defn valid-country-id? [_string]
  true)

(def validations-by-field
  {"byr" valid-birth-year?
   "iyr" valid-issue-year?
   "eyr" valid-expiration-year?
   "hgt" valid-height?
   "hcl" valid-hair-color?
   "ecl" valid-eye-color?
   "pid" valid-passport-id?
   "cid" valid-country-id?
   })

(defn valid-data-items? [_passport]
  (every? #(get validations-by-field (first (str/split % #":"))) (get-data-items _passport))
  )

(defn fully-valid-passport? [_passport]
  (and (basically-valid-passport? _passport)
       (valid-data-items? _passport)))

(defn read-passports [filename]
  (str/split (slurp filename) #"\n\n"))

(defn -main [& args]
  (println "Number of valid passports:"
           (count (filter identity (map basically-valid-passport? (read-passports "test/resources/passport_batch.txt"))))))