repl-tests
==========

A simple setup to figure out why M-. does not work when connecting to a remote repl (when origin and target architectures differ)

nREPL and Swank embedded in the same clojure project and try to get some interactive development up and running.

## Usage

Feersum-Endjinn:repl-tests klang$ lein run
Compiling repl-tests.core
user=> Connection opened on null port 4005.
nil
swank server started on localhost:4005
nREPL server started on localhost:7888
user=> Connection opened on 192.168.1.9 port 4006.
nil
swank server started on 192.168.1.9:4006
nREPL server started on 192.168.1.9:7889

From localhost and a remote host, try connecting to the different servers.

## remote connecting to nREPL

M-. runs the command nrepl-jump

nrepl-jump-to-def uses this one to find the location of the source and will ultimately run the following clojure code and return the result to elisp:

((clojure.core/juxt
  (comp clojure.core/str clojure.java.io/resource :file)
  (comp clojure.core/str clojure.java.io/file :file) :line)
 (clojure.core/meta (clojure.core/resolve 'map)))

The result of this call is a path to the place where the current var is defined in the source (here, I've selected 'map' as an example)

Assume that we are running emacs (23.3.1) on a unix (OpenSuse) host and that we have 'repl-tests' running on both the local unix host and a remote MacOS host.


When connecting to a to the project (started with 'lein run' on the local host) the result of the above expression is

["jar:file:/home/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar!/clojure/core.clj" "clojure/core.clj" 2416]

and the path is correct.

When connecting to the same project (started with 'lein run' on a remote host) the result of the expression is

["jar:file:/Users/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar!/clojure/core.clj" "clojure/core.clj" 2416]

that path is correct, as seen from the project. 

BUT, the path is wrong as seen from emacs. Emacs can't find that location on the local host (it's a location on the remote host) .. "/ssh:klang@192.168.1.9:" is missing from the path.

Had the remote host been a unix machine AND the usernames on the two machines were identical, the path would have been returned as "/home/klang/..." and the documentation would have been shown as expected.


## How should this be fixed?

Is it correct to refer to clojure.core and other jar files on the local host (and expect the usernames and path information to match) or do we have to add the path information that allows emacs (at least) to find the source via a tramp connection. 

In short; the path to the source has to be classpath and host/user aware in some way for lookup in jar files to work.

## remote connecting to swank (via slime)

M-. runs the command slime-edit-definition

.. and fails with "Search failed: "  clojure/core.cls$"

As swank seems to be deprecated, I will not dive into the reasons for this, yet.

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
