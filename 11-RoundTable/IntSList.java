// Esercizio 11: RoundTable

// Francesco Viciguerra - 10/04/2024
//   	(viciguerrafrancesco@gmail.com)
//   	(166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/10-04-24.pdf

// Commento di documentazione fornito col file dal professore:
// 		Classe IntSList: Integer Scheme-like Lists
//
// 		Definizione di una classe in Java per realizzare oggetti
// 		assimilabili alle liste in Scheme, limitatamente al caso
// 		di liste di interi.
//
// 		Le liste create sono "immutabili".
//
// 		----- Protocollo -----
// 		  IntSList s, t, u;                     // Tipi di riferimento
// 		  int n;
//
// 		Costruttori:                            // Scheme:
// 		    s = new IntSList();                 // null
// 		    t = new IntSList(n,s);              // cons
//
// 		Metodi:                                 // Scheme:
// 		    boolean b = t.isNull();             // null?
// 		    n = t.car();                        // car
// 		    u = t.cdr();                        // cdr
// 		    u = t.cons(n);                      // cons (diversa versione)
//
// Nota: La classe e'stata utilizzata senza modifiche

public class IntSList {
	public static final IntSList NULL_INTLIST = new IntSList();

	private final boolean empty;
	private final int first;
	private final IntSList rest;

	public IntSList() {
		empty = true;
		first = 0;
		rest = null;
	}

	public IntSList( int e, IntSList il ) {
		empty = false;
		first = e;
		rest = il;
	}

	public boolean isNull() {
		return empty;
	}

	public int car() {
		return first;
	}

	public IntSList cdr() {
		return rest;
	}

	public IntSList cons( int e ) {
		return new IntSList( e, this );
	}

	public int length() {
		if ( isNull() ) {
			return 0;
		} else {
			return ( 1 + cdr().length() );
		}
	} 

	public int listRef( int k ) {
		if ( k == 0 ) {
			return car();
		} else {
			return ( cdr().listRef(k-1) );
		}
	}

	public boolean equals( IntSList il ) {
		if ( isNull() || il.isNull() ) {
			return ( isNull() && il.isNull() );
		} else if ( car() == il.car() ) {
			return cdr().equals( il.cdr() );
		} else {
			return false;
		}
	}

	public IntSList append( IntSList il ) {
		if ( isNull() ) {
			return il;
		} else {
			return ( cdr().append(il) ).cons( car() );
		}
	}

	public IntSList reverse() {
		return reverseRec( new IntSList() );
	}

	private IntSList reverseRec( IntSList re ) {
		if ( isNull() ) {
			return re;
		} else {
			return cdr().reverseRec( re.cons(car()) );
		}
	}

	public String toString() {
		if ( isNull() ) {
			return "()";
		} else {
			String rep = "(" + car();
			IntSList r = cdr();
			while ( !r.isNull() ) {
				rep = rep + ", " + r.car();
				r = r.cdr();
			}
			return ( rep + ")" );
		}
	}
}
