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

the basis for the lookup is the path on the remote machine, but the path isn't modified with the tramp information and ends up opening a file in 

/Users/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar

which is wrong, if the machine intiating the remote connection is NOT a mac.
In this case, the remote machine is a mac and the local macine is running suse.

/home/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar

does exist.

## remote connecting to swank (via slime)

M-. runs the command slime-edit-definition

.. and fails with "Search failed: "  clojure/core.cls$"



## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
