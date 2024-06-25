;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname 22_11_23_Esercizio5) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss.1" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss.1" "installed-teachpacks")) #f)))
; Esercizio 5: Manhattan-3d

; Francesco Viciguerra - 22/11/23
;   (viciguerrafrancesco@gmail.com)
;   (166896@spes.uniud.it)
; In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/21-11-23.pdf

; Manhattan-3d: Note sul funzionamento
;
; Siccome non e'possibile muoversi in diagonale, in ogni caso ci sono sempre tre
; modi per avvicinarsi al punto (i, j, k) mantenendo la stessa distanza da esso.
; Sia (x, y, z) il punto attuale:
;   - avvicinamento nella dimensione I: (x, y, z) -> (x + 1, y, z)
;   - avvicinamento nella dimensione J: (x, y, z) -> (x, y + 1, z)
;   - avvicinamento nella dimensione K: (x, y, z) -> (x, y, z + 1)
;
; Applicando l'algoritmo ricorsivamente otteniamo che, in un determinato punto
; (x, y, z), il numero totale di percorsi con lunghezza minima per raggiungere
; il punto (i, j, k) e'dato dalla formula seguente:
;   N((x, y, z)) = se (x, y, z) != (i, j, k):
;                      N((x + 1, y, z)) + N((x, y + 1, z) + N((x, y, z + 1))
;                = se (x, y, z) = (i, j, k):
;                      1
;
; Notiamo che se il punto (x, y, z) e'uguale a (i, j, k), allora abbiamo trovato
; un percorso di lunghezza sicuramente minima.
;
; Si nota, inoltre che, per motivi si semplicita'della spiegazione, i seguenti
; casi sono stati ignorati, ma vengono comunque controllati nel codice:
;   - x = i
;   - y = j
;   - z = k
;
; L'algoritmo, infine utilizza una logica inversa, quindi al contrario, ma
; comunque lavorando in maniera analoga.

(define manhattan-3d          ; val: number
  (lambda (i j k)             ; i, j, k: number
    (let (
          (next-i (- i 1))
          (next-j (- j 1))
          (next-k (- k 1))
          (i-has-next? (not (zero? i)))
          (j-has-next? (not (zero? j)))
          (k-has-next? (not (zero? k)))
          (terminated (and (zero? i) (zero? j) (zero? k))))
      (if terminated
          1
          (let (
                (next-man-i (if i-has-next?
                           (manhattan-3d next-i j k)
                           0))
                (next-man-j (if j-has-next?
                           (manhattan-3d i next-j k)
                           0))
                (next-man-k (if k-has-next?
                           (manhattan-3d i j next-k)
                           0)))
            (+ next-man-i next-man-j next-man-k))))))
  
(manhattan-3d 0 0 7)
(manhattan-3d 2 0 2)
(manhattan-3d 1 1 1)
(manhattan-3d 1 1 5)
(manhattan-3d 2 3 1)
(manhattan-3d 2 3 3)