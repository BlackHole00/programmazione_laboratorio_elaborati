;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname 22_11_23_Esercizio6) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss.1" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss.1" "installed-teachpacks")) #f)))
; Esercizio 6: L-Tessellation

; Francesco Viciguerra - 22/11/23
;   (viciguerrafrancesco@gmail.com)
;   (166896@spes.uniud.it)
; In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/21-11-23b.pdf
; Teachpack: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/pages/examples/teachpack/drawings.ss

; L-Tessellation: Note sul funzionamento
;
; Si nota che, per come e'stato codificato il teach-pack, la rotazione di
; elementi precedentemente translati non e'facilmente utilizzabile, in quanto
; la rotazione avviene con un centro (pivot point) che viene calcolato tenendo
; in considerazione anche lo spazio vuoto.
; Cio'significa che ruotare una singola tile a positione (10, 10) verso destra
; risulta, oltre in una rotazione, nello spostamento (indesiderato) della tile
; in posizione (0, 10), in quanto il pivot point viene assegnato a (5, 5).
;
; Questo rende l'algoritmo leggermente piu'complicato, in quanto e'si e'dovuto
; tenere in mente questa restrizione. Infatti tutte le tiles utilizzate sono
; state ruotate prima di essere translate, in modo da non perdere la giusta
; posizione. Per far cio'in maniera piu'facile sono state implementate alcune
; funzioni helper, per facilmente gestire la rotazione e translazione delle
; tile.
;
; Si nota inoltre che, nell'algoritmo, la funzione principale (i.e.
; L-tessellation-helper) non funziona utilizzando la dimensione della piastrella,
; ma contando l'iterazione attuale, quindi una conversione e'necessaria. Per far
; cio'basta utilizzare il logaritmo ed aggiungere uno. Ad esempio:
;   1 -> log_2(1) + 1 = 0 + 1 = 1 -> Per una piastrella di dimensioni 1 serve 1 iterazione.
;   2 -> log_2(2) + 1 = 1 + 1 = 2 -> Per una piastrella di dimensioni 2 servono 2 iterazioni.
;   4 -> log_2(4) + 1 = 2 + 1 = 3 -> Per una piastrella di dimensioni 4 servono 3 iterazioni.
; 

(set-tessellation-shift-step!)

; variante di glue-tiles che accetta 4 tile
(define glue-4-tiles          ; val: Tile
  (lambda (tile1 tile2 tile3 tile4) ; tile1, tile2, tile3, tile4: Tile
    (glue-tiles tile1
                (glue-tiles tile2
                            (glue-tiles tile3 tile4)))))

; helper per un piu'facile movimento di una tile
(define move-tile             ; val: Tile
  (lambda (tile x-translation y-translation) ; tile: Tile, x-translation y-translation: integer
    (shift-down (shift-right tile x-translation) y-translation)))

; helper per una piu'facile rotazione di una tile
(define rotate-tile           ; val: Tile
  (lambda (tessel rotation)   ; tessel: Tile, rotation: integer
    (let (
          (actual-rotation (remainder rotation 4)))
      (cond
        ((= actual-rotation 0) tessel)
        ((< actual-rotation 0) (quarter-turn-left (rotate-tile tessel (+ rotation 1))))
        ((> actual-rotation 0) (quarter-turn-right (rotate-tile tessel (- rotation 1))))))))

; Helper per roteare e translate una tile facilmente
(define rotate-and-move-tile  ; val: Tile
  (lambda (tile x-translation y-translation rotation) ; tile: Tile, x-translation, y-translation, rotation: integer
    (move-tile (rotate-tile tile rotation) x-translation y-translation)))

; helper per creare una tile dove lo si desidera facilmente
(define base-tessel           ; val: Tile
  (lambda (x-pos y-pos rotation) ; x-pos, y-pos, rotation: integer
    (rotate-and-move-tile L-tile x-pos y-pos rotation)))

(define L-Tessellation-helper ; val: Tile
  (lambda (n x-pos y-pos rotation) ; n, x-pos, y-pos, rotation: integer
    (let (
          (next-n (- n 1))
          (n-offset (expt 2 (- n 2)))
          (2n-offset (* 2 (expt 2 (- n 2)))))
      (if (= n 1)
          (base-tessel x-pos y-pos rotation)
          (rotate-and-move-tile
           (glue-4-tiles (L-Tessellation-helper next-n 0 0 0)
                         (L-Tessellation-helper next-n n-offset n-offset 0)
                         (L-Tessellation-helper next-n 2n-offset 0 1)
                         (L-Tessellation-helper next-n 0 2n-offset 3))
           x-pos y-pos rotation)))))

(define L-Tessellation        ; val: Tile
  (lambda (dimension)         ; dimension: integer
    (let (
          (iterations-required (+ (inexact->exact (floor (log dimension 2))) 1)))
      (L-Tessellation-helper iterations-required 0 0 0))))


(L-Tessellation 1)
(L-Tessellation 2)
(L-Tessellation 4)
(L-Tessellation 16)
