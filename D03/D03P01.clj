(ns d03p01
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(defn split-empty [s]
  (vec (map Integer/parseInt (str/split s #" +"))))

(def lines (->> (slurp "input-d03p01.txt")))

(def product 0)
(doseq [% (vec (core/re-seq #"mul\([0-9]{1,3},[0-9]{1,3}\)" lines))]
  (def numerals (core/re-seq #"[0-9]{1,3}" %))
  (def n1 (Integer/parseInt (nth numerals 0)))
  (def n2 (Integer/parseInt (nth numerals 1)))
  (def product (+ product (* n1 n2))))
(println product)

;(def answer product)
;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 3 1 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
;(def response (aoc-post/post-answer 2024 3 2 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
