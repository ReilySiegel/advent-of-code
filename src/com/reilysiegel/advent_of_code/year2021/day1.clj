(ns com.reilysiegel.advent-of-code.year2021.day1
  (:require [com.reilysiegel.advent-of-code.input :as aoc]))

(def test [199
           200
           208
           210
           200
           207
           240
           269
           260
           263])

(defn count-increasing [xs]
  (count (filter (partial apply <) (partition 2 1 xs))))

(count-increasing test)
;; => 7

(def input (map parse-long (aoc/input-lines 2021 1)))

(count-increasing input)
;; => 1553

(defn sum-sliding-three [xs]
  (map (partial apply +) (partition 3 1 xs)))

(count-increasing (sum-sliding-three test))
;; => 5

(count-increasing (sum-sliding-three input))
;; => 1597
