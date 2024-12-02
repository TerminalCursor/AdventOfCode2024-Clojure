(ns d02p02
  (:require [clojure.string :as str]
            [clojure.core :as core]))

(def lines (->> (slurp "input-d02p01.txt")
                str/split-lines))
