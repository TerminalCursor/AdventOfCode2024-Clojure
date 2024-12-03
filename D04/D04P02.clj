(ns d04p02
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(defn split-empty [s]
  (vec (map Integer/parseInt (str/split s #" +"))))

(def lines (->> (slurp "input-d02p01.txt")
                str/split-lines))

(def numbers (map split-empty lines))

(defn parse-row [row])

;(def answer)
;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 4 2 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
