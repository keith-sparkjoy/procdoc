(ns procdoc.mobile.my-navigator
  (:require [reagent.core :as reagent]
            [procdoc.mobile.scenes :refer [renderer-for]]
            [procdoc.mobile.react-classes :refer
             [navigator navigation-bar touchable-highlight text view]]))

(defn parse
  [x]
  (js->clj x :keywordize-keys true))

(defn left-button
  [route nav _ _]
  (let [route (js->clj route :keywordize-keys true)]
    (when (not= 0 (:index route))
      (reagent/as-element
       [touchable-highlight
        {:on-press #(.pop nav)}
        [text
         {:style {:color "#FFF"}}
         "Back"]]))))

(defn title
  [route _ _ _]
  (let [route (parse route)]
    (reagent/as-element
     [text
      {:style {:color "#FFF"}}
      (:title route)])))

(defn right-button
  [_ _ _ _]
  (reagent/as-element
   [text
    {:style {:color "#FFF"}}
    "Done"]))

(defn my-navigator
  []
  [navigator
   {:initial-route (clj->js {:title "Processes" :id "proc-list"})
    :style {:background-color "#FFA"}
    :render-scene (fn [route nav]
                    (let [route (parse route)
                          renderer (renderer-for (:id route))]
                      (reagent/as-element
                       [view
                        {:style {:top 40}}
                        (renderer route nav)])))
    :navigation-bar (reagent/as-element
                     [navigation-bar
                      ;; Note the use of PascalCase in these selectors
                      ;; :LeftButton works, but :left-button does not
                      {:route-mapper {:LeftButton left-button
                                      :Title title
                                      :RightButton right-button}
                       :style {:background-color "#333"
                               :height 40}}])}])
