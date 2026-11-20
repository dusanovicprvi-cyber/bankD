(ns bank.core
  (:import (java.time LocalDateTime)
           (java.time.format DateTimeFormatter)))
;
;;v1.0
;(def person-a
;  (ref {:owner "Dule"
;        :amount 500}))
;
;(def person-b
;  (ref {:owner "Mihajilo"
;        :amount 1000}))
;
;(defn transver
;  [from to amaunt]
;  (dosync
;    (when (and (pos? amaunt) (<= amaunt (:amount @from)))
;      (alter from update :amount - amaunt)
;      (alter to update :amount + amaunt) )
;    )
;  )


;v1.1
;(def person-a
;  (ref {:owner "Dule"
;        :amount 500}))
;
;(def person-b
;  (ref {:owner "Mihajilo"
;        :amount 1000}))
;
;(defn transver
;  [from to amaunt]
;  (dosync
;    (let [old-from @from
;          old-to   @to]
;
;     (when (and (pos? amaunt) (<= amaunt (:amount @from)))
;
;       (alter from update :amount - amaunt)
;       (alter to update :amount + amaunt))
;
;     {:from {:owner (:owner old-from) :state {:old (:amount old-from)
;                                              :new (:amount @from)}}
;      :to {:owner (:owner old-to) :state {:old (:amount old-to)
;                                          :new (:amount @to)}}
;     }
;    )
;    ) )

;v1.2
;(import '[java.time LocalDateTime]
;        '[java.time.format DateTimeFormatter])
;(defn corent-time []
; (.format
;  (LocalDateTime/now)
;  (DateTimeFormatter/ofPattern "dd.MM.yyyy HH:mm:ss")))
;
;(def person-a
;  (ref {:owner "Dule"
;        :amount 500}))
;
;(def person-b
;  (ref {:owner "Mihajilo"
;        :amount 1000}))
;
;(defn transver
;  [from to amaunt]
;  (dosync
;    (let [old-from @from
;          old-to   @to]
;
;      (when (and (pos? amaunt) (<= amaunt (:amount @from)))
;
;        (alter from update :amount - amaunt)
;        (alter to update :amount + amaunt))
;
;      {:amount amaunt
;       :from {:owner (:owner old-from) :state {:old (:amount old-from)
;                                               :new (:amount @from)}}
;       :to {:owner (:owner old-to) :state {:old (:amount old-to)
;                                           :new (:amount @to)}}
;       :transver-time (corent-time)
;       }
;      )
;    ) )

;;v1.3
;(import '[java.time LocalDateTime]
;        '[java.time.format DateTimeFormatter])
;(defn corent-time []
;  (.format
;    (LocalDateTime/now)
;    (DateTimeFormatter/ofPattern "dd.MM.yyyy HH:mm:ss")))
;
;(def person-a
;  (ref {:owner "Dule"
;        :amount 500}))
;
;(def person-b
;  (ref {:owner "Mihajilo"
;        :amount 1000}))
;
;(defn transver
;  [from to amaunt]
;  (dosync
;    (let [old-from @from
;          old-to   @to]
;
;      (if (and (pos? amaunt) (<= amaunt (:amount @from)))
;        (do
;          (alter from update :amount - amaunt)
;          (alter to update :amount + amaunt)
;
;          {:amount amaunt
;           :from {:owner (:owner old-from) :state {:old (:amount old-from)
;                                                   :new (:amount @from)}}
;           :to {:owner (:owner old-to) :state {:old (:amount old-to)
;                                               :new (:amount @to)}}
;           :transver-time (corent-time)
;           :status "SUCCESS"
;           }
;        )
;        (do
;          {:amount amaunt
;           :from {:owner (:owner old-from) :state @from}
;           :to {:owner (:owner old-to) :state @to}
;           :transver-time (corent-time)
;           :status "DENIED"
;           }
;        )
;
;      )
;    )
;  ) )

;v1.4
;(import '[java.time LocalDateTime]
;        '[java.time.format DateTimeFormatter])
;(defn corent-time []
;  (.format
;    (LocalDateTime/now)
;    (DateTimeFormatter/ofPattern "dd.MM.yyyy HH:mm:ss")))
;
;(def person-a
;  (ref {:owner "Dule"
;        :amount 500}))
;
;(def person-b
;  (ref {:owner "Mihajilo"
;        :amount 1000}))
;
;(defn valid-transation?
;  [from amount]
;  (and (pos? amount) (<= amount (:amount @from))))
;
;(defn transfer-money!
;  [amount from to]
;  (alter from update :amount - amount)
;  (alter to update :amount + amount))
;
;
;(defn state-update
;  [old-st new-st]
;   (if (not= old-st new-st)
;     {:owner (:owner old-st)
;      :state {:old (:amount old-st)
;              :new (:amount new-st)}}
;
;     {:owner (:owner old-st)
;      :state (:amount old-st)}
;     )
;  )
;
;
;(defn transfer-review
;  [amount old-from new-from old-to new-to status]
;  {:amount amount
;   :status status
;   :from (state-update old-from new-from)
;   :to (state-update old-to new-to)
;   :transfer-time (corent-time)}
;  )
;
;(defn transver
;  [from to amaunt]
;  (dosync
;    (let [old-from @from
;          old-to   @to]
;
;      (if (valid-transation? from amaunt)
;        (do
;          (transfer-money! amaunt from to)
;
;          (transfer-review amaunt old-from @from old-to @to "SUCCESS")
;          )
;        (transfer-review amaunt @from @from @to @to "DENIED")
;
;        )
;      )
;    ) )


;v1.5
(import '[java.time LocalDateTime]
        '[java.time.format DateTimeFormatter])
(defn corent-time []
  (.format
    (LocalDateTime/now)
    (DateTimeFormatter/ofPattern "dd.MM.yyyy HH:mm:ss")))

(def person-a
  (ref {:owner "Dule"
        :amount 500
        :a-overdraft 10000}))

(def person-b
  (ref {:owner "Mihajilo"
        :amount 1000}))

(defn available-amount
  [account]
  (+ (:amount account) (or (:a-overdraft account) 0)))

(defn valid-transation?
  [account amount]
  (and (pos? amount) (<= amount (available-amount account))))

(defn transfer-money!
  [amount from to]
  (alter from update :amount - amount)
  (alter to update :amount + amount))


(defn state-update
  [old-st new-st]
  (if (not= old-st new-st)
    {:owner (:owner old-st)
     :state {:old (:amount old-st)
             :new (:amount new-st)}}

    {:owner (:owner old-st)
     :state (:amount old-st)}
    )
  )

(defn transfer-review
  [amount old-from new-from old-to new-to status]
  {:amount amount
   :status status
   :from (state-update old-from new-from)
   :to (state-update old-to new-to)
   :transfer-time (corent-time)}
  )

(defn transver
  [from to amaunt]
  (dosync
    (let [old-from @from
          old-to   @to]

      (if (valid-transation? old-from amaunt)
        (do
          (transfer-money! amaunt from to)

          (transfer-review amaunt old-from @from old-to @to "SUCCESS")
          )
        (transfer-review amaunt @from @from @to @to "DENIED")

        )
      )
    ) )