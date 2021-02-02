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

(defn read-bag-descriptions-from-file [filename]
  (str/split-lines (slurp filename)))

(defn append-key-values-to-map [map key value]
  (let [curr-value (get map key #{})]
    (assoc map key (conj curr-value value))))

(defn populate-bag-and-parents [bag-and-parents-atom bag-rule]
  (doseq [key (:contents bag-rule)]
    (swap! bag-and-parents-atom append-key-values-to-map key (:top bag-rule))))

(defn find-containing-colours [bag-and-parents-atom bag-colour])

(defn -main [& args]
  ;(def bag-rules
  ;  (map collect-colors-directly-contains-shiny-gold-bag (bag-descriptions "test/resources/handy_haversacks.txt")))

  ;(doseq [rule bag-rules]
  ;  (if (directly-contain-shiny-gold-bag? rule)
  ;    (swap! bags-directly-containing-gold-bags conj (:top rule))))

  ;(def fully-marked-bag-rules
  ;  (map #(mark-indirectly-contains-shiny-gold-bag % @bags-directly-containing-gold-bags) bag-rules))

  ;(println fully-marked-bag-rules)
  ;(def all-colours-containing-shiny-gold-bags
  ;  (reduce #(if (or (directly-contain-shiny-gold-bag? %2)
  ;                   (indirectly-contain-shiny-gold-bag? %2))
  ;             (conj %1 (:top %2))
  ;             %1) #{} fully-marked-bag-rules))

  ;(prn all-colours-containing-shiny-gold-bags)
  ;(println "No. of bag colours that can contain shiny gold bags:" (count all-colours-containing-shiny-gold-bags))
  )
