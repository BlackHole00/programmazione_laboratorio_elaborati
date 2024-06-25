// Progetto: Huffman - parte 1

// Francesco Viciguerra - 22/05/2024
// 		(viciguerrafrancesco@gmail.com)
// 		(166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/22-05-24.pdf

// Classe Node, utilizzata per rappresentare l'albero di Huffman.
// Questa classe e'tale e quale a quella implementata a lezione.

public class Node  implements Comparable<Node> {
	private final char ch;

	private final int weight;
	private final Node left, right;

	public Node( char ch, int weight ) {    // nodi foglia
		this.ch = ch;
		this.weight = weight;
		left = null;
		right = null;
	}
  
	public Node( Node left, Node right ) {  // nodi interni
		this.ch = (char)(0);
		weight = left.weight() + right.weight();
		this.left = left;
		this.right = right;
	}

	public boolean isLeaf() {
		return ( left == null );
	}

	public char character() {
		return ch;
	}
  
	public int weight() {
		return weight;
	}
  
	public Node left() {
		return left;
	}
  
	public Node right() {
		return right;
	}

	public int compareTo( Node n ) {
		if ( weight == n.weight() ) {
			return  0;
		} else if ( weight < n.weight() ) {
			return -1;
		} else {
			return  1;
		}
	}

	public String toString() {
		if ( isLeaf() ) {
			if ( (character() == '@') || (character() == '\\') ) {
				return "\\" + character() + ":" + weight();
			} else {
				return character() + ":" + weight();
			}
		} else {
			return "@:" + weight();
		}
	}
}
