(ns repl-tests.core
  (:require [swank swank])
  (:use [clojure.tools.nrepl.server :only (start-server stop-server)]))

(defn -main [& m]
  (swank.swank/start-repl 4005)
  (start-server :port 7888)
  (println "swank server started on localhost:4005")
  (println "nREPL server started on localhost:7888")

  (let [host (.getHostAddress
              (java.net.InetAddress/getLocalHost))]
    (swank.swank/start-repl 4006 :host host)
    (printf "swank server started on %s:4006\n" host)
    (start-server :port 7889 :bind host)
    (printf "nREPL server started on %s:7889\n" host)))

