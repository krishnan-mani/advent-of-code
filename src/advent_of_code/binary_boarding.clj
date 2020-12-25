(ns advent-of-code.binary-boarding
  (:require [clojure.string :as str]))

(defn get-row [_string]
  )


(defn bisect [range]
  (dec (unchecked-divide-int (inc (last range)) 2))
  )