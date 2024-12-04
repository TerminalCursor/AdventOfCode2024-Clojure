(ns d04p01
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(def lines (->> (slurp "input-d04p01.txt")
                str/split-lines))

(println (str/join "\n" lines))
(def diagonals-a lines)
(def diagonals-b (for [n (range (count diagonals-a))]
                   (str (str/join (take n (repeat "."))) (nth diagonals-a n))))
(def diagonals-1 (apply mapv str (for [n (range (count diagonals-a))]
                                   (str (str/join (take n (repeat "."))) (nth diagonals-a n) (str/join (take (- (count diagonals-a) n) (repeat ".")))))))
(def diagonals-2 (apply mapv str (for [n (range (count diagonals-a))]
                                   (str (str/join (take n (repeat "."))) (str/reverse (nth diagonals-a n)) (str/join (take (- (count diagonals-a) n) (repeat ".")))))))

(def lines (concat '("Original") lines
                   '("Reversed") (map str/reverse lines)
                   '("T") (apply map str lines)
                   '("Reversed T") (apply map str (reverse lines))
                   '("DownLeft D") diagonals-1
                   '("DownRight D") diagonals-2
                   '("UpRight D") (map str/reverse diagonals-1)
                   '("DownRight D") (map str/reverse diagonals-2)))
(println lines)

(def counter 0)
(doseq [line lines]
  (def matches (core/re-seq #"XMAS" line))
  (println (count matches) line matches)
  (def counter (+ counter (count matches))))
(println counter)
;(println diagonals)

;(def answer)
;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 4 1 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
