(ns d05p01
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(defn split-empty [s]
  (vec (map Integer/parseInt (str/split s #" +"))))

(def lines (->> (slurp "input-d05p01.txt")
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
(println rules)
(def second-lines (rest second-lines))

(println first-lines)
(println second-lines)

(defn check-rules [row rules]
  (def matches (vec (last (apply mapv list (vec (core/re-seq #"([0-9]+)" row))))))
  (def matches (map Integer/parseInt matches))
  (def pass true)
  (doseq [rule rules]
    (def firsts (nth rule 0))
    (def lasts (nth rule 1))
    (def checking false)
    (doseq [v matches]
      (if (and checking (= v firsts))
        (def pass false))
      (if (and (not checking) (= v lasts))
        (def checking true))
      )
    )
  (if pass
    matches
    nil))

(def valid '())
(def valid-m '())
(defn middle [v]
  (nth v (/ (count v) 2)))
(doseq [line second-lines]
  (def valid? (check-rules line rules))
  (println valid?)
  (when-not (= valid? nil)
    (def valid (concat valid (list valid?)))
    (def valid-m (concat valid-m (list (middle valid?))))))

(println valid)
(println valid-m)
(println (reduce + valid-m))

(defn parse-row [row])

;(def answer)
;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 5 1 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
