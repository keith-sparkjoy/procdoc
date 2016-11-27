(ns procdoc.mobile.nav-helper)

(defrecord NavHelper [nav route])

(defn push
  [nh route]
  (.push (:nav nh) (clj->js route)))

(defn pop
  [nh]
  (.pop (:nav nh)))
