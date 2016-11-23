(ns procdoc.mobile.state
  (:require [reagent.core :as reagent]))

(defonce !state (reagent/atom
                 {:proc-list {:proc-ids ["proc-one" "proc-two" "proc-three"]}}))

