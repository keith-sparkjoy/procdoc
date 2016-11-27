(ns procdoc.mobile.state-path)

(defrecord StatePath [root path])

(defn root-path
  [a]
  (map->StatePath {:root a}))

(defn child-path
  [{:keys [root path]} key]
  (map->StatePath {:root root :path (conj path key)}))

(defn get-state
  [{:keys [root path]}]
  (if (nil? path)
    @root
    (get-in @root path)))

(defn update-state!
  [{:keys [root path]} f & args]
  (swap! root #(update-in % path f args)))
