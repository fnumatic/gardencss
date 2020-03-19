(ns gardencss.use-cases.core-cases
  (:require
   [re-frame.core :as rf]
   [gardencss.db :as db]
   [gardencss.docs :refer [docs]]
   [tools.reframetools :refer [sdb gdb]]
   [garden.core :refer [css]]
   [clojure.string :refer [blank?]] 
   [cljs.pprint :as ppp]
   [reitit.frontend.easy :as rtfe]))


(defn pp [d]
  (binding [ppp/*print-right-margin* 35]
   (with-out-str (ppp/pprint d))))

(defn goto [u]
  (rtfe/push-state :routes/example {:id u}))

(defn change-selection [_ [_ v]]
  {:change-route v})

(defn change-document [db [_ v]]
  (let [doc (pp  (get docs (keyword v) ""))] 
     (assoc-in db [:text/input] doc)))

(defn change-activepanel [{:keys [db]} [_ v]]
  (let [id            (get-in v [:path-params :id])
        exampleroute? (= (get-in v [:data :name]) :routes/example)
        todispatch    (if  exampleroute?
                        [[:set-document id]
                         [:set-selection-inner id]]
                        [])]
    {:db         (assoc-in  db [:active-panel] v) 
     :dispatch-n todispatch}))
   

(defn convert-txt [txt _]
    (try
      (let [rdstring (cljs.reader/read-string txt)]
        (do
          (rf/dispatch [:compile-stats ""])
          (css rdstring)))

      (catch js/Error e
        (rf/dispatch [:compile-stats "error"])
        (println e))))

(defn calc-compilestats [[cs inp out] _]
  (cond
    (not (blank? cs))
    {:msg   "no valid edn struct"
     :class "reddot"}
    
    (and (blank? cs)
         (and (not (blank? inp)) 
              (blank? out)))
    {:msg   "css compile failed"
     :class "reddot"}
    
    :else
    {:msg   ""
     :class "greendot"}))

(defn render [txt _]  
 (css (cljs.reader/read-string txt)))
 
(rf/reg-sub :text/input (gdb [:text/input]))
(rf/reg-sub :text/rendered
                  :<- [:text/input]
                  convert-txt)
                  
(rf/reg-sub ::active-panel (gdb [:active-panel]))
(rf/reg-sub ::re-pressed-example  (gdb [:re-pressed-example]))
(rf/reg-sub :compile-stats (gdb [:compile-stats]))

(rf/reg-sub :compile-stats-full
            :<- [:compile-stats]
            :<- [:text/input]
            :<- [:text/rendered]
            calc-compilestats)

(rf/reg-sub :selection (gdb [:selection]))
(rf/reg-sub :selections (gdb [:selections]))
(rf/reg-event-fx :set-selection   change-selection)
(rf/reg-fx :change-route goto)
(rf/reg-event-db :set-selection-inner   (sdb [:selection]))

(rf/reg-event-db :set-document change-document)

(rf/reg-event-db :compile-stats (sdb [:compile-stats]))
(rf/reg-event-db ::initialize-db (constantly db/default-db))
(rf/reg-event-db :text/input  (sdb [:text/input]))
(rf/reg-event-fx ::set-active-panel change-activepanel)

