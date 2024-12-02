(ns d02p0T
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(def lines (->> (slurp "test-input.txt")
                str/split-lines))

; (load-file "../TEST/post.clj")
;
; (def response (aoc-post/post-answer 2024 1 1 11))
; (println "Response Body" (:body response))
; (println "Status Code" (:status response))
