(ns advent-of-code.handy-haversacks-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.handy-haversacks :refer :all]))


(deftest read-top-bag-test
  (is (= "shiny plum" (read-top-bag "shiny plum bags contain no other bags")))
  (is (= "clear crimson" (read-top-bag "clear crimson bags contain 3 pale aqua bags, 4 plaid magenta bags, 3 dotted beige bags, 3 dotted black bags")))
  )

(deftest read-contents-test
  (is (= [] (read-contents "shiny plum bags contain no other bags")))
  (is (= ["pale aqua" "plaid magenta" "dotted beige" "dotted black"] (read-contents "clear crimson bags contain 3 pale aqua bags, 4 plaid magenta bags, 3 dotted beige bags, 3 dotted black bags")))
  )

(deftest read-content-bag-test
  (is (= "pale aqua" (read-content-bag "3 pale aqua bags")))
  )

(deftest read-bag-rule-test
  (is (= {:top "shiny plum" :contents []} (read-bag-rule "shiny plum bags contain no other bags.")))
  (is (= {:top "clear crimson" :contents ["pale aqua" "plaid magenta" "dotted beige" "dotted black"]} (read-bag-rule "clear crimson bags contain 3 pale aqua bags, 4 plaid magenta bags, 3 dotted beige bags, 3 dotted black bags.")))
  (is (= {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"]} (read-bag-rule "vibrant beige bags contain 3 drab gray bags, 4 shiny gold bags, 4 dull white bags, 3 bright lavender bags.")))
  )

(deftest directly-contains-another-test
  (is (= true (directly-contains-another {:top "clear crimson" :contents ["pale aqua" "plaid magenta"]} "pale aqua")))
  (is (= false (directly-contains-another {:top "clear crimson" :contents ["pale aqua" "plaid magenta"]} "shiny plum")))
  )

(deftest mark-directly-contains-shiny-gold-bag-test
  (is (= {:top "shiny plum" :contents []} (mark-directly-contains-shiny-gold-bag {:top "shiny plum" :contents []})))
  (is (= {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"] :directly-contains true} (mark-directly-contains-shiny-gold-bag {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"]})))
  (is (= {:top "clear crimson" :contents ["pale aqua" "plaid magenta" "dotted beige" "dotted black"]} (mark-directly-contains-shiny-gold-bag {:top "clear crimson" :contents ["pale aqua" "plaid magenta" "dotted beige" "dotted black"]})))
  )

(deftest directly-contain-shiny-gold-bag-test
  (is (= false (directly-contain-shiny-gold-bag? {:top "shiny plum" :contents []})))
  (is (= true (directly-contain-shiny-gold-bag? {:top "vibrant beige", :contents ["drab gray" "shiny gold" "dull white" "bright lavender"] :directly-contains true})))
  )
