(ns d05p02
  (:require [clojure.string :as str]
            [clojure.set :as cset]
            [clojure.core :as core]))

(defn split-empty [s]
  (vec (map Integer/parseInt (str/split s #" +"))))

(def lines (->> (slurp "input-d05p01.txt")
;(def lines (->> (slurp "test-input.txt")
                str/split-lines))

(def first-lines '())
(def second-lines '())

(def doing true)
(doseq [line lines]
  (if (= line "")
    (def doing false))
  (if doing
    (def first-lines (concat first-lines (list line)))
    (def second-lines (concat second-lines (list line)))))

(def rules '())
(doseq [rule first-lines]
  (def match (first (vec (core/re-seq #"^([0-9]+)\|([0-9]+)$" rule))))
  (def rules (concat rules (list (list (Integer/parseInt (nth match 1)) (Integer/parseInt (nth match 2)))))))
(def second-lines (rest second-lines))

(defn check-rules [row rules]
  (def matches (vec (last (apply mapv list (vec (core/re-seq #"([0-9]+)" row))))))
  (def matches (map Integer/parseInt matches))
  (def pass true)
  (def broken-rules '())
  (doseq [rule rules]
    (def firsts (nth rule 0))
    (def lasts (nth rule 1))
    (def checking false)
    (doseq [v matches]
      (when (and checking (= v firsts))
        (def pass false)
        (def broken-rules (concat broken-rules (list rule))))
      (if (and (not checking) (= v lasts))
        (def checking true))
      )
    )
  (if pass
    nil
    (list matches broken-rules)
    ))

(def invalid '())
(def invalid-m '())
(defn middle [v]
  (nth v (/ (count v) 2)))

(defn fix [row rules]
  (def tree '()))

(def rule-order {})
(doseq [rule rules]
  (def n-0 (nth rule 0))
  (def n-1 (nth rule 1))
  (def rule-n0 (get rule-order n-0))
  (when (= rule-n0 nil)
    (def rule-order (assoc rule-order n-0 (list '() '())))
    )
  (def rule-n1 (get rule-order n-1))
  (when (= rule-n1 nil)
    (def rule-order (assoc rule-order n-1 (list '() '()))))
  (def rule-n0 (get rule-order n-0))
  (def rule-n1 (get rule-order n-1))
  (def rule-n0 (list (first rule-n0)
                     (concat (second rule-n0) (list n-1))))
  (def rule-n1 (list (concat (first rule-n1) (list n-0))
                     (second rule-n1)))
  (def rule-order (assoc rule-order n-0 rule-n0))
  (def rule-order (assoc rule-order n-1 rule-n1))
  )
(doseq [key (keys rule-order)]
  (def v (get rule-order key))
  (def l (first v))
  (def r (second v))
  (def rule-order (assoc rule-order key (list (set l) (set r)))))

(doseq [line second-lines]
  (def valid? (check-rules line rules))
  (when-not (= valid? nil)
    (def invalid (concat invalid (list (first valid?))))
    (def invalid-m (concat invalid-m (list (middle (first valid?)))))))

(defn construct-ordering [row]
  (if-not (= (count row) 1)
    (do
      (def local-rules {})
      (def row-s (set row))
      (doseq [v row-s]
        (def global-rule (get rule-order v))
        (def global-l (first global-rule))
        (def global-r (second global-rule))
        (core/assert (not (contains? global-l v)))
        (def local-rules (assoc local-rules v (list (cset/intersection global-l row-s) (cset/intersection global-r row-s))))
        )

      (defn find-least [x]
        (list
         x
         (count
          (second
           (get local-rules x)))))
      (def least-value (first (first
                               (filter #(= (second %) 0)
                                       (map find-least
                                            (keys local-rules))))))
      (def remaining (filter #(not (= % least-value)) row))
      (concat (list least-value) (construct-ordering remaining)))
    row
    ))

(def invalid-m '())
(doseq [line invalid]
  (def invalid-m (concat invalid-m (list (middle (construct-ordering line))))))
(println (reduce + invalid-m))

;(println invalid)
;(println valid)
;(println valid-m)
;(println (reduce + valid-m))

(defn parse-row [row])

;(def answer)
;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 5 2 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
