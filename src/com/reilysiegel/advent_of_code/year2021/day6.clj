(ns com.reilysiegel.advent-of-code.year2021.day6
  (:require [com.reilysiegel.advent-of-code.input :as aoc]
            [clojure.string :as str]
            [clojure.set :as set]))

(def test-input [3 4 3 1 2])

(defn lanternfish
  ([fish]
   (reduce-kv (fn [fish k v]
                (if (pos? k)
                  (update fish (dec k) (fnil +' 0) v)
                  (-> fish
                      (update 6 (fnil +' 0) v)
                      (update 8 (fnil +' 0) v))))
              {}
              fish))
  ([input iterations]
   (reduce +
           (-> (iterate lanternfish (frequencies input))
               (nth iterations)
               vals))))

(lanternfish test-input 80)
;; => 5934

(def input (-> 2021
               (aoc/input 6)
               str/trim
               (str/split #",")
               (->> (map parse-long))))

(lanternfish input 80)
;; => 365862


(lanternfish test-input 256)
;; => 26984457539

(lanternfish input 256)
;; => 1653250886439
