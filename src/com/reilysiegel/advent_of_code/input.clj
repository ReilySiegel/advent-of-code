(ns com.reilysiegel.advent-of-code.input
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn input
  [year day]
  (slurp (io/resource (str "com/reilysiegel/advent_of_code/year"
                           year "/day" day ".txt"))))

(defn input-lines
  [year day]
  (str/split-lines (input year day)))
