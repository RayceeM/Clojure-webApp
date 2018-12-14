(ns guestbook.routes.home
  (:require [compojure.core :refer :all]
            [guestbook.views.layout :as layout]
            [hiccup.form :refer :all]))

(defn show-guests[]
[:ul.guests
(for [{:keys [message name timestamp]}
[{:message "Hey work smart" :name "Racheal" :timestamp nil} 
{:message "Awesome stuff" :name "Getty" :timestamp nil }]]
[:li 
[:blockquote message]
[:p "-" [:cite name]]
[:time timestamp]])])

(defn home [ & [name message error]]
  (layout/common 
  [:h1 "Guestbook"]
  [:p "Welcome visitor"]
  [:p error]
  ;generate list of comments
  (show-guests)
  [:hr]
  (form-to [:post "/"]
  [:p "Name:"]
  (text-field "name" name)
  [:p "Message:"]
  (text-area {:rows 10 :cols 40} "message" message)
  [:br]
  (submit-button "comment"))))

  (defn save-message [name message]
    (cond 
      (empty? name)
      (home name message "some dude forgot his/her name oops!")
      (empty? message)
      (home name message "And he/she didn't leave a message..sad")
      :else
      (do
        (println name message)(home))))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" [name message] (save-message name message)))
