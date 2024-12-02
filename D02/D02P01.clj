(ns d02p01
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(defn split-empty [s]
  (vec (map Integer/parseInt (str/split s #" +"))))

(def lines (->> (slurp "input-d02p01.txt")
                str/split-lines))

(def numbers (map split-empty lines))

(defn parse-row [row]
  (def elem-difference  (map - (rest row) row))
  (def absolute-bound? (filter #(and (<= % 3) (>= % -3)) elem-difference))
  (def increasing-bound? (filter #(and (<= % 3) (> % 0)) elem-difference))
  (def decreasing-bound? (filter #(and (>= % -3) (< % 0)) elem-difference))
  (and (= (count elem-difference) (count absolute-bound?))
       (or
        (= (count increasing-bound?) (count elem-difference))
        (= (count decreasing-bound?) (count elem-difference)))
       ))

(def difrow (map parse-row numbers))
(def safe-count (count (filter #(= % true) difrow)))

(println safe-count)

;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 2 1 safe-count))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
