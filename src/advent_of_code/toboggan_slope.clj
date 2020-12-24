(ns advent-of-code.toboggan-slope
  (:require [clojure.string :as str]))

(defn read-basic-grid [filename]
  (str/split-lines (slurp filename)))

(defn landed-on [line idx]
  (let [adjusted-idx (mod idx (count line))]
    (subs line adjusted-idx (inc adjusted-idx))
    )
  )

(defn landed_on_tree? [line idx]
  (= "#" (landed-on line idx))
  )

(defn next-position [co-ords right down]
  (let [{:keys [y x]} co-ords]
    {:y (+ y down) :x (+ x right)}
    )
  )

(defn right-3-down-1 [co-ords]
  (next-position co-ords 3 1)
  )

(defn bin-truth [_boolean]
  (if (identity _boolean) 1 0))

(defn count-trees-for-slope [slope-fn basic_grid]
  (let [_length (dec (count basic_grid))]
    (loop [idx 0
           trees 0
           {:keys [y x]} (slope-fn {:y 0 :x 0})]
      (if-not (and (< idx _length) (<= y _length))
        trees
        (recur (inc idx)
               (+ trees (bin-truth (landed_on_tree? (nth basic_grid y) x)))
               (slope-fn {:y y :x x})))
      ))
  )

(def problem-grid (read-basic-grid "test/resources/toboggan_slope.txt"))
(def all-slopes
  [
   {:right 1 :down 1}
   {:right 3 :down 1}
   {:right 5 :down 1}
   {:right 7 :down 1}
   {:right 1 :down 2}
   ])

(defn trees-for-slope [right-and-down grid]
  (let [{:keys [right down]} right-and-down
        slope-fn #(next-position % right down)]
    (count-trees-for-slope slope-fn grid)))

(defn -main [& args]
  (println "Number of trees encountered (right 3, down 1):" (count-trees-for-slope right-3-down-1 problem-grid))
  (def tree-counts
    (map #(trees-for-slope % problem-grid) all-slopes))
  (println "Tree counts for different slopes:" tree-counts)
  (println "Product of tree counts for different slopes:" (reduce * 1 tree-counts))
  )