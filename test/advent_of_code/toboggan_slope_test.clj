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

(deftest landed-on-beyond-test
  (is (= "." (landed-on ".#" 2)))
  (is (= "#" (landed-on ".#" 3)))
  (is (= "." (landed-on "...#..#.##..#.#......#.#.#.#..#" 31)))
  (is (= "." (landed-on "...#..#.##..#.#......#.#.#.#..#" 32)))
  (is (= "." (landed-on "...#..#.##..#.#......#.#.#.#..#" 33)))
  (is (= "#" (landed-on "...#..#.##..#.#......#.#.#.#..#" 34)))
  )

(deftest jump-test
  (is (= {:y 1 :x 3} (right-3-down-1 0)))
  (is (= {:y 2 :x 6} (right-3-down-1 1)))
  )

(deftest bin-truth-test
  (is (= 1 (bin-truth true)))
  (is (= 0 (bin-truth false)))
  )

(def small-grid-test {:1 [
                          "........."
                          "...#....."
                          ]
                      :2 [
                          "........."
                          "...#.#..#"
                          "..#..##.#"
                          ]
                      :3 [
                          "#########"
                          "#########"
                          "#########"
                          "#########"
                          ]}
  )

(deftest navigate-test
  (is (= 1 (count-trees-for-slope right-3-down-1 (:1 small-grid-test))))
  (is (= 2 (count-trees-for-slope right-3-down-1 (:2 small-grid-test))))
  (is (= 3 (count-trees-for-slope right-3-down-1 (:3 small-grid-test))))
  )
