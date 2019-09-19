(ns gardencss.views.home
  (:require
    [re-frame.core :as rf]
    [tools.viewtools :as vt]
   [gardencss.use-cases.core-cases :as ccases]))
    

(defn txt-input []
 (let [inp (rf/subscribe [:text/input])]
  [:div.mainlane.col
   [:textarea.w-100.h-100.border.border-primary.scroll
    {:value @inp
     :on-change #(rf/dispatch [:text/input (-> % .-target .-value)])
     :rows 30}]]))

(defn ascii-render []
  (let [html (rf/subscribe [:text/rendered])]
    [:div.mainlane.col
     [:pre.w-100.h-100.border.border-success.scroll
      @html]]))

(defn option [label]
  [:option label])

(defn select-examples []
  (let [selection (rf/subscribe [:selection])
        selections (rf/subscribe [:selections])]
   [:select.custom-select.mb-3
       {:value @selection
        :on-change #(rf/dispatch [:set-selection (-> % (.-target) (.-value))])
        :size 1}
    (for [l @selections]
      ^{:key l}[option l])]))
    
(defn compile-stats []
  (let [stats (rf/subscribe [:compile-stats-full])]
    [:div.col.d-flex.align-items-center
     [:span {:class (:class @stats)}]
     [:span (:msg @stats)]]))

(defn main []
  
  [:div.container
   [:h2 "Garden to CSS"]
   [select-examples]
   [:div.row
     [txt-input]
     [ascii-render]]
    
   [:div.row
    [compile-stats]]])

(def toolbar-items
  [
   ["#" :routes/frontpage]
   ["component" :routes/component]])

;; main

(defn show-panel [route]
  (when-let [route-data (:data route)]
    (let [view (:view route-data)]
      [:<>
       [view]])))
       ;[:pre (with-out-str (cljs.pprint/pprint route))]])))

(defn main-panel []
  (let [active-route (rf/subscribe [::ccases/active-panel])]
    [:div
     
     ;[vt/navigation toolbar-items]
     [show-panel @active-route]]))
     
     
(comment
  (+ 2 2)
  (-> 2 (* 2))
  (+ 2 2))
  