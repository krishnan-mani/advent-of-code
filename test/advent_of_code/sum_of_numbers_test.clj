(ns advent-of-code.sum-of-numbers-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.sum-of-numbers :refer :all]))

(deftest find-two-numbers-test
  (is (= [911 1109] (find-two-numbers sum numbers)))
  )
