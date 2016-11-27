(ns procdoc.mobile.proc-player
  (:require
   [procdoc.mobile.nav-helper :refer [push]]
   [procdoc.mobile.state-path :refer [child-path get-state]]
   [procdoc.mobile.react-classes :refer
    [text view]]))

(defn loaded-proc
  [nh sp]
  [view
   (let [{:keys [proc]} (get-state sp)]
     (for [[i s] (map-indexed (fn [i s] [i s]) (:steps proc))]
       ^{:key i} [text (:sd s)]))])

(defn proc-player
  [nh parent-sp]
  (let [sp (child-path parent-sp :proc-player)]
    [view
     (if-let [proc-id (:loading (get-state sp))]
       [text "Loading " proc-id]
       [loaded-proc nh sp])]))
