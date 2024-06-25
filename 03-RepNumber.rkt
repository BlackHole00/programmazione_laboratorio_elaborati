;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname 07_01_23_Esercizio3) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss" "installed-teachpacks")) #f)))
; Esercizio 3: rep->number

; Francesco Viciguerra - 07/11/23
;   (viciguerrafrancesco@gmail.com)
;   (166896@spes.uniud.it)
; In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/07-11-23.pdf

; rep->number: Note sul funzionamento
;
; La procedura di conversione della rappresentazione di un numero avviene
; attraverso il normale procedimento matematico basato sulla potenza delle basi,
; come mostrato nel seguente esempio a base 3:
;   2310.2 (base 3) -> ? (base 10)
;   2310.2 (base 3) -> (2 * 3^3) + (3 * 3^2) + (3 * 3^1) +
;                      (0 * 3^0) + (2 * 3^(-1))
; Come si puo'notare la potenza applicata alla base della prima cifra e'data dal
; numero di cifre intere (prima del punto) meno 1. Ovvero:
;   Potenza per prima cifra = Numero di cifre intere - 1
;
; Si nota che per trovare il valore di una cifra nella sua base e'stata
; effettuata una ricerca nella base, per trovare l'indice della cifra
; desiderata.
;
; Infine si ricorda che per il processo di calcolo del valore di un numero in
; base binaria e'stato utilizzato lo stesso funzionamento delle basi generiche
; (utilizzando quindi la base "01").

; Ritorna l'ordine della base ("01" -> 2), equivalente a string-length
(define base-order string-length) ; val: integer ordine della base 

; Ritorna il valore di un simbolo in una base
(define digit_rep-val         ; val: integer 
  (lambda (digit-rep base)    ; digit-rep: char, base: string
    (let (
          (base-length (string-length base))
          (next-base (substring base 1))
          (current-base-char (string-ref base 0)))
      (if (or (char=? digit-rep current-base-char) (= base-length 1))
          0
          (+ 1 (digit_rep-val digit-rep next-base))))))

; Ritorna se la stringa ha un segno
(define rep-signed?           ; val: boolean
  (lambda (rep)               ; rep: string
    (let (
          (first-char (string-ref rep 0)))
        (or (char=? first-char #\+) (char=? first-char #\-)))))

; Ritorna se la rappresentazione rappresenta un numero negativo
(define rep-negative?         ; val: boolean
  (lambda (rep)               ; rep: string
    (let (
        (first-char (string-ref rep 0)))
      (if (rep-signed? rep)
          (char=? first-char #\-)
          false))))

; Toglie l'eventuale segno da una rappresentazione
(define rep-trim-sign         ; val: string
  (lambda (rep)               ; rep: string
    (if (rep-signed? rep)
        (substring rep 1)
        rep)))

(define rep-count-integer-digits-helper ; val: integer
  (lambda (rep)               ; rep: string
    (let (
          (current-digit (string-ref rep 0))
          (rep-length (string-length rep))
          (next-rep (substring rep 1)))
      (if (char=? current-digit #\.)
          0
          (if (= rep-length 1)
              1
              (+ 1 (rep-count-integer-digits next-rep)))))))

; Ritorna il numero di cifre intere (prima della virgola) presenti nella
; rappresentazione
(define rep-count-integer-digits ; val: integer
  (lambda (rep)               ; rep: string
    (rep-count-integer-digits-helper (rep-trim-sign rep))))

(define rep->number-helper    ; val: integer (il valore della rappresentazione)
  (lambda (base rep n)        ; base: string, rep: string, n: integer
    (let (
          (current-digit (string-ref rep 0))
          (rep-length (string-length rep))
          (next-rep (substring rep 1)))
      (let (
            (current-digit-val (if (char=? current-digit #\.)
                                   0
                                   (* (digit_rep-val current-digit base) (expt (base-order base) n)))))
        (if (= rep-length 1)
            current-digit-val
            (if (char=? current-digit #\.)
                (rep->number-helper base next-rep n)
                (+ current-digit-val (rep->number-helper base next-rep (- n 1)))))))))

; Ritorna il valore di un numero in una determinata rappresentazione in una base
(define rep->number           ; val: integer
  (lambda (base rep)          ; base: string, rep: string
    (let (
          (rep-val (rep->number-helper
                    base
                    (rep-trim-sign rep)
                    (- (rep-count-integer-digits rep) 1))))
      (if (rep-negative? rep)
          (- rep-val)
          rep-val))))

; Ritorna il valore di un numero rappresentato in base binaria
(define bin-rep->number       ; val: integer
  (lambda (rep)               ; rep: stringa di un numero in base binaria
    (rep->number "01" rep)))

(bin-rep->number "+1101")
(bin-rep->number "0")
(bin-rep->number "10110.011")
(bin-rep->number "-0.1101001")

(rep->number "zu" "-uuzz")
(rep->number "0123" "+21.1")
(rep->number "01234" "-10.02")
(rep->number "0123456789ABCDEF" "0.A")
(rep->number "0123456789ABCDEF" "1CF.0")
