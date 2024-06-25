;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname 05_12_23_Esercizio8) (read-case-sensitive #t) (teachpacks ((lib "drawings.ss.1" "installed-teachpacks") (lib "hanoi.ss" "installed-teachpacks"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "drawings.ss.1" "installed-teachpacks") (lib "hanoi.ss" "installed-teachpacks")) #f)))
; Esercizio 8: Hanoi

; Francesco Viciguerra - 05/12/23
;   (viciguerrafrancesco@gmail.com)
;   (166896@spes.uniud.it)
; In riferimento a: 
; Teachpack: 

(define hanoi-moves           ; val: lista di coppie
  (lambda (n)                 ; n > 0 intero
    (hanoi-rec n 1 2 3)))

(define hanoi-rec             ; val: lista di coppie
  (lambda (n s d t)           ; n intero, s, d, t: posizioni
    (if (= n 1)
        (list (list s d))
        (let ((m1 (hanoi-rec (- n 1) s t d))
              (m2 (hanoi-rec (- n 1) t d s)))
          (append m1 (cons (list s d) m2))))))

(define hanoi-disks-helper
  (lambda (disks-number move-number src dest temp src-val dest-val temp-val)
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

(define hanoi-disks
  (lambda (disks-number move-number)
    (hanoi-disks-helper disks-number move-number 1 2 3 0 0 0)))

#| (define hanoi-picture-helper
  (lambda (disks-number move-number src dest temp src-val dest-val temp-val original-disks-number)
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
          ((= next-disks-number 0) current-image)
          (greater? (above (hanoi-picture-helper next-disks-number
                                         next-move-number
                                         temp
                                         dest
                                         src
                                         temp-val
                                         (+ dest-val 1)
                                         src-val
                                         original-disks-number)
                           current-image))
          ((not greater?) (above (hanoi-picture-helper next-disks-number
                                                      move-number
                                                      src
                                                      temp
                                                      dest
                                                      (+ src-val 1)
                                                      temp-val
                                                      dest-val
                                                      original-disks-number)
                                current-image))))))) |#

(define hanoi-picture-helper
  (lambda (disks-number move-number src dest temp src-val dest-val temp-val original-disks-number image)
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
          ((= next-disks-number 0) current-image)
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




(define hanoi-picture
  (lambda (disks-number move-number)
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