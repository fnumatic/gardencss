(ns gardencss.styles
  (:require
      [goog.dom :as gdom]
      [garden.core :as g]))



(def styles
  [
   [:.mainlane {:height "80vh"
                :width "50% !important"}]
   [:.scroll {:overflow :scroll}]
   [:.reddot :.greendot
    {:height        "20px"
     :width         "20px"
     :border-radius "50%"
     :margin "3px 1em 3px 0"
     :display       :inline-block}]
   [:.reddot {:background-color :red}]
   [:.greendot {:background-color :green}]
   
   [:.my-auto {:margin-top :auto
               :margin-bottom :auto}]])
   
   



(defn inject-node! [old-node new-node document]
  (if old-node
    (gdom/replaceNode new-node old-node)
    (gdom/appendChild (.-head document) new-node)))

(defn inject-inline-style [document id style]
  (let [old-style (gdom/getElement  id)
        new-style (gdom/createDom "style"
                                  (clj->js {:type "text/css"
                                            :id id})
                                  style)]

    (inject-node! old-style new-style document)))

(defn inject-inline-link [document id link]
  (let [old-link (gdom/getElement id)
        new-link (gdom/createDom "link"
                                 (clj->js {:id id
                                           :rel :stylesheet
                                           :href link}))]

    (inject-node! old-link new-link document)))


(defn inject-trace-styles [document]
  (inject-inline-style document "--reframe-template--" (apply g/css styles))
  (inject-inline-link document "--bootstrap--"  "https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"))

