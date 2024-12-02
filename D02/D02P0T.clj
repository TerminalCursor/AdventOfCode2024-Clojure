(ns d02p0T
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(def lines (->> (slurp "test-input.txt")
                str/split-lines))
