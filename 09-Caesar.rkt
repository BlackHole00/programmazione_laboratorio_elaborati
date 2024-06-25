;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname 09-Caesar) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss.1" "installed-teachpacks") (lib "hanoi.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss.1" "installed-teachpacks") (lib "hanoi.ss" "installed-teachpacks")) #f)))
; Esercizio 9: caesar

; Francesco Viciguerra - 19/12/23
;   (viciguerrafrancesco@gmail.com)
;   (166896@spes.uniud.it)
; In riferimento a: http://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/19-12-23.pdf

; Caesar: note sul funzionamento
;
; Note relative alla prima parte dell'esercizio:
;   Siccome l'alfabeto latino non contiene alcune lettere presenti anche nel
;   nostro alfabeto, e'stato necessario l'utilizzo di una codifica temporanea
;   delle lettere, in relazione alla tabella successiva:
;     1 2 3 4 5 6 7 8 9 1011121314151617181920212223242526<- numero lettera alfabeto normale
;     A B C D E F G H I J K L M N O P Q R S T U V W X Y Z  <- lettera alfabeto normale
;     A B C D E F G H I     L M N O P Q R S T   V   X      <- lettera alfabeto latino
;     1 2 3 4 5 6 7 8 9     101112131415161718  19  20     <- numero lettera alfabeto latino
;
;   Si nota che e'proprio per la discrepanza di numerazione che non e'possibile
;   applicare il cifrario di cesare spiegato a lezione (ci sarebbe la
;   possibilita' di ottenere lettere non esistenti nell'alfabeto latino, ovvero
;   le lettere J, K, U, W, Y e Z).
;
;   Il programma quindi applichera'le seguenti conversioni:
;     - codifica ascii in chiaro ==> codifica latina in chiaro
;     - criptazione di cesare    ==> codifica latina criptata
;     - codifica latina criptata ==> codifica ascii criptata
;   Notiamo che il processo di decrittazione usa le stesse conversioni.
;
;   Non viene spiegato nel dettaglio l'algoritmo di crittografia in quanto
;   e'gia'stato spiegato nel corso.
;
;   Si nota infine che e'stata implementata manualmente una funzione string-map,
;   che funziona in modalita'identica alla funzione map, ma permette di lavorare
;   direttamente sui caratteri della stringa, cosa che non sarebbe stata
;   possibile nel caso della funzione map, che avrebbe richiesto procedimenti di
;   conversione (da stringa a lista di caratteri e viceversa).
;
; Note relvative alla seconda parte:
;   La risoluzione dell'esercizio non consiste in niente meno che saper
;   interpretare la consegna. La quasi interita'dell'esercizio e'infatti
;   espresso in forma matematica nella consegna. L'unica difficolta'e'il solo
;   fatto che e'stato necessario intuire che l'implementazione della funzione H
;   richide due lambda annidati.
;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Implementazione prima parte
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define latin-alphabet-len 20)

; Implementazione della funzione map specifica per le stringhe
(define string-map            ; val: string
  (lambda (func str)          ; func: (char) -> char, str: string
    (cond
      ((string=? str "") "")
      (else (string-append
             (string (func (string-ref str 0)))
             (string-map func (substring str 1)))))))


; Converte un carattere in codifica ASCII in codifica LATIN
(define ascii->latin          ; val: char
  (lambda (c)                 ; c: char
    (let (
          (c-as-int (char->integer c)))
      (cond
        ((char<? c #\J) c) ; lo spazio viene incluso in questo case
        ((char<? c #\U) (integer->char (- c-as-int 2)))
        ((char<? c #\W) (integer->char (- c-as-int 3)))
        (else (integer->char (- c-as-int 4)))))))

; Converte un carattere in codifica LATIN in codifica ASCII
(define latin->ascii          ; val: char
  (lambda (c)                 ; c: char
    (let (
          (c-as-int (char->integer c)))
      (cond
        ((char<? c #\L) c)
        ((char<? c #\V) (integer->char (+ c-as-int 2)))
        ((char<? c #\X) (integer->char (+ c-as-int 3)))
        (else (integer->char (+ c-as-int 4)))))))

; Ritorna una lambda che permette di criptare un singolo carattere utilizzando
; il cifrario di Cesare con chiave n
(define caesar-encoder        ; val: (char) -> char
  (lambda (n)                 ; n: integer (chiave)
    (lambda (chr)
      (if (char=? #\space)
          chr
          (letrec (
                (latin-chr (ascii->latin chr))
                (latin-chr-as-int (char->integer latin-chr))
                (encoded-chr-as-int (+ latin-chr-as-int n)))
            (latin->ascii
             (integer->char
              (cond
                ((< encoded-chr-as-int 0) (+ encoded-chr-as-int latin-alphabet-len))
                ((>= encoded-chr-as-int latin-alphabet-len) (- encoded-chr-as-int latin-alphabet-len))
                (else encoded-chr-as-int)))))))))

; Codifica una stringa con il cifrario di Cesare
(define encode-caesar         ; val: string
  (lambda (str n)             ; str: string, n: integer (chiave)
    (string-map (caesar-encoder n) str)))

; Decodifica una stringa con il cifrario di Cesare
(define decode-caesar         ; val: string
  (lambda (str n)             ; str: string, n: integer (chiave)
    (encode-caesar str (- n))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Implementazione seconda parte
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; funzione successivo
(define s2
  (lambda (u v)
    (+ v 1)))

; funzione identita'
(define i identity)

; funzione zero
(define z (lambda (x) 0))

; funzione uno
(define u (lambda (x) 1))

; funzione H
(define H                     ; val: (integer, integer) -> integer
  (lambda (f g)               ; f: (integer) -> integer, g: (integer, integer) -> integer
    (lambda (m n)
      (if (= n 0)
          (f m)
          (g m ((H f g) m (- n 1)))))))

; funzione add
(define add (H i s2))
; funzione mul
(define mul (H z add))
; funzione pow
(define pow (H u mul))


;;;;;;;;;;;;;;;;;
; Test generali
;;;;;;;;;;;;;;;;;

(encode-caesar "ALEA IACTA EST IVLIVS CAESAR DIXIT" 3)
(decode-caesar "DOHD NDFAD HXA NBONBX FDHXDV GNCNA" -3)

(add 2 40)
(add 22 20)
(add 18 24)
(mul 2 21)
(mul 3 14)
(mul 6 7)
(pow 2 8)
(pow 4 2)
(pow 1 256)