;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname 31_10_23_Esercizio1) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; Esercizio 1: Frase

; Francesco Viciguerra - 31/10/23
;   (viciguerrafrancesco@gmail.com)
;   (166896@spes.uniud.it)
; In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/31-10-23.pdf

(define noun-ends-with?       ; val: boolean
  (lambda (noun char)         ; noun: string, char: char
    (let (
          (last-char (string-ref noun (- (string-length noun) 1))))
      (char=? last-char char))))

(define noun-male?            ; val: boolean
  (lambda (noun)              ; noun: string
    (or (noun-ends-with? noun #\o) (noun-ends-with? noun #\i))))

(define noun-female?          ; val: boolean
  (lambda (noun)              ; noun: string
    (or (noun-ends-with? noun #\a) (noun-ends-with? noun #\e))))

(define noun-plural?          ; val: boolean
  (lambda (noun)              ; noun: string
    (or (noun-ends-with? noun #\e) (noun-ends-with? noun #\i))))

(define article-of            ; val: string
  (lambda (noun)              ; noun: string
    (if (noun-plural? noun)
        (cond
           ((noun-male? noun) "i")
           ((noun-female? noun) "le"))
        (cond
          ((noun-male? noun) "il")
          ((noun-female? noun) "la")))))

(define verd-root             ; val: string (radice del verbo (dormire -> dorm))
  (lambda (verb)              ; verb: string (verbo all'infinito)
    (let (
          (verb-len (string-length verb)))
    (substring verb 0 (- verb-len 3)))))

(define verb-last-3-chars?    ; val: boolean (compara gli ultimi 3 caratteri di un verbo)
  (lambda (verb eq)           ; verb: string, string
    (let (
          (last-verb-part (substring verb (- (string-length verb) 3))))
      (string=? last-verb-part eq))))

(define verb-first-form?      ; val: boolean
  (lambda (verb)              ; verb: string (verbo all'infinito)
    (verb-last-3-chars? verb "are")))

(define verb-second-form?     ; val: boolean
  (lambda (verb)              ; verb: string (verbo all'infinito)
    (verb-last-3-chars? verb "ere")))

(define verb-third-form?      ; val: boolean
  (lambda (verb)              ; verb: string (verbo all'infinito)
    (verb-last-3-chars? verb "ire")))

(define mutate-verb-form      ; val: string (verbo con forma modificata (solo ultimi 3 caratteri del verb in input cambiati))
  (lambda (verb new-form)     ; verb: string (verbo all'infinito), new-form: string (ultimi caratteri della forma del verbo)
    (let (
          (last-verb-part (substring verb 0 (- (string-length verb) 3))))
      (string-append last-verb-part new-form))))

(define correct-verb          ; val: string (verbo in forma corretta)
  (lambda (verb subject)      ; verb: string (verbo all'infinito), subject: string
    (if (noun-plural? subject)
        (if (verb-first-form? verb)
            (mutate-verb-form verb "ano")
            (mutate-verb-form verb "ono"))
        (if (verb-first-form? verb)
            (mutate-verb-form verb "a")
            (mutate-verb-form verb "e")))))

(define create-phrase         ; val: string (frase)
  (lambda (subject verb object) ; subject: string, verb: string (verbo all'infinito), object: string
    (string-append
     (article-of subject) " "
     subject " "
     (correct-verb verb subject) " "
     (article-of object) " "
     object)))

(define frase create-phrase)  ; alias of `create_phrase`

(frase "gatto" "cacciare" "topi")
(frase "mucca" "mangiare" "fieno")
(frase "sorelle" "leggere" "novella")
(frase "bambini" "amare" "favole")
(frase "musicisti" "suonare" "pianoforti")
(frase "cuoco" "friggere" "patate")
(frase "camerieri" "servire" "clienti")
(frase "mamma" "chiamare" "figlie")