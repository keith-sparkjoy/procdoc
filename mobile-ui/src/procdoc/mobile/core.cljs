(ns procdoc.mobile.core
  (:require [reagent.core :as reagent]
            [procdoc.mobile.nav :refer [my-navigator]]))

(enable-console-print!)

;; we need set! for advanced compilation

(set! js/React (js/require "react-native/Libraries/react-native/react-native.js"))
(defonce react (js/require "react-native/Libraries/react-native/react-native.js"))

;; Assets need to be relative path, starting from the `app/build/node_modules'
;; directory. The packager only finds images located in the `app/' folder
;; (the directory that contains package.json) or below.
;;
;; We use `defonce' to prevent errors on subsequent reloads.

(defonce !state (reagent/atom 0))

(defn root-view
  []
  [my-navigator])

(defn root-container
  "Wraps root-view. This is to make sure live reloading using boot-reload and
  reagent works as expected. Instead of editing root-container, edit root-view"
  []
  [root-view])

(defn ^:export main
  []
  (js/console.log "MAIN")
  (enable-console-print!)
  (.registerComponent (.-AppRegistry react)
                      "SimpleExampleApp"
                      #(reagent/reactify-component #'root-container)))

(defn on-js-reload
  []
  (println "on-js-reload. state:" (pr-str @!state))

  ;; Force re-render
  ;;
  ;; In React native, there are no DOM nodes. Instead, mounted
  ;; components are identified by numbers. The first root components
  ;; is assigned the number 1.

  (reagent/render #'root-container 1))
