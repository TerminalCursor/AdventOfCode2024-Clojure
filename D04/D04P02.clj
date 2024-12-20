(ns d04p02
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(def lines (->> (slurp "input-d04p01.txt")
                str/split-lines))

(println (str/join "\n" lines))

(defn get [mat x y]
  (nth (nth mat y) x))

(def y-c (count lines))
(def x-c (count (first lines)))
(def exes (for [x (range 1 (- x-c 1))]
           (for [y (range 1 (- y-c 1))]
             (list
              (str
               (get lines (- x 1) (- y 1))
               (get lines x y)
               (get lines (+ x 1) (+ y 1)))
              (str
               (get lines (- x 1) (+ y 1))
               (get lines x y)
               (get lines (+ x 1) (- y 1)))
              ))))

(def exes (apply concat exes))
(def truths 0)
(doseq [ex exes]
  (def a (nth ex 0))
  (def b (nth ex 1))
  (if (and
       (contains? #{"MAS" "SAM"} a)
       (contains? #{"MAS" "SAM"} b))
    (do
      (def truths (+ truths 1))
      )))

(println truths)

;(def answer)
;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 4 1 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
