(ns advent-of-code.handy-haversacks-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.handy-haversacks :refer :all]
            [clojure.string :as str]))

(deftest my-match-test
  (is (= "range" (my-match "2-71")))
  (is (= "all-range" (my-match "*")))
  (is (= 2 (my-match "2")))
  (is (= nil (my-match "/")))
  )


(deftest read-top-bag-test
  (is (= "shiny plum" (read-top-bag "shiny plum bags contain no other bags")))
  (is (= "clear crimson" (read-top-bag "clear crimson bags contain 3 pale aqua bags, 4 plaid magenta bags, 3 dotted beige bags, 3 dotted black bags")))
  )

(deftest read-content-bag-and-count-test
  (is (= {"pale aqua" 3} (read-content-bag-and-count "3 pale aqua bags"))))

(deftest read-contents-with-count-test
  (is (= {} (read-contents-with-count "shiny plum bags contain no other bags")))
  (is (= {"pale aqua" 3 "plaid magenta" 4 "dotted beige" 5 "dotted black" 1} (read-contents-with-count "clear crimson bags contain 3 pale aqua bags, 4 plaid magenta bags, 5 dotted beige bags, 1 dotted black bags")))
  )

(deftest count-all-bags-test
  (is (= 1 (count-all-bags {"a" {}} "a")))
  (is (= 3 (count-all-bags {"a" {"b" 2}} "a")))
  (is (= 5 (count-all-bags {"a" {"b" 2 "c" 2}} "a")))
  )

(def bags-and-contents (atom {}))
(deftest total-number-of-bags-in-bag-test
  (def bag-count-descriptions (str/split-lines "shiny gold bags contain 2 dark red bags.\ndark red bags contain 2 dark orange bags.\ndark orange bags contain 2 dark yellow bags.\ndark yellow bags contain 2 dark green bags.\ndark green bags contain 2 dark blue bags.\ndark blue bags contain 2 dark violet bags.\ndark violet bags contain no other bags."))
  (is (= 7 (count bag-count-descriptions)))
  (doseq [bag-description bag-count-descriptions]
    (swap! bags-and-contents conj (read-bag-contents bag-description)))
  (is (= 64 (count-innermost-bags @bags-and-contents "shiny gold")))
  )

(deftest read-content-bag-test
  (is (= "pale aqua" (read-content-bag "3 pale aqua bags")))
  )

(deftest read-bag-contents-test
  (is (= {"bright white" {"shiny gold" 1}} (read-bag-contents "bright white bags contain 1 shiny gold bag.")))
  (is (= {"shiny plum" {}} (read-bag-contents "shiny plum bags contain no other bags.")))
  (is (= {"clear crimson" {"pale aqua" 3 "plaid magenta" 4 "dotted beige" 3 "dotted black" 3}} (read-bag-contents "clear crimson bags contain 3 pale aqua bags, 4 plaid magenta bags, 3 dotted beige bags, 3 dotted black bags.")))
  )

(deftest read-contents-test
  (is (= [] (read-contents "shiny plum bags contain no other bags")))
  (is (= ["pale aqua" "plaid magenta" "dotted beige" "dotted black"] (read-contents "clear crimson bags contain 3 pale aqua bags, 4 plaid magenta bags, 3 dotted beige bags, 3 dotted black bags")))
  )

(deftest read-bag-rule-test
  (is (= {:top "bright white" :contents ["shiny gold"]} (read-bag-rule "bright white bags contain 1 shiny gold bag.")))
  (is (= {:top "shiny plum" :contents []} (read-bag-rule "shiny plum bags contain no other bags.")))
  (is (= {:top "clear crimson" :contents ["pale aqua" "plaid magenta" "dotted beige" "dotted black"]} (read-bag-rule "clear crimson bags contain 3 pale aqua bags, 4 plaid magenta bags, 3 dotted beige bags, 3 dotted black bags.")))
  (is (= {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"]} (read-bag-rule "vibrant beige bags contain 3 drab gray bags, 4 shiny gold bags, 4 dull white bags, 3 bright lavender bags.")))
  )

(def test-bag-descriptions (str/split-lines "light red bags contain 1 bright white bag, 2 muted yellow bags.\ndark orange bags contain 3 bright white bags, 4 muted yellow bags.\nbright white bags contain 1 shiny gold bag.\nmuted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\nshiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\ndark olive bags contain 3 faded blue bags, 4 dotted black bags.\nvibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\nfaded blue bags contain no other bags.\ndotted black bags contain no other bags."))
(def test-bag-rules (map read-bag-rule test-bag-descriptions))
(deftest test-bag-rule-count
  (is (= 9 (count test-bag-rules))))

(def bag-and-parents (atom {}))
(deftest populate-bag-and-parents-test
  (doseq [rule test-bag-rules]
    (populate-bag-and-parents bag-and-parents rule))
  (is (= {"bright white" #{"light red" "dark orange"}, "muted yellow" #{"light red" "dark orange"}, "shiny gold" #{"muted yellow" "bright white"}, "faded blue" #{"muted yellow" "vibrant plum" "dark olive"}, "dark olive" #{"shiny gold"}, "vibrant plum" #{"shiny gold"}, "dotted black" #{"vibrant plum" "dark olive"}} @bag-and-parents))

  (deftest find-containing-colours-test
    (is (= #{"muted yellow" "light red" "dark orange" "bright white" "shiny gold"} (find-containing-colours @bag-and-parents "shiny gold")))
    )
  )

(deftest append-key-values-to-map-test
  (is (= {"a" #{1}} (append-key-values-to-map {} "a" 1)))
  (is (= {"a" #{1 2}} (append-key-values-to-map {"a" #{1}} "a" 2)))
  )