(ns gardencss.db
  (:require
   [gardencss.docs :refer [docs]]))



(def default-db
  {:name "re-frame"
   :selection ""
   :selections (->> docs keys vec )})

