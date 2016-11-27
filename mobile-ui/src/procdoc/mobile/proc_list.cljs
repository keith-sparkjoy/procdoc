(ns procdoc.mobile.proc-list
  (:require
   [procdoc.mobile.state-path :refer [child-path get-state]]
   [procdoc.mobile.react-classes :refer
    [text view touchable-highlight]]))

(defn proc-list
  [nh sp]
  [view
   (let [sp (child-path sp :proc-list)
         {:keys [proc-ids]} (get-state sp)]
     (for [id proc-ids]
       [text id]))])
