(ns repl-tests.scratch)

;; nrepl-jump
;; nrepl-jump-to-def uses this one to find the location of the source
;; if nrepl is running on the local server

((clojure.core/juxt
  (comp clojure.core/str clojure.java.io/resource :file)
  (comp clojure.core/str clojure.java.io/file :file) :line)
 (clojure.core/meta (clojure.core/resolve 'map)))

;; local unix host
["jar:file:/home/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar!/clojure/core.clj" "clojure/core.clj" 2416]

;; remote iOS host
["jar:file:/Users/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar!/clojure/core.clj" "clojure/core.clj" 2416]