(ns repl-tests.scratch
  (:use [repl-tests.jump-here :only (target)]))

;; nrepl-jump
;; nrepl-jump-to-def uses this one to find the location of the source
;; if nrepl is running on the local server

((clojure.core/juxt
  (comp clojure.core/str clojure.java.io/resource :file)
  (comp clojure.core/str clojure.java.io/file :file) :line)
 (clojure.core/meta (clojure.core/resolve 'map)))

;; local unix host
["jar:file:/home/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar!/clojure/core.clj" "clojure/core.clj" 2416]

;; remote MacOS host
["jar:file:/Users/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar!/clojure/core.clj" "clojure/core.clj" 2416]

;; this path is wrong ("/ssh:klang@192.168.1.9:" is missing and the file is looked up on the local and not the remote host. Because the local host is a different type than the remote the ~ is different and result in a flawed lookup. At any rate, looking up the reference on local host is wrong, even if the path was correct (which would happen if the two hosts were of the same type) there would be no security that the source was in the same state [granted, the versioning of the clojure jar file does give some security, which leaves only the wrong path to consider])
["jar:file:/ssh:klang@192.168.1.9:/Users/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar!/clojure/core.clj" "clojure/core.clj" 2416]


;;
;; looking up a custom created file does result in the correct file, in both cases

((clojure.core/juxt
  (comp clojure.core/str clojure.java.io/resource :file)
  (comp clojure.core/str clojure.java.io/file :file) :line)
 (clojure.core/meta (clojure.core/resolve 'target)))

;; local unix host
["file:/home/klang/projects/repl-tests/src/repl_tests/jump_here.clj" "repl_tests/jump_here.clj" 3]

;; remote MacOS host
["" "/ssh:klang@192.168.1.9:/Users/klang/Dropbox/repos/nonrepo/repl-tests/src/repl_tests/jump_here.clj" 3]

;; latest version of the source is a bit different, but the above findings still hold:
;; the path to the jars running are returned, as local paths and thereby not understood
;; by the system running emacs, if this system differs from the system running clojure.
((clojure.core/juxt
  (clojure.core/comp clojure.core/str clojure.java.io/resource :file)
  (clojure.core/comp clojure.core/str clojure.java.io/file :file) :line)
 (clojure.core/meta (clojure.core/ns-resolve 'repl-tests.scratch 'map)))
["jar:file:/home/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar!/clojure/core.clj" "clojure/core.clj" 2416]

((clojure.core/juxt
  (clojure.core/comp clojure.core/str clojure.java.io/resource :file)
  (clojure.core/comp clojure.core/str clojure.java.io/file :file) :line)
 (clojure.core/meta (clojure.core/ns-resolve 'repl-tests.scratch 'target)))

["" "/ssh:klang@192.168.1.9:/Users/klang/Dropbox/repos/nonrepo/repl-tests/src/repl_tests/jump_here.clj" 3]

