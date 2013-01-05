(defproject repl-tests "0.1.0-SNAPSHOT"
  :description "nREPL and swank connections embedded"
  :url "http://clojure.lang.dk"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
		 [org.clojure/tools.nrepl "0.2.0-RC1"]
		 [swank-clojure "1.4.0-SNAPSHOT"]]

  :plugins [[lein-swank "1.4.1"]]
  :min-lein-version "2.0.0"
  :main repl-tests.core)
