(ns procdoc.mobile.state
  (:require [reagent.core :as reagent]
            [procdoc.mobile.state-path :refer [root-path]]))

(defonce !state (reagent/atom
                 {:proc-list {:proc-ids ["proc-one" "proc-two" "proc-three"]}
                  :proc-player nil}))

(defonce root (root-path !state))
