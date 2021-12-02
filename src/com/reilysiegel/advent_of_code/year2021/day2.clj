(ns com.reilysiegel.advent-of-code.year2021.day2
  (:require [com.reilysiegel.advent-of-code.input :as aoc]
            [clojure.string :as str]))

(def test-input ["forward 5"
                 "down 5"
                 "forward 8"
                 "up 3"
                 "down 8"
                 "forward 2"])

(defn str->command-map
  "Tales a string in the form of \"direction amount\", and returns a map from
  direction to amount."
  [s]
  (let [[direction amount] (str/split s #"\s")]
    (hash-map (keyword direction) (parse-long amount))))

(defn commands->position [commands]
  (let [{:keys [forward down up]}  
        (reduce (partial merge-with (fnil + 0 0)) commands)]
    {:depth               (- down up)
     :horizontal-position forward}))

(->> test-input
     (map str->command-map)
     commands->position
     ((juxt :depth :horizontal-position))
     (reduce *))
;; => 150

(def input (aoc/input-lines 2021 2))

(->> input
     (map str->command-map)
     commands->position
     ((juxt :depth :horizontal-position))
     (reduce *))
;; => 1507611

(defn aim-commands->position [commands]
  (reduce (fn [{:keys [aim]
                :as   position}
               {:keys [down up forward]}]
            (cond-> position
              down
              (update :aim + down)
              up
              (update :aim + (- up))
              forward
              (-> (update :horizontal-position + forward)
                  (update :depth + (* aim forward)))))
          {:aim                 0
           :depth               0
           :horizontal-position 0}
          commands))

(->> test-input
     (map str->command-map)
     aim-commands->position
     ;; Multiply depth and horizontal position
     ((juxt :depth :horizontal-position))
     (reduce *))
;; => 900

(->> input
     (map str->command-map)
     aim-commands->position
     ;; Multiply depth and horizontal position
     ((juxt :depth :horizontal-position))
     (reduce *))
;; => 1880593125
