import java.lang.*;
import java.lang.reflect.*;

public class Inspector {

	public void inspect(Object obj, boolean recursive) {
		Class c = obj.getClass();
		inspectClass(c, obj, recursive, 0);
	}

	private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
		println(getDeclaringClass(c));
	}

	private String getDeclaringClass(Class c) {
		String result = "Declaring Class: ";

		if (c == null)
			result = "null";

		else if (c.getDeclaringClass() == null)
			result += "None";

		else
			result += c.getDeclaringClass().getSimpleName();

		return result;
	}

	private void print(Object arg) {
		System.out.print(arg);
	}

	private void println(Object arg) {
		System.out.println(arg);
	}

}
