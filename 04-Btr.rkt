;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname 14_11_23_Esercizio4_ver2) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss" "installed-teachpacks")) #f)))
; Esercizio 4: BTR

; Francesco Viciguerra - 14/11/23
;   (viciguerrafrancesco@gmail.com)
;   (166896@spes.uniud.it)
; In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/14-11-23.pdf

; BTR: Note sul funzionamento
;
; Prima di implementare la somma e'necessario considerare le cifre e stabilire
; un metodo per determinare il risultato dell'addizione tra di loro e quindi
; anche l'eventuale carry di tale operazione.
; Siccome un'addizione puo', a sua volta, ricevere il carry di un'altra, e'
; necessario implementare anche un'addizione a tre cifre ed un metodo per
; calcolarne il carry.
;
; Si nota che per la realizzazione del carry a 3 operandi viene utilizzata la
; seguente formula:
;   Carry(A, B, C) = Carry(A, B) + Carry(A + B, C)
; Notiamo che tale formula usa solamente operazioni di somma e carry a 2
; operandi.
;
; Per l'implementazione della somma vera e propria e'stato utilizzata la tecnica
; della somma in colonna, con carry iniziale '.'. Per ogni colonna verranno
; sommate le cifre corrispondenti del primo e del secondo numero con il carry
; della somma precedente, fino a quando il carry e''.' e non rimangono ulteriori
; cifre da sommare dei numeri.
; Per esempio si consideri il seguente caso:
;                 .        ..      -...      -...     <= Resto
;    +-.+ +    +-.+ +    +-.+ +    +-.+ +    +-.+ +   <= Primo addendo
;     -+- =     -+- =     -+- =     -+- =     -+- =   <= Secondo addendo
;   --------  --------  --------  --------  --------
;    ????                   .       ++.      .++.     <= Risultato
;
; Come si puo'notare, l'algoritmo funziona da destra verso sinistra, analizzando
; per prima la cifra meno significativa. Si puo'anche notare che il primo carry
; e''.'.

; Calcola la somma senza carry di 2 cifre in notazione ternaria
(define btr-digit-simple-sum  ; val:  char +/./-
  (lambda (u v)               ; u, v: char +/./-
    (cond
      ((char=? u #\.) v)
      ((char=? v #\.) u)
      ((not (char=? u v)) #\.) ; u=+, v=- oppure u=-, v=+
      ((char=? u #\+) #\-)     ; u=+, v=+
      (else #\+))))            ; u=-, v=-

; Calcola il carry della somma senza carry di 2 cifre in notazione ternaria
(define btr-simple-carry      ; val:  char +/./-
  (lambda (u v)               ; u, v: char +/./-
    (cond
      ((and (char=? u #\+) (char=? v #\+)) #\+)
      ((and (char=? u #\-) (char=? v #\-)) #\-)
      (else #\.))))

; Calcola la somma con carry di 2 cifre in notazione ternaria
(define btr-digit-sum         ; val:     carattere +/./-
  (lambda (u v c)             ; u, v, c: caratteri +/./-
    (btr-digit-simple-sum
     c
     (btr-digit-simple-sum u v))))

; Calcola il carry della somma di 2 cifre in notazione ternaria
(define btr-carry             ; val:     carattere +/./-
  (lambda (u v c)             ; u, v, c: caratteri +/./-
    (btr-digit-simple-sum
     (btr-simple-carry u v)
     (btr-simple-carry c (btr-digit-simple-sum u v)))))

; Ritorna la cifra meno significativa di un numero in notazione ternaria.
; Se la stringa non e'valida ritorna #\.
(define lsd                   ; val: char
  (lambda (num)               ; num: integer
    (let (
          (num-length (string-length num)))
      (if (= num-length 0)
          #\.
          (string-ref num (- num-length 1))))))

; Ritorna un numero in notazione ternaria senza la cifra meno significativa.
; Se la stringa non e'valida ritorna "".
(define head                  ; val: string
  (lambda (num)               ; num: integer
    (let (
          (num-length (string-length num)))
      (if (= num-length 0)
          ""
          (substring num 0 (- num-length 1))))))

; Ritorna la stringa normalizzata, quindi elimina tutti i punti in testa, tranne
; il primo.
(define normalized-btr        ; val: string 
  (lambda (num)               ; num: string
    (cond
      ((= (string-length num) 1) num) ; .
      ((char=? (string-ref num 0) #\.) (normalized-btr (substring num 1)))
      (else num))))

; Helper per effettuare la somma. Dati due numeri ed un carry iniziale, esegue
; la somma in colonna.
(define btr-sum-aux           ; val: string
  (lambda (n1 n2 carry)       ; n1, n2: string, carry: char
    (let (
          (d1 (lsd n1))
          (d2 (lsd n2))
          (next-n1 (head n1))
          (next-n2 (head n2)))
      (if (and (string=? n1 "") (string=? n2 ""))
          (string carry)
          (string-append (btr-sum-aux next-n1 next-n2 (btr-carry d1 d2 carry)) (string (btr-digit-sum d1 d2 carry)))))))

(define btr-sum               ; val: string
  (lambda (n1 n2)             ; n1, n2: string
    (normalized-btr (btr-sum-aux n1 n2 #\.))))

(btr-sum "-+--" "+")
(btr-sum "-+--" "-")
(btr-sum "+-.+" "-+.-")
(btr-sum "-+--+" "-.--")
(btr-sum "-+-+." "-.-+")
(btr-sum "+-+-." "+.+-")
    