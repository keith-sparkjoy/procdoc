(ns procdoc.mobile.react-classes
  (:require [reagent.core :as r]))

;; we need set! for advanced compilation

(set! js/React (js/require "react-native/Libraries/react-native/react-native.js"))
(defonce react (js/require "react-native/Libraries/react-native/react-native.js"))

(def view                (r/adapt-react-class (.-View               react)))
(def text                (r/adapt-react-class (.-Text               react)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight react)))
(def navigator           (r/adapt-react-class (.-Navigator          react)))

(def navigation-bar      (r/adapt-react-class (.-NavigationBar (.-Navigator react))))

