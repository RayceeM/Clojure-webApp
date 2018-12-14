(ns guestbook.models.db
(:require [clojure.java.jdbc :as sql])
(:import  java.sql.DriverManager))

(def db {:classname "org.sqlite.JDBC",
        :subprotocol "sqlite"
        :subname "db.sq3" } )

(def guestbook-table-ddl
  (sql/create-table-ddl
      :guestbook
      [[:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
       [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
       [:name "TEXT"] 
       [:message "TEXT"]]))

(defn create-guestbook-table []
  (sql/db-do-commands db
                     [guestbook-table-ddl
                      "CREATE INDEX timestamp_index ON guestbook(timestamp)"]))

; (defn create-guestbook-table []
;   (sql/with-db-connection 
;     db
;     (sql/create-table-ddl
;       :guestbook
;       [[:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
;        [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
;        [:name "TEXT"] 
;        [:message "TEXT"]])
;     (sql/db-do-commands "CREATE INDEX timestamp_index ON guestbook(timestamp)")))

(defn read-guests []
(sql/query db ["SELECT * FROM guestbook ORDER BY timestamp DESC"]))

(defn save-message[name message]
(sql/insert! db :guestbook [:name :message :timestamp]
[name message (new java.util.Date)]))