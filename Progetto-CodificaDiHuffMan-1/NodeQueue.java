// Progetto: Huffman - parte 1

// Francesco Viciguerra - 22/05/2024
//         (viciguerrafrancesco@gmail.com)
//         (166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/22-05-24.pdf

// La classe NodeQueue fornisce una alternativa implementazione alla classe
// PriorityQueue<Node> (proveniente dal package java.util).
// Si nota che questa classe necessita che la classe Node implementi l'
// interfaccia Comparable, come e'stato implementato a lezione.
// Si nota anche che la lunghezza della lista interna alla classe e'dinamica.
// Questo non sarebbe strettamente necessario, in quanto il numero totale di 
// nodi non e'mai maggiore di 128 (in quanto stiamo lavorando con caratteri 
// ASCII standard a 7 bit). Tuttavia per ricerca personale si e'voluta 
// implementare una soluzione piu'flessibile.
//
// Segue il protocollo della classe NodeQueue:
//         new NodeQueue()
//        new NodeQueue(int initialCapacity)
//        int size()
//        Node peek()
//        Node pool()
//        void add()

public class NodeQueue {
    private Node[] nodes;
    private int size;

    public NodeQueue() {
        this(8);
    }

    public NodeQueue(int initialCapacity) {
        nodes = new Node[initialCapacity];
        size = 0;
    }

    public int size() {
        return size;
    }

    public Node peek() {
        if (size == 0) {
            return null;
        }

        return nodes[0];
    }

    public Node poll() {
        Node result = peek();
        removeNode(0);

        return result;
    }

    public void add(Node node) {
        int i = 0;
        while (i < (size - 1) && nodes[i].compareTo(node) == -1) {
            i++;
        }

        insertNode(node, i);
    }

    // Inserisce un nodo nell'indice specificato. Ingrandisce la lista interna
    // se necessario
    private void insertNode(Node node, int index) {
        if (size == nodes.length) {
            resize(nodes.length * 2);
        }

        for (int i = size; i > index; i--) {
            nodes[i] = nodes[i - 1];
        }
        nodes[index] = node;

        size += 1;
    }

    // Rimiove un nodo dalla lista interna specificato da un indice
    private void removeNode(int index) {
        for (int i = index; i < (size - 1); i++) {
            nodes[i] = nodes[i + 1];
        }

        size -= 1;
    }

    // Cambia la grandezza della lista interna alla classe
    private void resize(int newCapacity) {
        Node[] newNodes = new Node[newCapacity];
        System.arraycopy(nodes, 0, newNodes, 0, Math.min(nodes.length, newCapacity));

        nodes = newNodes;
    }
}
