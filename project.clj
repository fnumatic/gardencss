(defproject gardencss "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.597"]
                 [reagent "0.10.0"]
                 [re-frame "0.12.0"]
                 [garden "1.3.9"] 
                 [metosin/reitit "0.4.2"]
                 [metosin/reitit-schema "0.4.2"]
                 [metosin/reitit-frontend "0.4.2"]]
                 

  :source-paths ["src"]

  :aliases {"fig"       ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:min"   ["run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]
            "fig:test"  ["run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" gardencss.test-runner]
            "ancient-all" ["update-in" ":plugins" "conj" "[lein-ancient \"0.6.15\"]"
                                                              "--" "ancient" "upgrade" ":interactive" ":all" ":check-clojure" ":no-tests"]
            "ancient" ["update-in" ":plugins" "conj" "[lein-ancient \"0.6.15\"]" "--" "ancient"]}

  :profiles {:dev {:dependencies [;[binaryage/devtools "0.9.10"]
                                  ;[day8.re-frame/re-frame-10x "0.4.3"]  
                                  [com.bhauman/figwheel-main "0.2.3"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]]}})
                   

