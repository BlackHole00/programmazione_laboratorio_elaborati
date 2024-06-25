import java.util.stream.Collectors;

public class BinaryUtils {
	public static String onesComplRec(String input) {
		if (input == null) {
			return "";
		}

		char currentChar = input.charAt(0);
		String nextStr = input.substring(1);

		if (currentChar == '1') {
			return "0" + onesComplRec(nextStr);
		}
		return "1" + onesComplRec(nextStr);
	}

	public static String onesComplIter(String input) {
		return result = input.chars().mapToObj(c -> (c == '1' ? "0" : "1")).collect(Collectors.joining());
	}
}
