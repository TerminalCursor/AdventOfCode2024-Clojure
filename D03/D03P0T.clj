(ns d03p0T
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(def lines (->> (slurp "test-input.txt")))

(def product 0)

(def match (vec (core/re-seq #"mul\(([0-9]{1,3}),([0-9]{1,3})\)|do\(\)|don't\(\)" lines)))


(doseq [% match]
  (def op (nth % 0))
  (def n1 (nth % 1))
  (def n2 (nth % 2))
  (when-not (= n1 nil)
    (def n2 (Integer/parseInt n2))
    (def n1 (Integer/parseInt n1))
    (def product
      (+ product (* n1 n2)))))
(println product)

;(def answer)
;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 3 1 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
;(def response (aoc-post/post-answer 2024 3 2 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
