(ns gardencss.docs)

(def docs
  {:simple [:body {:font-size "16px"}]
   
   :child-selectors [[:h1 :h2
                      {:font-weight "none"}]
                     [:h1
                      [:a {:text-decoration "none"}]]]
   
   :parent-selectors [:a
                      {:font-weight 'normal
                       :text-decoration 'none}
                      [:&:hover
                       {:font-weight 'bold
                        :text-decoration 'underline}]]
   
   :nested-multiple-selectors [:h1 :h2 {:font-weight "normal"}
                               [:strong :b {:font-weight "bold"}]]
   
   :escape-literal-strings [:pre {:font-family "\"Liberation Mono\", Consolas, monospace"}]})