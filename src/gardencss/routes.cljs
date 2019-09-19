(ns gardencss.routes
  (:require
    [re-frame.core :as rf]
    [reitit.frontend :as rtf]
    [reitit.frontend.history :as rtfh]
    [reitit.frontend.easy :as rtfe]
    [reitit.coercion.schema :as rsc]
    [gardencss.use-cases.core-cases :as ccases]
    [gardencss.views.home :as home]))
    

;;https://clojure.org/guides/weird_characters#__code_code_var_quote
(def routes
  [
   ["/"
    {:name :routes/frontpage
     :view #'home/main}]
   ["/example/{id}"
    {:name       :routes/example
     :view       #'home/main}]])
     ;:parameters {:path {:id string?}}}]])
       

(defn app-routes []

  (rtfe/start! 
   (rtf/router routes {:data {:coercion rsc/coercion}})
   (fn [m ]  
     (rf/dispatch [::ccases/set-active-panel m]))
   {:use-fragment true}))



