(ns d06p01
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(defn split-empty [s]
  (vec (map Integer/parseInt (str/split s #" +"))))

(def lines (->> (slurp "input-d06p01.txt")
                str/split-lines))

(def toint (fn [x] (int x)))
(def grid (map #(map toint %) lines))
(def width (count (first grid)))
(def height (count grid))
(def player 1)
(def UP 0)
(def RIGHT 1)
(def DOWN 2)
(def LEFT 3)
(def direction UP)

(defn decode [x]
  (cond
    (= x 35) 2
    (= x 94) 1
    :else 0))

(def grid (map #(map decode %) grid))
(def grid (vec (map vec grid)))

(def player-x -1)
(def player-y -1)

(defn index-of [coll value]
  (some #(when (= (second %) value) (first %))
        (map-indexed vector coll)))

(def finding true)
(def row-index 0)
(while finding
  (assert (< row-index height))
  (def row (vec (nth grid row-index)))
  (if (not (= nil ((set row) player)))
    (do
      (def finding false)
      (def player-y row-index)
      (def player-x (index-of row player))
      )
    )
  (def row-index (inc row-index)))

(def player-row (get grid player-y))
(def player-row (assoc player-row player-x 3))
(def grid (assoc grid player-y player-row))
(def running true)
(def next-number 4)
(def covered 1)

(println width height player player-x player-y)
;(run! println grid)

(defn mget [coll x y]
  (get (get coll y) x))

(while running
  (def dx 0)
  (def dy 0)
  (cond
    (= direction UP) (def dy -1)
    (= direction RIGHT) (def dx 1)
    (= direction DOWN) (def dy 1)
    (= direction LEFT) (def dx -1))
  (def next-block (mget grid (+ player-x dx) (+ player-y dy)))
  (cond
    (= next-block nil) (def running false)
    (= next-block 0) (do
                       (def covered (inc covered))
                       (def player-x (+ player-x dx))
                       (def player-y (+ player-y dy))
                       (def row (get grid player-y))
                       (def row (assoc row player-x 3))
                       (def grid (assoc grid player-y row))
                       )
    (>= next-block 3) (do
                       (def player-x (+ player-x dx))
                       (def player-y (+ player-y dy))
                       )
    (= next-block 2) (do
                      (def direction (mod (inc direction) 4)))
    :else nil
    )
  )

(println covered)
(def answer covered)

;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 6 1 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
