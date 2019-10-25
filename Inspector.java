import java.lang.*;
import java.lang.reflect.*;

public class Inspector {

	public void inspect(Object obj, boolean recursive) {
		println("");
		inspectClass(obj.getClass(), obj, recursive, 0);
	}

	private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
		println(getIndent(depth) + "InspectClass: " + getClassName(c));

		printDeclaringClass(c, recursive, depth);

		printSuperClass(c, recursive, depth);

		printInterface(c, recursive, depth);
	}

	private void printDeclaringClass(Class c, boolean recursive, int depth) {
		println(getIndent(depth) + " Declaring Class: " + getClassName(c.getDeclaringClass()));
	}

	private void printSuperClass(Class c, boolean recursive, int depth) {
		println(getIndent(depth) + " Super-Class: " + getClassName(c.getSuperclass()));

		if (c.getSuperclass() != null && c.getSuperclass() != c.getClass())
			inspectClass(c.getSuperclass(), null, recursive, depth + 1);
	}

	private void printInterface(Class c, boolean recursive, int depth) {
		for (Class i : c.getInterfaces()) {
			println(getIndent(depth) + " Interface: " + getClassName(i));
			if (c != i)
				inspectClass(i, null, recursive, depth + 1);
		}
	}

	private String getClassName(Class c) {
		// May want to change to descriptive name at some point
		return (c != null) ? c.getSimpleName() : "[N/A]";
	}

	private String getIndent(int depth) {
		String indent = "";
		for (int i = 0; i < depth; i++)
			indent += "\t";
		return indent;
	}

	private void print(Object arg) {
		System.out.print(arg);
	}

	private void println(Object arg) {
		System.out.println(arg);
	}

}
