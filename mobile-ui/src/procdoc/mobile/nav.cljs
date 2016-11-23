(ns procdoc.mobile.nav
  (:require [reagent.core :as reagent]
   [procdoc.mobile.react-classes :refer
             [navigator navigation-bar touchable-highlight text]]))

(defn left-button
  [route nav _ _]
  (let [route (js->clj route :keywordize-keys true)]
    (when (not= 0 (:index route))
      (reagent/as-element
       [touchable-highlight
        {:on-press #(.pop nav)}
        [text "Back"]]))))

(defn my-navigator
  []
  (let [routes (clj->js [{:title "First Scene" :index 0}
                         {:title "Second Scene" :index 1}])]
    [navigator
     {:initial-route (aget routes 0)
      :initial-route-stack routes
      :style {:padding 100}
      :render-scene (fn [route nav]
                      (let [route (js->clj route :keywordize-keys true)]
                        (reagent/as-element
                         [touchable-highlight
                          {:on-press (fn []
                                       (if (= 0 (:index route))
                                         (.push nav (clj->js {:title "Whoa!" :index 1}))
                                         (.pop nav)))}
                          [text "Hello " (:title route)]])))
      :navigation-bar (reagent/as-element
                       [navigation-bar
                        ;; Note the use of PascalCase in these selectors
                        ;; :LeftButton works, but :left-button does not
                        {:route-mapper {:LeftButton left-button
                                        :RightButton (fn [_ _ _ _]
                                                       (reagent/as-element
                                                        [text "Done"]))
                                        :Title (fn [_ _ _ _]
                                                 (reagent/as-element
                                                  [text "Awesome Nav Bar"]))
                                        }
                         :style {:background-color "gray"}}])
      }]))
