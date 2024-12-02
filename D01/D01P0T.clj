(ns d01p0T
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(defn split-empty [s]
  (str/split s #" +"))

(def lines (->> (slurp "test-input.txt")
                str/split-lines))

(def numbers (map split-empty lines))

;(run! println lines)
;(run! println numbers)

(def first-ids (core/sort (map Integer/parseInt (vec (map first numbers)))))
(def second-ids (core/sort (map Integer/parseInt (vec (map last numbers)))))

;(println first-ids)
;(println second-ids)

(defn element-absolute-difference [v1 v2]
  (mapv #(Math/abs (- %1 %2)) v1 v2))

(def abs-difference (element-absolute-difference first-ids second-ids))

;(println abs-difference)

(println "d01p0T")
(println (reduce + abs-difference))

(defn count-occurrences [v]
  (* v (count (filter #(= % v) second-ids))))

(def similarity (map count-occurrences first-ids))
(println (reduce + similarity))
