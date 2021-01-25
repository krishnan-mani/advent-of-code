(ns advent-of-code.handy-haversacks
  (:require [clojure.string :as str])
  (:require [rx.lang.clojure.core :as rx])
  (:require [clojure.core.match :refer [match]])
  (:require [clojure.set :as cset]))

(defn read-top-bag [description]
  (-> description (str/split #"bags contain") (first) (str/trim)))

(defn read-content-bag [content-bag-description]
  (-> content-bag-description (str/split #" bag") (first) (str/split #"\d") (last) (str/trim)))

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

(defn indirectly-contain-shiny-gold-bag? [bag-rule]
  (boolean (:indirectly-contains bag-rule)))

(def bags-directly-containing-gold-bags (atom #{}))

(defn collect-colors-directly-contains-shiny-gold-bag [description]
  (-> description
      (read-bag-rule)
      (mark-directly-contains-shiny-gold-bag)))

(defn -main [& args]
  (def bag-rules
    (map collect-colors-directly-contains-shiny-gold-bag (bag-descriptions "test/resources/handy_haversacks.txt")))

  (doseq [rule bag-rules]
    (if (directly-contain-shiny-gold-bag? rule)
      (swap! bags-directly-containing-gold-bags conj (:top rule))))

  (prn @bags-directly-containing-gold-bags)
  (def fully-marked-bag-rules
    (map #(mark-indirectly-contains-shiny-gold-bag % @bags-directly-containing-gold-bags) bag-rules))

  (println fully-marked-bag-rules)
  (def all-colours-containing-shiny-gold-bags
    (reduce #(if (or (directly-contain-shiny-gold-bag? %2)
                     (indirectly-contain-shiny-gold-bag? %2))
               (conj %1 (:top %2))
               %1) #{} fully-marked-bag-rules))

  ;(prn all-colours-containing-shiny-gold-bags)
  ;(println "No. of bag colours that can contain shiny gold bags:" (count all-colours-containing-shiny-gold-bags))
  )
