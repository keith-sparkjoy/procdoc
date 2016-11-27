(ns procdoc.mobile.scenes
  (:require
   [procdoc.mobile.react-classes :refer [text]]
   [procdoc.mobile.proc-list :refer [proc-list]]))

(defn default-renderer
  [route nav sp]
  [text
   {:style {:background-color "#C00"
            :color "#0C0"
            :padding 10}}
   (str "No renderer for route: " route)])

(defn renderer-for
  [id]
  (case id
    "proc-list" proc-list
    default-renderer))
