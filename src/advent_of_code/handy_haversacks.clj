(ns advent-of-code.handy-haversacks
  (:require [clojure.string :as str])
  (:require [clojure.core.match :refer [match]])
  (:require [clojure.set :as cset]))

(defn read-top-bag [description]
  (-> description (str/split #"bags contain") (first) (str/trim)))

(defn read-content-bag-and-count [content-bag-description]
  (let [count-and-colour (-> content-bag-description
                             (str/split #" bag")
                             (first))
        count (-> count-and-colour
                  (str/split #" ")
                  (first)
                  (read-string))
        colour (-> count-and-colour
                   (.replace (str count) "")
                   (str/trim))]
    {colour count}))

(defn read-contents-with-count [description]
  (let [contents-str (-> description (str/split #"bags contain") (last) (str/trim))]
    (match [contents-str]
           ["no other bags"] {}
           [bags] (reduce #(conj %1 %2) (map read-content-bag-and-count (str/split bags #", ")))))
  )

(defn read-content-bag [content-bag-description]
  (-> content-bag-description (str/split #" bag") (first) (str/split #"\d") (last) (str/trim)))

(defn read-contents [description]
  (let [contents-str (-> description (str/split #"bags contain") (last) (str/trim))]
    (match [contents-str]
           ["no other bags"] []
           [bags] (map read-content-bag (str/split bags #",")))))


(defn read-bag-contents [description]
  (let [desc (str/replace description "." "")
        top (read-top-bag desc)
        contents (read-contents-with-count desc)]
    {top contents}))

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

(defn find-containing-colours [bag-and-parents-map bag-colour]
  (let [containing-colours (get bag-and-parents-map bag-colour #{})]
    (reduce #(cset/union %1 (find-containing-colours bag-and-parents-map %2)) #{bag-colour} containing-colours)))

(def all-bags-and-parents (atom {}))
(defn -main [& args]
  (def bag-rules (map read-bag-rule (read-bag-descriptions-from-file "test/resources/handy_haversacks.txt")))
  (doseq [rule bag-rules]
    (populate-bag-and-parents all-bags-and-parents rule))

  (def colours-containing-shiny-gold
    (find-containing-colours @all-bags-and-parents "shiny gold"))

  (println "No. of bag colours that can contain shiny gold bags:" (dec (count colours-containing-shiny-gold))))
