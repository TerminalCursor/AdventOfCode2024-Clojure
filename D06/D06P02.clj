(ns d06p02
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(defn split-empty [s]
  (vec (map Integer/parseInt (str/split s #" +"))))

;(def lines (->> (slurp "test-input.txt")
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

(println width height player player-x player-y)
;(run! println grid)
(defn mget [coll x y]
  (get (get coll y) x))

(defn traverse-grid [grid player-x player-y direction]
  (def running true)
  (def success true)
  (def covered 1)
  (def lattice grid)
  (def dir direction)
  (def guard-y player-y)
  (def guard-x player-x)
  (def steps 1)
  (while running
    (def dx 0)
    (def dy 0)
    (cond
      (= dir UP) (def dy -1)
      (= dir RIGHT) (def dx 1)
      (= dir DOWN) (def dy 1)
      (= dir LEFT) (def dx -1))
    (def next-block (mget lattice (+ guard-x dx) (+ guard-y dy)))
    (cond
      (= next-block nil) (do
                           (def running false)
                           (def success false))
      (= next-block 0) (do
                         (def covered (inc covered))
                         (def guard-x (+ guard-x dx))
                         (def guard-y (+ guard-y dy))
                         (def row (get lattice guard-y))
                         (def row (assoc row guard-x 3))
                         (def lattice (assoc lattice guard-y row))
                         (def steps (inc steps))
                         )
      (>= next-block 3) (do
                          (def guard-x (+ guard-x dx))
                          (def guard-y (+ guard-y dy))
                          (def steps (inc steps))
                          )
      (= next-block 2) (do
                         (def dir (mod (inc dir) 4)))
      :else (do
              (def running false)
              (if (= next-block nil)
                (def success false)))
      )
    (if (> steps (* 4 (* width height)))
      (do
        (def running false)
        (def success true)))
    )
  success)

(def obstructions 0)
(doseq [y (range height)]
  (doseq [x (range width)]
    (when-not (and
               (= y player-y)
               (= x player-x))
      (def test-row (get grid y))
      (def test-row (assoc test-row x 2))
      (def test-grid (assoc grid y test-row))
      (def looping (traverse-grid test-grid player-x player-y direction))
      ;(println looping)
      (when looping
        (def obstructions (inc obstructions))))))
(println obstructions)
(def answer obstructions)

;(load-file "../TEST/post.clj")
;(def response (aoc-post/post-answer 2024 6 2 answer))
;(println "Response Body" (:body response))
;(println "Status Code" (:status response))
