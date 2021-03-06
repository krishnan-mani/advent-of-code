(ns advent-of-code.toboggan-slope-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.toboggan-slope :refer :all]))

(deftest acceptance-tests
  (is (= 176 (count-trees-for-example-slope)))
  (is (= '(85 176 96 87 47) tree-counts))
  )

(deftest landed-on-tree?-test
  (is (= true (landed_on_tree? ".#.#" 3)))
  (is (= false (landed_on_tree? ".#.#" 2)))
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

(deftest next-position-test
  (is (= {:y 1 :x 1} (next-position {:y 0 :x 0} 1 1)))
  (is (= {:y 2 :x 1} (next-position {:y 0 :x 0} 1 2)))
  (is (= {:y 4 :x 2} (next-position {:y 2 :x 1} 1 2)))
  (is (= {:y 4 :x 3} (next-position {:y 2 :x 1} 2 2)))
  )

(deftest jump-test
  (is (= {:y 1 :x 3} (right-3-down-1 {:y 0 :x 0})))
  (is (= {:y 2 :x 6} (right-3-down-1 {:y 1 :x 3})))
  )

(deftest bin-truth-test
  (is (= 1 (bin-truth true)))
  (is (= 0 (bin-truth false)))
  )

(def small-grid-test {:0 [
                          "........."
                          "........."
                          "........."
                          "........."
                          ]
                      :1 [
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

(deftest count-trees-for-slope-test
  (is (= 0 (count-trees-for-slope right-3-down-1 (:0 small-grid-test))))
  (is (= 1 (count-trees-for-slope right-3-down-1 (:1 small-grid-test))))
  (is (= 2 (count-trees-for-slope right-3-down-1 (:2 small-grid-test))))
  (is (= 3 (count-trees-for-slope right-3-down-1 (:3 small-grid-test))))
  )
