(ns d03p02
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(defn split-empty [s]
  (vec (map Integer/parseInt (str/split s #" +"))))

;(def lines (->> (slurp "test-input.txt")))
(def lines (->> (slurp "input-d03p01.txt")))

(def product 0)

(def match (vec (core/re-seq #"mul\(([0-9]{1,3}),([0-9]{1,3})\)|do\(\)|don't\(\)" lines)))
(println match)

(doseq [% match])

(def doing true)
(doseq [% match]
  (def op (nth % 0))
  (def n1 (nth % 1))
  (def n2 (nth % 2))
  (if (= n1 nil)
      (if (= op "do()")
        (def doing true)
        (def doing false))
      (if (= true doing)
        (do
    (def n2 (Integer/parseInt n2))
    (def n1 (Integer/parseInt n1))
    (def product
      (+ product (* n1 n2)))))))
(println product)

;(def answer product)
;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 3 1 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
;(def response (aoc-post/post-answer 2024 3 2 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
