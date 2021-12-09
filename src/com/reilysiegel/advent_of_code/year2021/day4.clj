(ns com.reilysiegel.advent-of-code.year2021.day4
  (:require [clojure.string :as str]
            [com.reilysiegel.advent-of-code.input :as aoc]))

(defn parse-input [s]
  (let [[xs & boards] (str/split s #"\n\n")]
    {:calls  (into []
                   (map parse-long)
                   (str/split xs #","))
     :boards (->> boards
                  (mapv str/split-lines)
                  (mapv (partial map #(str/split (str/trim %) #"\s+")))
                  (mapv (partial mapv (partial mapv parse-long))))}))

(def test-input
  (parse-input
   "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7"))

(defn call [n board]
  (mapv (partial replace {n nil}) board))

(defn board->rows [board]
  board)

(defn board->cols [board]
  (apply mapv vector board))

(defn board->diags [board]
  [(mapv #(nth %1 %2)
         board
         (range (count (first board))))
   (mapv #(nth %1 %2)
         board
         (reverse (range (count (first board)))))])

(defn board->directions [board]
  (vec (mapcat identity ((juxt board->rows board->cols #_board->diags)
                         board))))

(defn winner? [board]
  (->> board
       board->directions
       (some (partial every? nil?))))

(defn score [{:keys [boards calls]}]
  (reduce
   (fn [boards x]
     (let [boards (mapv (partial call x) boards)]
       (if (some winner? boards)
         (->> boards
              (filter winner?)
              first
              flatten
              (remove nil?)
              (reduce +)
              (* x)
              reduced)
         boards)))
   boards
   calls))

(score test-input)
;; => 4512

(def input (parse-input (aoc/input 2021 04)))

(score input)
;; => 41668

(defn last-winner-score [{:keys [boards calls]}]
  (reduce
   (fn [boards x]
     (let [boards (mapv (partial call x) boards)]
       (if (and (= 1 (count boards))
                (some winner? boards))
         (->> boards
              (filter winner?)
              first
              flatten
              (remove nil?)
              (reduce +)
              (* x)
              reduced)
         (remove winner? boards))))
   boards
   calls))

(last-winner-score test-input)
;; => 1924

(last-winner-score input)
;; => 10478
