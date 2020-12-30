(ns advent-of-code.sum-of-numbers-test
  (:require [clojure.test :refer :all])
  (:require [clojure.string :as str])
  (:require [advent-of-code.sum-of-numbers :refer :all]))

(deftest find-two-numbers-acceptance-test
  (is (= [911 1109] (find-two-numbers sum numbers)))
  (is (= 1010299 (reduce * (find-two-numbers sum numbers))))
  )

(deftest find-three-numbers-acceptance-test
  (is (= [1488 60 472] (find-three-numbers sum numbers)))
  (is (= 42140160 (reduce * (find-three-numbers sum numbers))))
  )

(def my-lazy-seq-ints (map read-string (str/split "1\n2\n3" #"\n")))
(deftest lazy-contains?-test
  (is (= true (lazy-contains? my-lazy-seq-ints 1)))
  (is (= false (lazy-contains? my-lazy-seq-ints 0)))
  )
