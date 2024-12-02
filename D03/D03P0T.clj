(ns d03p0T
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(defn split-empty [s]
  (vec (map Integer/parseInt (str/split s #" +"))))

(def lines (->> (slurp "test-input.txt")
                str/split-lines))

(def numbers (map split-empty lines))

(defn parse-row [row])

;(def answer)
;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 3 1 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
;(def response (aoc-post/post-answer 2024 3 2 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
