(ns advent-of-code.handy-haversacks
  (:require [clojure.string :as str])
  (:require [rx.lang.clojure.core :as rx])
  (:require [clojure.core.match :refer [match]])
  (:require [clojure.set :as cset]))

(defn read-top-bag [description]
  (-> description (str/split #"bags contain") (first) (str/trim)))

(defn read-content-bag [content-bag-description]
  (-> content-bag-description (str/split #" bags") (first) (str/split #"\d") (last) (str/trim)))

(defn read-contents [description]
  (let [contents-str (-> description (str/split #"bags contain") (last) (str/trim))]
    (match [contents-str]
           ["no other bags"] []
           [bags] (map read-content-bag (str/split bags #",")))))

(defn read-bag-rule [description]
  (let [desc (str/replace description "." "")
        top (read-top-bag desc)
        contents (read-contents desc)]
    {:top top :contents contents}))

(defn bag-descriptions [filename]
  (str/split-lines (slurp filename)))

(defn directly-contains-another [bag-rule another-bag]
  (let [contents (:contents bag-rule)]
    (boolean (some #{another-bag} contents))))

(defn mark-directly-contains-shiny-gold-bag [bag-rule]
  (if (directly-contains-another bag-rule "shiny gold")
    (assoc bag-rule :directly-contains true)
    bag-rule))

(defn indirectly-contains-shiny-gold-bag [bag-rule direct-bag-colours]
  (let [contents (:contents bag-rule)]
    (boolean (some direct-bag-colours contents))))

(defn mark-indirectly-contains-shiny-gold-bag [bag-rule direct-bag-colours]
  (if (indirectly-contains-shiny-gold-bag bag-rule direct-bag-colours)
    (assoc bag-rule :indirectly-contains true)
    bag-rule))

(defn directly-contain-shiny-gold-bag? [bag-rule]
  (boolean (:directly-contains bag-rule)))

(def bags-directly-containing-gold-bags (atom #{}))

(defn append-to-bags-atom [bag-rule]
  (if (directly-contain-shiny-gold-bag? bag-rule)
    (swap! bags-directly-containing-gold-bags conj (:top bag-rule))))

(defn -main [& args]
  (def bag-rules-observable (rx/seq->o (bag-descriptions "test/resources/handy_haversacks.txt")))
  (rx/subscribe bag-rules-observable
                #(-> % (read-bag-rule)
                     (mark-directly-contains-shiny-gold-bag)
                     (append-to-bags-atom)
                     ))
  (def bag-rules
    (map read-bag-rule (bag-descriptions "test/resources/handy_haversacks.txt")))

  (def marked-bag-rules (map
                          #(-> %
                               (mark-directly-contains-shiny-gold-bag)
                               (mark-indirectly-contains-shiny-gold-bag @bags-directly-containing-gold-bags)) bag-rules))

  (def bags-indirectly-contain-shiny-gold-bags (reduce #(if (or (:directly-contains %2) (:indirectly-contains %2)) (conj %1 (:top %2)) %1) #{} marked-bag-rules))
  ;(prn bags-indirectly-contain-shiny-gold-bags)
  ;(prn @bags-directly-containing-gold-bags)
  (prn "No. of bag colours that can contain shiny gold bags:" (count (cset/union @bags-directly-containing-gold-bags bags-indirectly-contain-shiny-gold-bags))))
