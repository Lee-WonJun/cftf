(ns cftf.core-test
  (:require [clojure.test :refer :all]
            [cftf.core :as c]))


(defn amount-of-alcohol-per-one-bottle [bottle-size abv]
  (* bottle-size (/ abv 100)))

(def alcohol-units
  {:soju (amount-of-alcohol-per-one-bottle 360 16.5)
   :beer (amount-of-alcohol-per-one-bottle 500 5)
   :whisky (amount-of-alcohol-per-one-bottle 700 40)
   })

(c/generate-function-by-units alcohol-units)

(deftest a-test
  (testing "soju->beer test"
    (is (= (int (c/soju->beer 1)) 2))))
