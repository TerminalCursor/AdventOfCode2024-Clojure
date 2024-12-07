(import 'java.math.BigInteger)
(ns d07p01
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(defn split-empty [s]
  (vec (map Integer/parseInt (str/split s #" +"))))

(def lines (->> (slurp "input-d07p01.txt")
                str/split-lines))

(defn toBigInteger [x]
  (BigInteger. x))

(def numbers (vec (map #(core/re-seq #"([0-9]+)" %) lines)))
(def numbers (map #(map second %) numbers))
(def numbers (map #(map toBigInteger %) numbers))
;(println numbers)

(def operator-choice (list + *))
(def success-sum 0)
(doseq [line numbers]
  (def answer (first line))
  (def success false)
  (def numerals (rest line))
  (def operation-count (dec (count numerals)))
  ;(println "==" answer "==")
  (doseq [op-number (range (Math/pow 2 operation-count))]
    (def starting-number (first numerals))
    (def operation-string (.replace (format (str "%" operation-count "s") (Integer/toBinaryString op-number)) " " "0"))
    (doseq [n (range (count operation-string))]
      (do
        (def starting-number ((nth operator-choice (Integer/parseInt (str (nth operation-string n))))
                              starting-number
                              (nth numerals (inc n)))))
      )
    ;(println starting-number)
    (if (= starting-number answer)
      (do
          (def success true))))
  (if success
    (def success-sum (+ success-sum answer)))
  )

(println "== ANSWER ==")
(println success-sum)

;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 7 1 safe-count))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
