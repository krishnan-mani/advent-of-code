(ns advent-of-code.core)

(defn print-foo [str printStream]
  (.println printStream str))

(defn -main
  [& args]
  (print-foo (last args) System/out) 
  (println "adventofcode.com"))
