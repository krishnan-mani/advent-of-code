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

(deftest bisect-by-identifier-test
  (is (= [0 63] (bisect-by-identifier "F" [0 127])))
  (is (= [32 63] (bisect-by-identifier "FB" [0 127])))
  (is (= [32 47] (bisect-by-identifier "FBF" [0 127])))
  (is (= [44 44] (bisect-by-identifier "FBFBBFF" [0 127])))
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

(deftest bisect-and-choose-range-test
  (is (= [0 63] (bisect-and-choose-range [0 127] "F")))
  (is (= [0 63] (bisect-and-choose-range [0 127] "L")))
  (is (= [64 127] (bisect-and-choose-range [0 127] "B")))
  (is (= [64 127] (bisect-and-choose-range [0 127] "R")))
  )

(deftest get-rows-test
  (is (= 44 (get-rows "FBFBBFFRLR")))
  )

(deftest get-columns-test
  (is (= 5 (get-columns "FBFBBFFRLR")))
  )

(deftest seat-id-test
  (is (= 357 (seat-id "FBFBBFFRLR")))
  )

(deftest find-missing-num-test
  (is (= 3 (find-missing-num [1 2 4])))
  (is (= 6 (find-missing-num [2 3 4 5 7])))
  (is (= 5 (find-missing-num [7 4 6])))
  )
