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
  (every? required-fields (remove optional-fields (get-fields (get-data-items _passport))))
  )

(defn valid-passport? [_passport]
  (has-required-fields? _passport))
