(ns procdoc.core
  (:require '[clojure.spec :as s]))

(s/def ::id-type (s/and string? (partial re-matches #"^[-a-z]+$")))
(s/def ::text-type (s/and string? (partial re-matches #"^\w+$")))
(s/def ::short-text-type (s/and ::text-type #(<= (count %) 40)))
(s/def ::med-text-type (s/and ::text-type #(<= (count %) 140)))
(s/def ::long-text-type (s/and ::text-type #(<= (count %) 2048)))

(s/def ::id ::id-type)
(s/def ::aim ::med-text-type)
(s/def ::bb ::med-text-type)
(s/def ::eb ::med-text-type)
(s/def ::sd ::short-text-type)
(s/def ::md ::med-text-type)
(s/def ::ld ::long-text-type)
(s/def ::ap ::id-type)

;; procdoc currently supports three types of steps
;; 1. directive
;;    This is a "do something" step, like "Take out the trash."
;; 2. alt-proc-predicate
;;    This predicate asks a question to see if we should be using an alternative
;;    process, like "Have a grocery list?" If the answer is no, an ID for the
;;    alternate process is supplied so the user can easily navigate to it.
;; 3. corrective-predicate
;;    This predicate asks a question to see if the process is running okay, like
;;    "Have enough bags?" In the normal case the answer is yes, but if you answer
;;    no, the step includes possible corrective actions you can take to get to yes.
(s/def ::step-common-keys (s/keys :req [::sd]
                                  :opt [::md ::ld]))

;; directive
(s/def ::directive-step-type ::step-common-keys)

;; alternative process predicate
(s/def ::alt-proc-predicate-step-type (s/merge ::step-common-keys
                                               (s/keys :req [::ap])))
;; corrective predicate step
(s/def ::corrective-action-step ::step-common-keys)
(s/def ::pca (s/coll-of ::corrective-action-step))
(s/def ::corrective-predicate-step-type (s/merge ::step-common-keys
                                               (s/keys :req [::pca])))

(defmulti step-type ::st)
(defmethod step-type ::directive [_] ::directive-step-type)
(defmethod step-type ::alt-proc-predicate [_] ::alt-proc-predicate-step-type)
(defmethod step-type ::corrective-predicate [_] ::corrective-predicate-step-type)

(s/def ::step-type (s/multi-spec step-type ::st))

(s/def ::steps (s/coll-of ::step-type))

(s/def ::proc-type (s/keys :req [::id ::aim ::bb ::eb ::steps]))

(defn foo []
  #::{:id "prepare-for-grocery-shopping"
   :aim "Prepare to have a smooth shopping experience."
   :bb "You would like to go to the grocery store."
   :eb "You are ready to go to the grocery store."
   :steps [
           {:st :directive
            :sd "Bring grocery list."
            :md (str "Bringing a grocery list is a great way to "
                     "keep you focused on buying only the food that you need, "
                     "and avoid buying lots of junk food.")}

           {:st :alt-proc-predicate
            :sd "Have grocery list?"
            :md (str "If you don't have a grocery list, you're not ready to execute "
                     "this process yet. Make a grocery list first.")
            ;; alternate process
            :ap "make-grocery-list"}

           {:st :directive
            :sd "Bring grocery bags."
            :md (str "Gather up enough empty grocery bags to hold all of your "
                     " groceries, and put them in the car.")
            :ld (str "It's really easy to forget to bring reusable grocery bags to "
                     "the store in the US, because stores automatically assume you "
                     "want them to use disposable plastic or paper bags to hold your "
                     "groceries. Our family is making a small difference by choosing "
                     "to reuse grocery bags instead of following the current "
                     "cultural norm.")}

           {:st :corrective-predicate
            :sd "Have enough bags?"
            :md "Do you have enough grocery bags to hold your planned purchases?"
            ;; possible corrective actions
            :pca [{:st :directive
                   :sd "Search for the bags."
                   :md (str "See if you can find enough reusable grocery bags in your "
                            "house or in your vehicles.")}

                  {:st :directive
                   :sd "Add to grocery list."
                   :md (str "Add an item to your list to remind you to purchase "
                            "enough reusable bags so this won't happen in the "
                            "future.")}]}

           {:st :directive
            :sd "Make errand list."
            :md (str "You may not have any other errands to run, but it's worth "
                     "checking before you go out.")
            :ld (str "If you're going to get into the car to go to the grocery store, "
                     "you might be able to accomplish some other errands while out "
                     "and about. If you have no other errands, no worries! "
                     "If you did have other errands, you'll feel really good about "
                     "yourself by getting them done in one trip instead of multiple.")}

           {:st :directive
            :sd "Bring two payment methods."
            :md "Put your two payment methods into your pocket / wallet / purse."
            :ld (str "It's always good to have a plan B, in case your credit card "
                     "is declined because some fraudster skimmed it from a gas "
                     "station. And it's obviously good to have a plan A, so this step "
                     "helps ensure we have a way to pay for our groceries.")}]
   })
