// Esercizio 12: Queens - part 1

// Francesco Viciguerra - 17/04/2024
// 		(viciguerrafrancesco@gmail.com)
// 		(166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/17-04-24.pdf

import java.util.function.Function;
import java.util.function.BiFunction;

// Commento di documentazione fornito col file dal professore:
// 
// 		Classe SList<T>: Scheme-like Lists of T (generics)
// 
//		Definizione di una classe in Java per realizzare oggetti
// 		assimilabili alle liste in Scheme, limitatamente al caso
// 		di liste con elementi di tipo omogeneo.
// 
// 		Le liste create sono "immutabili".
//
// All'implementazione originaria sono state apportate le seguenti modifiche:
// 		- aggiunta del metodo contains(): controlla se nella lista esiste o meno
//      	un elemento
//      - modifica del metodo map(): permette di ritornare SList di typi, oltre
//			che ad oggetti. **non necessario**, modificato per curiosita' 
//			personale
//		- aggiunta del metodo reduce(): **non utilizzato**, implementato per
//			curiosita' personale

public class SList<T> {
	private final T first;
	private final SList<T> rest;

	public SList() {
		first = null;
		rest = null;
	}
  
	public SList( T e, SList<T> tl ) {
		first = e;
		rest = tl;
	}

	public boolean isNull() {
		return ( first == null );
	}
  
	public T car() {
		return first;
	}

	public SList<T> cdr() {
		return rest;
	}

	public SList<T> cons( T e ) {
		return new SList<T>( e, this );
	}

	public int length() {
		if ( isNull() ) {
			return 0;
		} else {
			return ( 1 + cdr().length() );
		}
	}

	public T listRef( int k ) {
		if ( k == 0 ) {
			return car();
		} else {
			return ( cdr().listRef(k-1) );
		}
	}

	public boolean equals( SList<T> tl ) {
		if ( isNull() || tl.isNull() ) {
			return ( isNull() && tl.isNull() );
		} else if ( car().equals(tl.car()) ) {
			return cdr().equals( tl.cdr() );
		} else {
			return false;
		}
	}
  
	public boolean equals( Object tl ) {
		return equals( (SList<T>) tl );
	}

  
	public SList<T> append( SList<T> tl ) {
		if ( isNull() ) {
			return tl;
		} else {
			return ( cdr().append(tl) ).cons( car() );
		}
	}
  
  
	public SList<T> reverse() {
		return reverseRec( new SList<T>() );
	}
  
	private SList<T> reverseRec( SList<T> re ) {
		if ( isNull() ) {
			return re;
		} else {
			return cdr().reverseRec( re.cons(car()) );
		}
	}

	public <U> SList<U> map( Function<T, U> f ) {
		if ( isNull() ) {
			return new SList<U>();
		} else {
			return ( cdr().map(f) ).cons( f.apply(car()) );
		}
	}

	public T reduce(BiFunction<T, T, T> f) {
		if ( cdr().isNull() ) {
			return car();
		} else {
			return f.apply(cdr().reduce(f), car());
		}
	}

	public boolean contains(T obj) {
		if (isNull()) {
			return false;
		} else if (car().equals(obj)) {
			return true;
		} else {
			return cdr().contains(obj);
		}
	}

	public String toString() {
		if ( isNull() ) {
			return "()";
		} else if ( cdr().isNull() ) {
			return "(" + car() + ")";
		} else {
			String rep = "(" + car();
			SList<T> r = cdr();
			while ( !r.isNull() ) {
				rep = rep + ", " + r.car();
				r = r.cdr();
			}
			return ( rep + ")" );
		}
	}
}  // class SList<T>
