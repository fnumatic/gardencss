(ns ^:figwheel-hooks gardencss.core
  (:require
   [goog.dom :as gdom]
   [re-frame.core :as re-frame]
   [gardencss.styles :as styl]
   [gardencss.views.home :as views ]
   [gardencss.use-cases.core-cases :as ccases]
   [gardencss.routes :as routes]
   [reagent.core :as reagent :refer [atom]]))


(defn dev-setup []
  (when ^boolean goog.DEBUG
    (enable-console-print!)
    (println "dev mode")))

;; define your app data so that it doesn't get over-written on reload

(defn get-app-element []
  (gdom/getElement "app"))


(defn mount [el]
 (re-frame/clear-subscription-cache!)
 (styl/inject-trace-styles js/document) 
 (reagent/render-component [views/main-panel] el))



(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

(defn init []
  (re-frame/dispatch-sync [::ccases/initialize-db])
  ;(dev-setup)
  (routes/app-routes))

(defonce inits (init))

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element))
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)

