import java.util.Stack;

public class Huffman2 {
	class Frame {
		public final Node node;
		public final int depth;

		public Frame(Node n, int d) {
			node = n;
			depth = d;
		}
	}

	public static int codeSizeIter(Node root) {
		long bits = 0;
		Stack<Frame> stack = new Stack<Frame>();

		stack.push(new Frame(root, 0));

		do {
			Frame current = stack.pop();
			Node n = current.node;
			int depth = current.depth;

			if (n.isLeaf()) {
				bits += depth * n.weigth();
			} else {
				stack.push(new Frame(n.right(), depth + 1));
				stack.push(new Frame(n.left(), depth + 1));
			}
		} while(!stack.isEmpty());

		return (int)(bits / 7) + ( (bits % 7 > 0) ? 1 : 0);
	}
}
