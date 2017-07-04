package xyz.linsage.kit;

/**
 * @author linsage
 * @create 2017-07-03  上午9:23
 */
public class StringKit {
	public static String multiply(String s, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}
}
