(ns advent-of-code.binary-boarding-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.binary-boarding :refer :all]))


;Start by considering the whole range, rows 0 through 127.
;F means to take the lower half, keeping rows 0 through 63.
;B means to take the upper half, keeping rows 32 through 63.
;F means to take the lower half, keeping rows 32 through 47.
;B means to take the upper half, keeping rows 40 through 47.
;B keeps rows 44 through 47.
;F keeps rows 44 through 45.
;The final F keeps the lower of the two, row 44.

(deftest get-rows-test
  (is (= [0 63] (get-rows "F")))
  (is (= [32 63] (get-rows "FB")))
  (is (= [32 47] (get-rows "FBF")))
  (is (= [44 44] (get-rows "FBFBBFF")))
  )

(deftest bisect-test
  (is (= 63 (bisect [0 127])))
  (is (= 31 (bisect [0 63])))
  (is (= 47 (bisect [32 63])))
  )

(deftest bisect-and-upper-test
  (is (= [64 127] (bisect-and-upper [0 127])))
  (is (= [32 63] (bisect-and-upper [0 63])))
  (is (= [40 47] (bisect-and-upper [32 47])))
  )

(deftest bisect-and-lower-test
  (is (= [0 63] (bisect-and-lower [0 127])))
  (is (= [32 47] (bisect-and-lower [32 63])))
  )
