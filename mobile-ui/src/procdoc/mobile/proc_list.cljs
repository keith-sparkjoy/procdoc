(ns procdoc.mobile.proc-list
  (:require
   [procdoc.mobile.state :refer [!state]]
   [procdoc.mobile.react-classes :refer
    [text view touchable-highlight]]))

(defn proc-list
  [route nav]
  [view
   (let [{:keys [proc-ids]} (:proc-list @!state)]
     (for [id proc-ids]
       [text id]))])
