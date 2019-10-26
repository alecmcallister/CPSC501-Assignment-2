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

		printConstructors(c, recursive, depth);

		printMethods(c, recursive, depth);
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

	private void printConstructors(Class c, boolean recursive, int depth) {
		println(getIndent(depth) + " Constructors: "
				+ ((c.getConstructors().length == 0) ? "[N/A]" : ""));

		for (Constructor con : c.getConstructors()) {
			println(getIndent(depth) + getConstructorInfo(con));
		}

		if (c.getConstructors().length != 0)
			println("");
	}

	private void printMethods(Class c, boolean recursive, int depth) {
		println(getIndent(depth) + " Declared Methods: "
				+ ((c.getDeclaredMethods().length == 0) ? "[N/A]" : ""));

		for (Method m : c.getDeclaredMethods()) {
			println(getIndent(depth) + getMethdInfo(m));
		}

		if (c.getDeclaredMethods().length != 0)
			println("");
	}

	private String getClassName(Class c) {
		// May want to change to descriptive name at some point
		return (c != null) ? c.getSimpleName() : "[N/A]";
	}

	private String getConstructorInfo(Constructor con) {
		String result = " ";

		result += Modifier.toString(con.getModifiers());
		result += " " + con.getName() + "(";

		for (int i = 0; i < con.getParameterCount(); i++) {
			Parameter p = con.getParameters()[i];
			result += getClassName(p.getType()) + ((i + 1 == con.getParameterCount()) ? "" : ", ");
		}

		result += ")";

		return result;
	}

	private String getMethdInfo(Method m) {
		String result = " ";

		result += Modifier.toString(m.getModifiers());
		result += " " + getClassName(m.getReturnType());
		result += " " + m.getName() + "(";

		for (int i = 0; i < m.getParameterCount(); i++) {
			Parameter p = m.getParameters()[i];
			result += getClassName(p.getType()) + (i + 1 == m.getParameterCount() ? "" : ", ");
		}

		result += ")";

		if (m.getExceptionTypes().length > 0)
		{
			result += " throws ";

			for (int i = 0; i < m.getExceptionTypes().length; i++)
			{
				Class e = m.getExceptionTypes()[i];
				result += getClassName(e) + (i + 1 == m.getExceptionTypes().length ? "" : ", ");
			}
		}

		return result;
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
