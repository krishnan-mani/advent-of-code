(ns advent-of-code.passport-processing
  (:require [clojure.string :as str]))



(defn get-data-items [_passport]
  (str/split _passport #"[ \n]")
  )

(defn get-fields [_data-item]
  )

(defn has-required-fields? [_passport]
  (get-fields (get-data-items _passport))
  )

(defn valid-passport? [_passport]
  (has-required-fields? _passport))
