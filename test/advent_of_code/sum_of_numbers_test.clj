(ns advent-of-code.sum-of-numbers-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.sum-of-numbers :refer :all]))

(deftest find-two-numbers-acceptance-test
  (is (= [911 1109] (find-two-numbers sum numbers)))
  (is (= 1010299 (reduce * (find-two-numbers sum numbers))))
  )

(deftest find-three-numbers-acceptance-test
  (is (= [1488 60 472] (find-three-numbers sum numbers)))
  (is (= 42140160 (reduce * (find-three-numbers sum numbers))))
  )
