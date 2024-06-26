// Progetto: Huffman - parte 2

// Francesco Viciguerra - 29/05/2024
//         (viciguerrafrancesco@gmail.com)
//         (166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/29-05-24.pdf

import java.util.*;

import huffman_toolkit.*;

// Classe Huffman. Fornisce metodi per la facile compressione e decompressione
// di files.
//
// Questa classe e'basata su quella della della parte 1 dell'esercitazione.
// Le seguenti modifiche sono state apportate:
//        - implementazione di compressWithDefaultTree(), per comprimere un
//            testo con l'albero statistico di default tarato su testi inglesi
//        - implemetazione di decompressWithDefaultTree(), per decomprimere un
//            testo con l'albero statistico di default tarato su testi inglesi
//        - variabile statica ENGLISH_HUFFMAN_TREE: l'albero statistico di
//            default
// Notiamo che il file compresso con il default tree ha un peso che, a seconda
// del file originario puo'anche essere maggiore di quello compresso con l'
// albero codificato nel file. Nel particolare:
//        - se l'originale e'corto, la compressione con l'albero di default
//            pesa di meno
//        - se l'originale e'lungo, la compressione con l'albero apposito pesa
//            di meno
// Questo perche'l'albero generale non e'perfetto (non sara'mai il migliore per
// un determinato file), in quanto formulato statisticamente. Questo fatto, 
// unito alla probabile imprecisa predizione statistica, fa in modo che in file
// lunghi non sia vantaggioso.
// Nel caso si stia comprimendo file non testuali, l'albero apposito e'
// sicuramente la soluzione migliore.

public class Huffman {
    private static final int CHARS = InputTextFile.CHARS;
    private static final Node ENGLISH_HUFFMAN_TREE = huffmanTree(DefaultFreqs.DEFAULT_ENGLISH_FREQS);

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
        PriorityQueue<Node> queue = new PriorityQueue<>();

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

    public static void compressWithDefaultTree(String src, String dst) {
        Node root = huffmanTree(DefaultFreqs.DEFAULT_ENGLISH_FREQS);
        String[] codes = huffmanCodesTable( root );

        InputTextFile in = new InputTextFile( src );
        OutputTextFile out = new OutputTextFile( dst );

        while (in.bitsAvailable()) {
            char c = in.readChar();
            for (int i = 0; i < codes[c].length(); i++) {
                out.writeBit(codes[c].charAt(i) == '1' ? 1 : 0);
            }
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

    public static void decompressWithDefaultTree(String src, String dst) {
        Node root = huffmanTree(DefaultFreqs.DEFAULT_ENGLISH_FREQS);

        InputTextFile in = new InputTextFile( src );
        OutputTextFile out = new OutputTextFile( dst );

        String[] codes = huffmanCodesTable(root);

        String buffer = "";
        while (in.bitsAvailable()) {
            int b = in.readBit();
            buffer += (b == 1 ? "1" : "0");

            int i = 0;
            while (i < codes.length && !codes[i].equals(buffer)) {
                i += 1;
            }

            if (i != codes.length) {
                out.writeChar((char)(i));
                buffer = "";
            }
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

