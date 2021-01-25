(ns advent-of-code.handy-haversacks-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.handy-haversacks :refer :all]
            [clojure.string :as str]))


(deftest read-top-bag-test
  (is (= "shiny plum" (read-top-bag "shiny plum bags contain no other bags")))
  (is (= "clear crimson" (read-top-bag "clear crimson bags contain 3 pale aqua bags, 4 plaid magenta bags, 3 dotted beige bags, 3 dotted black bags")))
  )

(deftest read-content-bag-test
  (is (= "pale aqua" (read-content-bag "3 pale aqua bags")))
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

(deftest directly-contains-another-test
  (is (= true (directly-contains-another {:top "clear crimson" :contents ["pale aqua" "plaid magenta"]} "pale aqua")))
  (is (= false (directly-contains-another {:top "clear crimson" :contents ["pale aqua" "plaid magenta"]} "shiny plum")))
  )

(deftest directly-contain-shiny-gold-bag?-test
  (is (= false (directly-contain-shiny-gold-bag? {:top "shiny plum" :contents []})))
  (is (= true (directly-contain-shiny-gold-bag? {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"] :directly-contains true})))
  )

(deftest mark-directly-contains-shiny-gold-bag-test
  (is (= {:top "shiny plum" :contents []} (mark-directly-contains-shiny-gold-bag {:top "shiny plum" :contents []})))
  (is (= {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"] :directly-contains true} (mark-directly-contains-shiny-gold-bag {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"]})))
  (is (= {:top "clear crimson" :contents ["pale aqua" "plaid magenta" "dotted beige" "dotted black"]} (mark-directly-contains-shiny-gold-bag {:top "clear crimson" :contents ["pale aqua" "plaid magenta" "dotted beige" "dotted black"]})))
  )

(deftest indirectly-contains-shiny-gold-bag-test
  (is (= true (indirectly-contains-shiny-gold-bag {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"]} #{"bright lavender" "dull white"})))
  (is (= true (indirectly-contains-shiny-gold-bag {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"]} #{"bright lavender"})))
  (is (= false (indirectly-contains-shiny-gold-bag {:top "vibrant beige", :contents ["drab gray" "shiny gold"]} #{"bright lavender" "dull white"})))
  )

(deftest mark-indirectly-contains-shiny-gold-bag-test
  (is (= {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"] :indirectly-contains true} (mark-indirectly-contains-shiny-gold-bag {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"]} #{"bright lavender" "dull white"})))
  (is (= {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"] :indirectly-contains true} (mark-indirectly-contains-shiny-gold-bag {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"]} #{"dull white"})))
  (is (= {:top "vibrant beige", :contents ["drab gray" "shiny gold"]} (mark-indirectly-contains-shiny-gold-bag {:top "vibrant beige", :contents ["drab gray" "shiny gold"]} #{"bright lavender" "dull white"})))
  )

(def test-bag-descriptions (str/split-lines "light red bags contain 1 bright white bag, 2 muted yellow bags.\ndark orange bags contain 3 bright white bags, 4 muted yellow bags.\nbright white bags contain 1 shiny gold bag.\nmuted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\nshiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\ndark olive bags contain 3 faded blue bags, 4 dotted black bags.\nvibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\nfaded blue bags contain no other bags.\ndotted black bags contain no other bags."))
(def test-bag-rules (map read-bag-rule test-bag-descriptions))
(deftest test-bag-rule-count
  (is (= 9 (count test-bag-rules))))
(def test-marked-bag-rules (map #(mark-directly-contains-shiny-gold-bag %) test-bag-rules))
(def test-bag-colours-directly-containing-shiny-gold-bags (atom #{}))


