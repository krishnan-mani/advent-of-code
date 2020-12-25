(ns advent-of-code.binary-boarding-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.binary-boarding :refer :all]))

(deftest get-row-test
  (is (= 44 (get-row "FBFBBFF")))
  )

(deftest bisect-test
  (is (= 63 (bisect [0 127])))
  )
