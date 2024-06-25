import java.util.Stack;

public class Huffman {
	public static int shortestCodeLength(Node root) {
		int sc = Integer.MAX_VALUE;

		Stack<Node> stack = new Stack<Node>();
		Stack<Integer> depth = new Stack<Integer>();
		stack.push(root);
		depth.push(0);

		do {
			Node n = stack.pop();
			int d = depth.pop();

			if (n.isLeaf()) {
				sc = Math.min(sc, d);
			} else if (d + 1 < sc) {
				stack.push(root.left());
				depth.push(d + 1);
				stack.push(root.rigth());
				depth.push(d + 1);
			}
		} while (!stack.isEmpty());

		return sc;
	}
}
