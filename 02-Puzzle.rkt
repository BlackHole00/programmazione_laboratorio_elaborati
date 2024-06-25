;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname 31_01_23_Esercizio2) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss" "installed-teachpacks")) #f)))
; Esercizio 2: Puzzle

; Francesco Viciguerra - 31/10/23
;   (viciguerrafrancesco@gmail.com)
;   (166896@spes.uniud.it)
; In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/21-11-23b.pdf
; Teachpack: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/pages/examples/teachpack/drawings.ss

(set-puzzle-shift-step!)

; Definizioni delle varie parti formanti la croce
(define cross-part-1 larger-tile)
(define cross-part-2
  (shift-down (shift-right (half-turn larger-tile) 2) 1))
(define cross-part-3
  (shift-right smaller-tile 2))
(define cross-part-4
  (shift-down (shift-right (half-turn smaller-tile) 2) 5))

(define cross
  (glue-tiles
   (glue-tiles cross-part-1 cross-part-2)
   (glue-tiles cross-part-3 cross-part-4)))

; Definizioni delle varie parti formanti il quadrato
(define quad-part-1
  (shift-down (shift-right larger-tile 2) 1))
(define quad-part-2
  (shift-down (shift-right smaller-tile 2) 5))
(define quad-part-3
  (half-turn larger-tile))
(define quad-part-4
  (shift-right (half-turn smaller-tile) 2))

(define quad
  (glue-tiles
   (glue-tiles quad-part-1 quad-part-2)
   (glue-tiles quad-part-3 quad-part-4)))

cross
quad