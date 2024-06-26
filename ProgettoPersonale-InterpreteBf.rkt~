; Progetto personale: bf-interpreter

; Francesco Viciguerra
;   (viciguerrafrancesco@gmail.com)
;   (166896@spes.uniud.it)

; Questo progetto constiste nell'implementazione di un interprete per il
; linguaggio di programmazione brainf**k. Ne segue una generica spiegazione
;
; Il linguaggio brainf**k e'un linguaggio di programmazione esoterico, ovvero non
; ideato per essere utile o aiutare il programmatore, ma come sfida tecnica per
; l'ideatore stesso o i programmatori che hanno intenzione di cimentarsi nel
; provare ad usarlo.
;
; Il linguaggio brainf**k si basa sui seguenti comandi, che, utilizzando una
; lista di elementi (un nastro infinito) ed un puntatore (indice alla lista) e'
; in grado di esprimire qualsiasi algoritmo, in quanto turing complete.
; Quest'ultimo e'al quanto minimale e supporta solo 8 istruzioni, codificate
; dai seguenti caratteri:
;   - '+': incrementa il valore della cella puntata dal puntatore di uno
;            mem[i]++
;   - '-': decrementa il valore della cella puntata dal puntatore di uno
;            mem[i]--
;   - '>': incrementa il puntatore in modo da farlo puntare alla cella
;          successiva
;            i++
;   - '<': decrementa il puntatore in modo da farlo puntare alla cella
;          precedente
;            i--
;   - '[': se l'attuale cella puntata dal puntatore e'0, salta alla cella
;          successiva alla corrispondente parentesi chiusa. Altrimenti non
;          applica nessuna modifica
;            if (mem[i] == 0) {
;              jumpToCorrespondingClosedBracked+1;
;            }
;   - ']': se l'attuale cella puntata dal puntatore e'diversa da 0, salta alla
;          cella successiva alla corrispondente parentesi aperta. Altrimenti
;          non applica nessuna modifica
;            if (mem[i] != 0) {
;              jumoToCorrespondingOpenBracket+1;
;            }
;   - '.': stampa in output il valore contenuto nella cella puntata dal puntatore
;          interpretato come carattere ASCII
;            print(mem[i] as char)
;   - ',': salva nella cella puntata dal puntatore un carattere ricevuto dall'
;          utente in codifica ASCII
;            read(mem[i])
; Si nota che nel pseudo codice con mem viene intesa la lista infinita di
; elementi, mentre con i si intende il puntatore.
;
; Secondo una perfetta implementazione del linguaggio, il nastro dobrebbe essere
; di dimensione infinita ed ogni cella dovrebbe essere in grado di contentere
; valori infinitamente grandi. Tuttavia, sebbene questo sia facilmente
; implementabile, e'stato preferito implementare una versione di brainf**k simile
; a quella implementata dalla maggiorparte degli interpreti, ovvero dove:
;   - il nastro e'di lunghezza 65536. Una chiamata a '>' o '<' causante un over o
;     underflow comportera'il "wrapping" del puntatore (65535 + 1 = 0,
;     0 - 1 = 65536)
;   - ogni cella contiene valori ad 8 bit, ovvero nel range [0, 255]. Una
;     chiamata a '+' o '-' causante un over o underflow comportera'il "wrapping"
;     del valore contenuto all'interno della cella (255 + 1 = 0, 0 - 1 = 255)
;
; Dal punto di vista dell'implementazione, non e'stato usato uno stato globale,
; ma come spiegato a lezione, l'interita'dell'interprete e'implementata in
; maniera puramente funzionale. Non e'percio'possibile usare la ricorsione ad
; albero in quanto non e'possibile definire se o meno un programma e'in grado di
; terminare. Percio'e'stata utilizzata la ricorsione di coda.
; Ogni funzione che modifica il nastro lavora sulle seguenti variabili (che sono
; parametri della funzione stessa):
;   - memory: la lista (specificato precedentemente come mem)
;   - mem-pointer: il puntatore (specificato precedentemente come i)
;   - loop-counter: contenente il livello di "nesting" delle parentesi quadre,
;                   utilizzata per simplificare la risoluzione dei salti
;   - code: il sorgente che viene attualmente eseguito, codificato come stringa
;   - instruction-idx: l'indice del carattere che si sta attualmente eseguendo
;
; L'interprete e'in grado ci eseguire semplici programmi, come i seguenti:
;   - "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.": hello world
;   - ",[.,]": programma cat
;
#lang scheme

(define mem-size 65536)
(define cell-size 256)

; Crea una lista di lunghezza specificata
(define list-with-length     ; val: list of any
  (lambda (length default-val) ; length: integer, default-val: any
    (if (= length 0)
        null
        (cons default-val (list-with-length (- length 1) default-val)))))

; Modifica un elemento di una lista specificato da un indice
(define list-set             ; val: list of any
  (lambda (list idx val)     ; list: list of any, idx: integer, val: any
    (let (
          (next-list (cdr list))
          (first-list-elem (car list))
          (next-idx (- idx 1)))
    (cond
      ((null? list) (cons val null))
      ((= idx 0) (cons val next-list))
      (else (cons first-list-elem (list-set next-list next-idx val)))))))

(define memory-set list-set)

; Modifica un elemento della lista, incrementandolo di un valore diff, 
; accontando per possibili under ed overflow
(define memory-modify        ; val: list of integer
  (lambda (memory mem-pointer diff) ; memory: list of integer, mem-pointer: integer, diff: integer
    (let (
          (new-value (remainder (+ (list-ref memory mem-pointer) diff) cell-size)))
      (list-set memory mem-pointer new-value))))

; Ritorna l'indice della prossima istruzione
(define next-instruction-idx ; val: integer
  (lambda (instruction-idx)  ; instruction-idx: integer
    (+ instruction-idx 1)))

; Ritorna l'indice della precedente istruzione
(define prev-instruction-idx ; val: integer
  (lambda (instruction-idx)  ; instruction-idx: integer
    (- instruction-idx 1)))

; Esegue l'istruzione '+' o '-' allo stato attuale
(define apply+/-             ; val: integer
  (lambda (memory            ; memory: list of integer
           mem-pointer       ; mem-pointer: integer
           loop-counter      ; loop-counter: integer
           code              ; code: string
           instruction-idx   ; instruction-idx: integer
           is-plus)          ; is-plus: bool, true se '+', false se '-'
    (let (
          (mem-diff (if is-plus 1 -1)))
      (eval-helper (memory-modify memory mem-pointer mem-diff)
                   mem-pointer
                   loop-counter
                   code
                   (next-instruction-idx instruction-idx)))))

; Esegue l'istruzione '<' o '>' allo stato attuale
(define apply>/<             ; val: integer
  (lambda (memory            ; memory: list of integer
           mem-pointer       ; mem-pointer: integer
           loop-counter      ; loop-counter: integer
           code              ; code: string
           instruction-idx   ; instruction-idx: integer
           is-next)          ; is-next: bool, true se '>', false se '<'
    (let (
          (next-mem-pointer (remainder (+ mem-pointer (if is-next 1 -1) mem-size) mem-size)))
      (eval-helper memory
                   next-mem-pointer
                   loop-counter
                   code
                   (next-instruction-idx instruction-idx)))))

; Esegue l'istruzione '.' allo stato attuale
(define apply.               ; val: integer
  (lambda (memory            ; memory: list of integer
           mem-pointer       ; mem-pointer: integer
           loop-counter      ; loop-counter: integer
           code              ; code: string
           instruction-idx)  ; instruction-idx: integer
    (let (
          (value (list-ref memory mem-pointer)))
      (display (integer->char value))
      (eval-helper memory
                   mem-pointer
                   loop-counter
                   code
                   (next-instruction-idx instruction-idx)))))

; Esegue l'istruzione ',' allo stato attuale
(define apply-comma          ; val: integer
  (lambda (memory            ; memory: list of integer
           mem-pointer       ; mem-pointer: integer
           loop-counter      ; loop-counter: integer
           code              ; code: string
           instruction-idx)  ; instruction-idx: integer
    (letrec (
             (input-char (read-char))
             (input-value (if (char? input-char)
                              (char->integer input-char)
                              0)))
        (eval-helper (memory-set memory mem-pointer input-value)
                     mem-pointer
                     loop-counter
                     code
                     (next-instruction-idx instruction-idx)))))
  
; Ritorna l'indice dell'intruzione corrispondente alla parentesi quadra aperta
; o chiusa a quella attuale.
(define goto-correct-bracket ; val: integer
  (lambda (code instruction-idx loop-counter forward?) ; code: string, instruction-idx: integer, loop-counter: integer, forward?: bool
    (let (
          (next-instruction (+ instruction-idx (if forward? 1 -1))))
    (if (or (>= instruction-idx (string-length code)) (< instruction-idx 0) (zero? loop-counter))
        instruction-idx
        (let (
              (instruction (string-ref code instruction-idx)))
          (cond
            ((char=? instruction #\[) (goto-correct-bracket code next-instruction (- loop-counter 1) forward?))
            ((char=? instruction #\]) (goto-correct-bracket code next-instruction (+ loop-counter 1) forward?))
            (else (goto-correct-bracket code next-instruction loop-counter forward?))))))))

; Esegue l'istruzione ']' allo stato attuale
(define apply-open           ; val: integer
  (lambda (memory            ; memory: list of integer
           mem-pointer       ; mem-pointer: integer
           loop-counter      ; loop-counter: integer
           code              ; code: string
           instruction-idx)  ; instruction-idx: integer
    (let (
          (next-instruction (if (zero? (list-ref memory mem-pointer))
                                (goto-correct-bracket code (next-instruction-idx instruction-idx) -1 #true)
                                (next-instruction-idx instruction-idx))))
      (eval-helper memory
                   mem-pointer
                   loop-counter
                   code
                   next-instruction))))

; Esegue l'istruzione '[' allo stato attuale
(define apply-closed          ; val: integer
  (lambda (memory             ; memory: list of integer
           mem-pointer        ; mem-pointer: integer
           loop-counter       ; loop-counter: integer
           code               ; code: string
           instruction-idx)   ; instruction-idx: integer
    (let (
          (next-instruction (if (not (zero? (list-ref memory mem-pointer)))
                                (+ (goto-correct-bracket code (prev-instruction-idx instruction-idx) 1 #false) 1)
                                (next-instruction-idx instruction-idx))))
      (eval-helper memory
                   mem-pointer
                   loop-counter
                   code
                   next-instruction))))

; Helper per eval
(define eval-helper           ; val: integer
  (lambda (memory             ; memory: list of integer
           mem-pointer        ; mem-pointer: integer
           loop-counter       ; loop-counter: integer
           code               ; code: string
           instruction-idx)   ; instruction-idx: integer
    (if (>= instruction-idx (string-length code))
        (list-ref memory mem-pointer)
        (let (
              (instruction (string-ref code instruction-idx)))
          (cond
            ((char=? instruction #\+) (apply+/- memory mem-pointer loop-counter code instruction-idx #true))
            ((char=? instruction #\-) (apply+/- memory mem-pointer loop-counter code instruction-idx #false))
            ((char=? instruction #\>) (apply>/< memory mem-pointer loop-counter code instruction-idx #true))
            ((char=? instruction #\<) (apply>/< memory mem-pointer loop-counter code instruction-idx #false))
            ((char=? instruction #\[) (apply-open memory mem-pointer loop-counter code instruction-idx))
            ((char=? instruction #\]) (apply-closed memory mem-pointer loop-counter code instruction-idx))
            ((char=? instruction #\.) (apply. memory mem-pointer loop-counter code instruction-idx))
            ((char=? instruction #\,) (apply-comma memory mem-pointer loop-counter code instruction-idx))
            (else (eval-helper memory mem-pointer loop-counter code (+ instruction-idx 1))))))))

; Esegue una programma scritto in brainf**k. Ritorna il numbero contenuto nella
; cella puntata dal puntatore al momemto della terminazione dell'esecuzione
(define eval                  ; val: integer (input ed output forniti nel/dal terminale) 
  (lambda (code)              ; code: string
    (eval-helper (list-with-length mem-size 0) 0 0 code 0)
    (newline)))

(eval "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.
       <-.<.+++.------.--------.>>+.>++.")
(eval "++++++++[>++++++++<-]>[<++++>-]+<[>-<[>++++<-]>[<++++++++>-]<[>++++++++<-
       ]+>[>++++++++++[>+++++<-]>+.-.[-]<<[-]<->] <[>>+++++++[>+++++++<-]>.+++++
       .[-]<<<-]]>[>++++++++[>+++++++<-]>.[-]<<-]<+++++++++++[>+++>+++++++++>+++
       ++++++>+<<<<-]>-.>-.+++++++.+++++++++++.<.>>.++.+++++++..<-.>>-[[-]<]")
(eval ",[.,]")
