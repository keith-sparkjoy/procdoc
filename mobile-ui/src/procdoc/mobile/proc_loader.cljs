(ns procdoc.mobile.proc-loader
  (:require
   [procdoc.mobile.state-path :refer [child-path assoc-state!]]))

(defn simulate-download-completion
  [sp proc-id]
  (fn []
    (.log js/console ":) Download complete")
    (assoc-state! sp {:proc {:steps [{:sd "Step one"}
                                     {:sd "Step two"}
                                     {:sd "Step three"}]}})))

(defn load-proc!
  [sp proc-id]
  (assoc-state! sp {:loading proc-id})
  (js/setTimeout (simulate-download-completion sp proc-id) 1000)
  )
