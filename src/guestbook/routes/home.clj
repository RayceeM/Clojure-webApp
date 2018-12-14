(ns guestbook.routes.home
  (:require [compojure.core :refer :all]
            [guestbook.views.layout :as layout]
            [hiccup.form :refer :all]))

(defn show-guests[])
[:ul.guests
(for [{:keys [message name timestamp]}
[{:message "Hey work smart" :name "Racheal" :timestamp nil} 
{:message "Awesome stuff" :name "Getty" :timestamp nil }]]
[:li 
[:blockquote message]
[:p "-" ]])]
(defn home [$ [name message error]]
  (layout/common 
  [:h1 "Guestbook"]
  [:p "Welcome visitor"]
  [:p "error"]
  ;generate list of comments
  (show-guests)
  [:hr]
  (form-to [post "/"]
  [:p "Name:"]
  (text-field "name" name)
  [:p "Message:"]
  (:textarea {:rows 10 :cols 40} "message" message)
  [:br]
  (submit-button "comment"))))

(defroutes home-routes
  (GET "/" [] (home)))
