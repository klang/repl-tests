;; nrepl-find-resource will get the wrong resource, if the resource is located
;; on an external host (of different type), connected via nrepl.
;; as find-file will open a resource, if it is not found M-. will result in
;; opening an empty file.
;; Figuring, that we want to avoid transferring large jar files over a slow
;; network connection (with the en/de-coding also involved) we could see if
;; there is a local resource that might serve the same purpose.

;; nrepl-maybe-local-m2-resource will only modify the path, if the path given
;; does not exist in the current environment. This can happen, if the jar file
;; is supposed to be in a path starting with "/Users/USER/..", i.e. nrepl
;; is connected to a mac from a platform that has another dir layout.

;; tested:
;; | emacs with nrepl running on | connected to clojure running on |
;; | windows 7                   | Linux                           |
;; | Linux                       | MacOS                           |
;;
;; | windows 7                   | MacOS                           |
;;
;; | MacOS                       | MacOS                           |
;; | Linux                       | Linux                           |
;; | windows 7                   | windows 7                       |
;

(defun nrepl-maybe-local-m2-resource (jar)
  (cond 
   ((file-exists-p jar) jar)
   ((string-match "^.+\\(\\/.m2.+\\)" jar)
    (concat (getenv "HOME")  (match-string 1 jar)))
   (:else jar)))

(defun nrepl-maybe-local-m2-resource (jar)
  (cond 
   ((file-exists-p jar) jar)
   ((string-match "^.+\\(\\/.m2.+\\)" jar)
    (let ((local-jar (concat (getenv "HOME")  (match-string 1 jar))))
      (if (file-exists-p local-jar) local-jar jar)))
   (t jar)))


(defun nrepl-find-resource (resource)
  (cond ((string-match "^file:\\(.+\\)" resource)
         (find-file (match-string 1 resource)))
        ((string-match "^\\(jar\\|zip\\):file:\\(.+\\)!/\\(.+\\)" resource)
         (let* ((jar (match-string 2 resource))
                (path (match-string 3 resource))
                (buffer-already-open (get-buffer (file-name-nondirectory jar))))
	   (find-file (nrepl-maybe-local-m2-resource jar))
           (goto-char (point-min))
           (search-forward path)
           (let ((opened-buffer (current-buffer)))
             (archive-extract)
             (when (not buffer-already-open)
               (kill-buffer opened-buffer)))))
        (:else (error "Unknown resource path %s" resource))))

;; with 0.1.5
;;  /Users/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar

;; with the latest master:
;;  /plink:klang@192.168.1.6:/Users/klang/projects/repl-tests/src/repl_tests/Users/klang/.m2/repository/org/clojure/clojure/1.4.0/clojure-1.4.0.jar

(defun nrepl-find-resource (resource)
  (cond ((string-match "^file:\\(.+\\)" resource)
         (nrepl-find-file (match-string 1 resource)))
        ((string-match "^\\(jar\\|zip\\):file:\\(.+\\)!/\\(.+\\)" resource)
         (let* ((jar (match-string 2 resource))
                (path (match-string 3 resource))
                (buffer-already-open (get-buffer (file-name-nondirectory jar))))
           (nrepl-find-file (nrepl-maybe-local-m2-resource jar))
           (goto-char (point-min))
           (search-forward path)
           (let ((opened-buffer (current-buffer)))
             (archive-extract)
             (when (not buffer-already-open)
               (kill-buffer opened-buffer)))))
        (:else (error "Unknown resource path %s" resource))))
