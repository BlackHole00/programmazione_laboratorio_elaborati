;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname 08-Hanoi) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss.1" "installed-teachpacks") (lib "hanoi.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss.1" "installed-teachpacks") (lib "hanoi.ss" "installed-teachpacks")) #f)))
; Esercizio 8: Hanoi

; Francesco Viciguerra - 05/12/23
;   (viciguerrafrancesco@gmail.com)
;   (166896@spes.uniud.it)
; In riferimento a: http://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/05-12-23.pdf
; Teachpack: http://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/code/teachpack/hanoi.ss

; In questo esercizio e'stata implementato un'algoritmo di rosoluzione del gioco
; della torre di Hanoi in maniera simile a quella spiegata in classe, ma
; utilizzando la ricorsione di coda.
;
; Il trucco per risolvere questo problema non e'altro che il capire i giusti
; movimenti tra le asticelle tra un movimento ed un altro, come spiegato
; nella consegna.
;

; Funzione helper di hanoi-disks
(define hanoi-disks-helper    ; val: list of integer
  (lambda (disks-number move-number src dest temp src-val dest-val temp-val) ; disk-number, move-number, src, dest, temp, src-val, dest-val, temp-val: integer
    (let (
          (greater? (>= move-number (expt 2 (- disks-number 1))))
          (next-disks-number (- disks-number 1))
          (next-move-number (- move-number (expt 2 (- disks-number 1)))))
      (cond
        ((= disks-number 0) (list (list src src-val) (list dest dest-val) (list temp temp-val)))
        (greater? (hanoi-disks-helper next-disks-number
                                      next-move-number
                                      temp
                                      dest
                                      src
                                      temp-val
                                      (+ dest-val 1)
                                      src-val))
        ((not greater?) (hanoi-disks-helper next-disks-number
                                            move-number
                                            src
                                            temp
                                            dest
                                            (+ src-val 1)
                                            temp-val
                                            dest-val))))))

; Ritorna la situazione del gioco della torre di hanoi alla mossa n-esima del
; giocatore
(define hanoi-disks           ; val: list of integer
  (lambda (disks-number move-number) ; disks-number, move-number: integer
    (hanoi-disks-helper disks-number move-number 1 2 3 0 0 0)))

; Helper di hanoi-picture
(define hanoi-picture-helper  ; val: Picture
  (lambda (disks-number move-number src dest temp src-val dest-val temp-val original-disks-number image) ; disk-number, move-number, src, dest, temp, src-val, dest-val, temp-val: integer
    (let (
          (greater? (>= move-number (expt 2 (- disks-number 1))))
          (next-disks-number (- disks-number 1))
          (next-move-number (- move-number (expt 2 (- disks-number 1)))))
      (let (
            (current-image (if greater?
                               (disk-image disks-number
                                           original-disks-number
                                           dest
                                           dest-val)
                               (disk-image disks-number
                                           original-disks-number
                                           src
                                           src-val))))
        (cond
          ((= next-disks-number 0) (above current-image image))
          (greater? (hanoi-picture-helper next-disks-number
                                          next-move-number
                                          temp
                                          dest
                                          src
                                          temp-val
                                          (+ dest-val 1)
                                          src-val
                                          original-disks-number
                                          (above current-image image)))
          ((not greater?) (hanoi-picture-helper next-disks-number
                                                move-number
                                                src
                                                temp
                                                dest
                                                (+ src-val 1)
                                                temp-val
                                                dest-val
                                                original-disks-number
                                                (above current-image image))))))))




; Ritorna la situazione del gioco della torre di hanoi alla mossa n-esima del
; giocatore in modo visuale
(define hanoi-picture         ; val: Picture
  (lambda (disks-number move-number) ; disks-number, move-number: integer
    (hanoi-picture-helper disks-number move-number 1 2 3 0 0 0 disks-number (towers-background disks-number))))

(hanoi-disks 3 0)
(hanoi-disks 3 1)
(hanoi-disks 3 2)
(hanoi-disks 3 3)
(hanoi-disks 3 4)
(hanoi-disks 3 5)
(hanoi-disks 3 6)
(hanoi-disks 3 7)
(hanoi-disks 5 13)
(hanoi-disks 15 19705)
(hanoi-disks 15 32767)

(hanoi-picture 5 0)
(hanoi-picture 5 13)
(hanoi-picture 5 22)
(hanoi-picture 5 31)
(hanoi-picture 15 19705)