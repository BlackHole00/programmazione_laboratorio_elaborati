;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname 28_11_23_Esercizio7) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss" "installed-teachpacks")) #f)))
; Esercizio 7: sorted-list

; Francesco Viciguerra - 28/11/23
;   (viciguerrafrancesco@gmail.com)
;   (166896@spes.uniud.it)
; In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/28-11-23.pdf

; sorted-list: note sul funzionamento
;
; Si nota che, per l'implementazione della funzione sorted-ins, non sono state
; utilizzate le funzioni belong? e position precedentemente implementate.
;
; Si potrebbe pensare che il controllo preliminare della presenza dell'elemento
; da inserire nella lista possa portare ad una piu'rapida soluzione (in quanto
; se l'elemento e'gia'presente nella lista non occorre fare nulla), tuttavia
; la funzione belong?, come sorted-ins, itera la lista. Un eventuale controllo
; preliminare quindi richiederebbe una iterazione in piu', che non e' piu'
; efficace, ma, anzi, e'cosa piu'lenta, specialmente nel caso l'elemento non sia
; presente nella lista.
;

; Converte un qualsiasi valore in una lista di un elemento.
(define to-list               ; val: list of anys
  (lambda (any)               ; any: any
    (cons any null)))

; Restituisce vero se un numero appartiene ad una lista.
(define belong?               ; val: boolean
  (lambda (elem list)         ; elem: number, list: list of numbers
    (cond
      ((null? list) #false)
      ((= elem (car list)) #true)
      (else (belong? elem (cdr list))))))

; Ritorna la posizione di un numero in una lista. Si presuppone che tale numero
; faccia parte della lista.
(define position              ; val: number
  (lambda (elem list)         ; elem: number, list: list of numbers
    (if (= elem (car list)) ; Non controlliamo per (null? list), siccome assumiamo che elem sia elemento della lista
        0
        (+ 1 (position elem (cdr list))))))

; Inserisce in modo ordinato un elemento in una lista. Se l'elemento e'gia'
; esistente quest'ultimo non verra'inserito una seconda volta. Se l'elemento e'
; gia'esistente si presuppone che sia gia'nella posizione giusta.
(define sorted-ins            ; val: list of numbers
  (lambda (elem list)         ; elem: number, list: list of numbers
    (if (null? list)
        (to-list elem)
        (let (
              (current-elem (car list))
              (next-list (cdr list)))
          (cond
            ((> elem current-elem) (cons current-elem (sorted-ins elem next-list)))
            ((= elem current-elem) list)
            (else (cons elem list))))))) ; (< elem current-elem)

; Riordina una lista ed elimina eventuali elementi doppi.
(define sorted-list           ; val: list of numbers
  (lambda (list)              ; list: list of numbers
    (let (
          (next-list (cdr list))
          (current-elem (car list)))
      (if (null? next-list)
          list
          (sorted-ins current-elem (sorted-list next-list))))))

(belong? 18 '())
(belong? 18 '(5 7 10 18 23))
(belong?  18 '(5 7 10 12 23))

(position 7 '(7 8 24 35 41))
(position 35 '(7 8 24 35 41))
(position 41 '(7 8 24 35 41))

(sorted-ins 24 '())
(sorted-ins 5 '(7 8 24 35 41))
(sorted-ins 24 '(7 8 24 35 41))
(sorted-ins 27 '(7 8 24 35 41))

(sorted-list '(35 8 41 24 7))
(sorted-list '(35 24 8 41 24 7))
        
        