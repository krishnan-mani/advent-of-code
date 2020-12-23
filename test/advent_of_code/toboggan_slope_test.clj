(ns advent-of-code.toboggan-slope-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.toboggan-slope :refer :all]))

(deftest landed-on-tree?-test
  (is (= true (landed_on_tree? ".#.#" 3)))
  )

(deftest landed-on-test
  (is (= "." (landed-on ".#" 0)))
  (is (= "#" (landed-on ".#" 1)))
  )

(deftest jump-test
  (is (= {:y 1 :x 2}) (jump 0))
  (is (= {:y 2 :x 5}) (jump 1))
  )

(deftest binary-truth-test
  (is (= 1 (binary-truth true)))
  (is (= 0 (binary-truth false)))
  )

(def small-grid-test {:2 [
                          "#.#.#.#.#"
                          "..#..#..#"
                          "..#..#..#"
                          ]
                      :3 [
                          "#########"
                          "#########"
                          "#########"
                          "#########"
                          ]}
  )

(deftest navigate-test
  (is (= 2 (navigate (:2 small-grid-test))))
  (is (= 3 (navigate (:3 small-grid-test))))
  )
