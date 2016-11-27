(ns procdoc.mobile.proc-list
  (:require
   [procdoc.mobile.proc-loader :refer [load-proc!]]
   [procdoc.mobile.nav-helper :refer [push]]
   [procdoc.mobile.state-path :refer [child-path get-state assoc-state!]]
   [procdoc.mobile.react-classes :refer
    [text view touchable-highlight]]))

(defn nav-to-proc
  [nh parent-sp proc-id]
  (fn []
    (let [sp (child-path parent-sp :proc-player)]
      (load-proc! sp proc-id)
      (push nh {:title "Process" :id "proc-player"}))))

(defn proc-list
  [nh parent-sp]
  [view
   (let [sp (child-path parent-sp :proc-list)
         {:keys [proc-ids]} (get-state sp)]
     (for [id proc-ids]
       [touchable-highlight
        {:on-press (nav-to-proc nh parent-sp id)}
        [text id]
        ]))])
