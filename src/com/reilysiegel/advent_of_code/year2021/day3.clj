(ns com.reilysiegel.advent-of-code.year2021.day3
  (:require [clojure.set :as set]
            [com.reilysiegel.advent-of-code.input :as aoc]))

(def test-input ["00100"
                 "11110"
                 "10110"
                 "10111"
                 "10101"
                 "01111"
                 "00111"
                 "11100"
                 "10000"
                 "11001"
                 "00010"
                 "01010"])

(defn rank-frequencies [xs]
  (->> xs
       frequencies
       (into [])
       (map reverse)
       (sort-by (juxt first second))))

(def most-common (comp second first rank-frequencies))
(def least-common (comp second last rank-frequencies))

(defn rate [digit-select-fn xs]
  (Long/parseLong (apply str (apply map (comp digit-select-fn vector) xs)) 2))

(def gamma-rate (partial rate most-common))
(def epsilon-rate (partial rate least-common))

(defn power-consumption [xs]
  (* (gamma-rate xs)
     (epsilon-rate xs)))

(power-consumption test-input)
;; => 198

(def input (aoc/input-lines 2021 3))

(power-consumption input)
;; => 2648450

(defn first-filtered-rate [digit-select-fn xs]
  (Long/parseLong
   (first (reduce
           (fn [xs index]
             (let [get-digit      #(nth % index)
                   selected-digit (digit-select-fn (map get-digit xs))]
               (filter (comp (partial = selected-digit) get-digit) xs)))
           xs
           (range (count (first xs)))))
   2))

(def oxygen-generator-rating (partial first-filtered-rate most-common))
(def co2-scrubber-rating (partial first-filtered-rate least-common))

(defn life-support-rating [xs]
  (* (oxygen-generator-rating xs)
     (co2-scrubber-rating xs)))

(life-support-rating test-input)
;; => 230

(life-support-rating input)
;; => 2845944

