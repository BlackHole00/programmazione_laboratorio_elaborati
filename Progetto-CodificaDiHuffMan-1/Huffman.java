// Progetto: Huffman - parte 1

// Francesco Viciguerra - 22/05/2024
//         (viciguerrafrancesco@gmail.com)
//         (166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/22-05-24.pdf

import java.util.*;

import huffman_toolkit.*;

// Classe Huffman. Fornisce metodi per facilmente comprimere e decomprimere
// interi files. Questa classe e'tale e quale a quella implementata a lezione.
//
// Le uniche modifiche sono, come richiesto dalla consegna dell'esercizio:
//      - utilizzo della classe NodeQueue in alternativa alla classe 
//          PriorityQueue<Node>
//      - implementazione di generateAndCompressRandomText()
//
// Si nota, come chiesto dalla consegna dell'esercizio, che la funzione
// generateAndCompressRandomText() non comprime veramente testo, questo perche'
// un testo generato da caratteri totalmente randomici ha alta entropia ed
// ogni carattere compare lo stesso numero di volte. Il metodo di compressione
// di Huffman utilizza una codifica a lunghezza variabile, dove ai caratteri
// piu'frequenti corrispondono i codici piu'corti. Questo tuttavia non funziona
// bene con i testi randomici in quanto ogni carattere ha la stessa frequenza.
// Il file "compresso" infatti e'di dimensione comparabile a quello originale
// nel caso quest'ultimo sia di lunghezza non indifferente, mentre e'di 
// dimensione addirittura maggiore nel caso di file piccoli, in quanto la 
// codifica dell'albero all'interno del file pesa gran parte del file compresso. 

public class Huffman {
    private static final int CHARS = InputTextFile.CHARS;

    public static int[] charHistogram( String src ) {
        InputTextFile in = new InputTextFile( src );
        int[] freq = new int[ CHARS ];
    
        for ( int c=0; c<CHARS; c=c+1 ) {
            freq[c] = 0;
        }
    
        while ( in.textAvailable() ) {
            int c = in.readChar();
            freq[c] = freq[c] + 1;
        }
        in.close();

        return freq;
    }
  
  
    public static Node huffmanTree( int[] freq ) {
        NodeQueue queue = new NodeQueue();

        for ( int c=0; c<CHARS; c=c+1 ) {
            if ( freq[c] > 0 ) {
                Node n = new Node( (char) c, freq[c] );
                queue.add( n );
            }
        }

        while ( queue.size() > 1 ) {
            Node l = queue.poll();
            Node r = queue.poll();

            Node n = new Node( l, r );
            queue.add( n );
        }

        return queue.poll();
    }
  
  
    public static String[] huffmanCodesTable( Node root ) {
        String[] codes = new String[ CHARS ];
        fillTable( root, "", codes );

        return codes;
    }

    private static void fillTable( Node n, String code, String[] codes ) {
        if ( n.isLeaf() ) {
            codes[ n.character() ] = code;
        } else {
            fillTable( n.left(),  code+"0", codes );
            fillTable( n.right(), code+"1", codes );
        }
    }

    public static String flattenTree( Node n ) {
        if ( n.isLeaf() ) {
            char c = n.character();
            if ( (c == '\\') || (c == '@') ) {
                return ( "\\" + c );
            } else {
                return ( "" + c );
            }
        } else {
            return ( "@"
            + flattenTree( n.left() )
            + flattenTree( n.right() )
            );
        }
    }

    public static void compress( String src, String dst ) {
        int[] freq = charHistogram( src );

        Node root = huffmanTree( freq );

        int count = root.weight();
        String[] codes = huffmanCodesTable( root );

        InputTextFile in = new InputTextFile( src );
        OutputTextFile out = new OutputTextFile( dst );

        out.writeTextLine( "" + count );
        out.writeTextLine( flattenTree(root) );

        for ( int j=0; j<count; j=j+1 ) {
            char c = in.readChar();
            out.writeCode( codes[c] );
        }
        in.close();
        out.close();
    }
  
    public static Node restoreTree( InputTextFile in ) {
        char c = (char) in.readChar();

        if ( c == '@' ) {
            Node left  = restoreTree( in );
            Node right = restoreTree( in );

            return new Node( left, right );
        } else {
            if ( c == '\\' ) {
                c = (char) in.readChar();
            }
            return new Node( c, 0 );
        }
    }

    public static void decompress( String src, String dst ) {
        InputTextFile in = new InputTextFile( src );

        int count = Integer.parseInt( in.readTextLine() );
        Node root = restoreTree( in );

        String dummy = in.readTextLine();
        OutputTextFile out = new OutputTextFile( dst );

        for ( int j=0; j<count; j=j+1 ) {
            char c = decodeNextChar( root, in );
            out.writeChar( c );
        }

        in.close();
        out.close();
    }
  
    private static char decodeNextChar( Node root, InputTextFile in ) {
        Node n = root;

        do {
            if ( in.readBit() == 0 ) {
                n = n.left();
            } else {
                n = n.right();
            }
        } while ( !n.isLeaf() );

        return n.character();
    }
    
    public static void generateAndCompressRandomText(String file) {
        OutputTextFile out = new OutputTextFile(file);
        
        for (int i = 0; i < 65536; i++) {
            char c = (char)(Math.random() * 127 + 1); // Escudiamo il carattere terminatore \0
            out.writeChar(c);
        }
        
        out.close();
        
        compress(file, file + ".huff");
    }

    public static void main( String[] args ) {
        System.out.println( "huffman coding:" );

        if (args.length < 3) {
            System.out.println( "no operation specified: 1st parameter should be either 'compress' or 'decompress', followed by the source file and the destination file" );
            return;
        }

        if ( args[0].equals("compress") ) {
            System.out.println( "compressing..." );
            compress( args[1], args[2] );
        } else if ( args[0].equals("decompress") ) {
            System.out.println( "decompressing..." );
            decompress( args[1], args[2] );
        } else {
            System.out.println( "no operation specified: 1st parameter should be either 'compress' or 'decompress'" );
        }

        System.out.println( "done." );
    }
}

