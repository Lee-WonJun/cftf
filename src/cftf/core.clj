(ns cftf.core)

(defn- generation-function [function-name ratio]
  (let [function-symbol        (symbol function-name)]
    (intern 'cftf.core function-symbol (fn [input] (cftf.core/convert ratio input)))))

(defn- unit-combination
  [units]
  (for [key1 units key2 units]
    (vector key1 key2)))

(defn- get-name [input]
  (if (keyword? input)
    (name input)
    (str input)))

(defn- create-function-name
  ([unit-pair]
   (let [first-name (comp first first)
         second-name (comp first second)]
     (create-function-name (first-name unit-pair) (second-name unit-pair))))
  ([from to]
   (str (get-name from) "->" (get-name to))))

(defn- create-unit-ratio
  ([unit-pair]
   (let [first-unit (comp second first)
         second-unit (comp second second)]
     (create-unit-ratio (first-unit unit-pair) (second-unit unit-pair))))
  ([from to]
   (/ from to)))

(defn- units->function-name-and-ratio [units]
  (map (juxt create-function-name create-unit-ratio) (unit-combination units)))

(defn convert
  ([units from to input-value]
   (convert (create-unit-ratio (from units) (to units)) input-value))
  ([ratio input-value]
   (* input-value ratio)))

(defn generate-function-by-units [units]
  (->> units
       (units->function-name-and-ratio)
       (map #(generation-function (first %) (second %)))))
