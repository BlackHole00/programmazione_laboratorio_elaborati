// Esercizio 10: StringSList

// Francesco Viciguerra - 03/04/2024
//   (viciguerrafrancesco@gmail.com)
//   (166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/03-04-24.pdf

// La classe StringSList fornisce una lista in stile scheme (che utilizza
// quindi cons, car e cdr) utilizzabile in Java.
// E'basata sul codice della classe IntSList fornita dal professore.
// Segue il protocollo della classe StringSList:
// 		public StringSList ()                          	// null
// 		public StringSList ( String e, StringSList sl )	// cons (costruttore)
// 		public boolean     isNull()                   	// null?
// 		public String      car()                      	// car
// 		public StringSList cdr()                      	// cdr
// 		public StringSList cons( String e )           	// cons (metodo)
// 		public int         length()                   	// length
// 		public String      listRef( int k )           	// list-ref
// 		public boolean     equals( StringSList sl )   	// equal?
// 		public StringSList append( StringSList sl )   	// append
// 		public StringSList reverse()                  	// reverse
// 		public String      toString()                 	// visualizzazione testuale

public class StringSList {                    
	public static final StringSList NULL_STRING_SLIST = new StringSList();

	private final boolean empty;             
	private final String first;                 
	private final StringSList rest;

	public StringSList() {                      
		empty = true;
		first = "";                             
		rest = null;
	}

	public StringSList(String e, StringSList il) {  
		empty = false;
		first = e;
		rest = il;
	}

	public boolean isNull() {                
		return empty;
	}

	public String car() {                       
		return first;                          
	}

	public StringSList cdr() {                  
		return rest;                           
	}

	public StringSList cons(String e) {          
		return new StringSList(e, this);
	}

	public int length() {                    
		if (isNull()) {                      
			return 0;
		} else {
			return (1 + cdr().length());       
		}                                      
	}

	public String listRef(int k) {            
		if (k == 0) {
			return car();                        
		} else {
			return cdr().listRef(k - 1);       
		}
	}                                        

	public boolean equals(StringSList il) {   
		if ( isNull() || il.isNull() ) {
			return isNull() && il.isNull();
		} else if (car() == il.car()) {
			return cdr().equals(il.cdr());
		} else {
			return false;
		}
	}

	public StringSList append(StringSList il) {  
		if (isNull()) {
			return il;
		} else {
			
			return (cdr().append(il)).cons( car() );
		}
	}

	public StringSList reverse() {              
		return reverseRec(new StringSList());
	}

	private StringSList reverseRec(StringSList re) {
		if (isNull()) {                      
			return re;
		} else {
			return cdr().reverseRec(re.cons(car()));
		}
	}

	public String toString() {               
		if (isNull()) {
			return "()";
		} else {
			String rep = "(" + car();
			StringSList r = cdr();
			while (!r.isNull()) {
				rep = rep + ", " + r.car();
				r = r.cdr();
			}
			return ( rep + ")" );
		}
	}
}
