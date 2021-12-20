(ns com.reilysiegel.advent-of-code.year2021.day5
  (:require [clojure.string :as str]
            [com.reilysiegel.advent-of-code.input :as aoc]))

(def test-input-raw "0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2")

(defn parse-input [s]
  (into []
        (comp (mapcat #(str/split % #" -> "))
              (mapcat  #(str/split % #","))
              (map parse-long)
              (partition-all 2)
              (partition-all 2))
        (str/split-lines s)))

(def test-input (parse-input test-input-raw))

(defn step [[x2 y2] [x1 y1]]
  [(+ x1 (Long/signum (- x2 x1)))
   (+ y1 (Long/signum (- y2 y1)))])

(defn covers
  ([from to]
   (conj (take-while (complement #{to}) (iterate (partial step to) from))
         to))
  ([pairs]
   (mapcat (partial apply covers) pairs)))

(defn part1 [input]
  (->> input
       ;; Only horizontal and vertical lines
       (filter (fn [[[x1 y1] [x2 y2]]] (or (= x1 x2) (= y1 y2))))
       covers
       frequencies
       (filter (fn [[point count]] (> count 1)))
       count))

(part1 test-input)
;; => 5

(def input (parse-input (aoc/input 2021 5)))

(part1 input)
;; => 6572

(defn part2 [input]
  (->> input
       covers
       frequencies
       (filter (fn [[point count]] (> count 1)))
       count))

(part2 test-input)
;; => 12

(part2 input)
;; => 21466
