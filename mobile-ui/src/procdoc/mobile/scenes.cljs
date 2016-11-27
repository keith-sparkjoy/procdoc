(ns procdoc.mobile.scenes
  (:require
   [procdoc.mobile.react-classes :refer [text]]
   [procdoc.mobile.proc-list :refer [proc-list]]
   [procdoc.mobile.proc-player :refer [proc-player]]))

(defn default-renderer
  [nh sp]
  [text
   {:style {:background-color "#C00"
            :color "#FF0"
            :padding 10}}
   (str "No renderer for route: " (:route nh))])

(defn renderer-for
  [id]
  (case id
    "proc-list" proc-list
    "proc-player" proc-player
    default-renderer))
