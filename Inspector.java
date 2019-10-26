import java.lang.*;
import java.lang.reflect.*;

public class Inspector {


	public void inspect(Object obj, boolean recursive) {
		println("");
		inspectClass(new InspectObj(obj.getClass(), obj, recursive, 0));
	}

	private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
		inspectClass(new InspectObj(c, obj, recursive, depth));
	}

	private void inspectClass(InspectObj o) {
		// TODO: Implement array check

		if (o.c.isArray()) {

		}

		println(getIndent(o.depth) + "InspectClass: " + getClassName(o.c));

		printDeclaringClass(o);

		printSuperClass(o);

		printInterface(o);

		printConstructors(o);

		printMethods(o);

		printFields(o);
	}


	private void printDeclaringClass(InspectObj o) {
		println(getIndent(o.depth) + " Declaring Class: " + getClassName(o.c.getDeclaringClass()));
	}

	private void printSuperClass(InspectObj o) {
		println(getIndent(o.depth) + " Super-Class: " + getClassName(o.c.getSuperclass()));

		if (o.c.getSuperclass() != null && o.c.getSuperclass() != o.c.getClass())
			inspectClass(o.c.getSuperclass(), o.obj, o.recursive, o.depth + 1);
	}

	private void printInterface(InspectObj o) {
		for (Class i : o.c.getInterfaces()) {
			println(getIndent(o.depth) + " Interface: " + getClassName(i));
			if (o.c != i)
				inspectClass(i, o.obj, o.recursive, o.depth + 1);
		}
	}



	private void printConstructors(InspectObj o) {
		println(getIndent(o.depth) + " Constructors: "
				+ ((o.c.getConstructors().length == 0) ? "[N/A]" : ""));

		for (Constructor con : o.c.getConstructors()) {
			println(getIndent(o.depth) + getConstructorInfo(con));
		}
	}

	private String getConstructorInfo(Constructor con) {
		String result = " - ";

		result += Modifier.toString(con.getModifiers());
		result += " " + con.getName() + "(";

		for (int i = 0; i < con.getParameterCount(); i++) {
			Parameter p = con.getParameters()[i];
			result += getClassName(p.getType()) + ((i + 1 == con.getParameterCount()) ? "" : ", ");
		}

		result += ")";

		return result;
	}



	private void printMethods(InspectObj o) {
		println(getIndent(o.depth) + " Declared Methods: "
				+ ((o.c.getDeclaredMethods().length == 0) ? "[N/A]" : ""));

		for (Method m : o.c.getDeclaredMethods()) {
			println(getIndent(o.depth) + getMethdInfo(m));
		}
	}

	private String getMethdInfo(Method m) {
		String result = " - ";

		result += Modifier.toString(m.getModifiers());
		result += " " + getClassName(m.getReturnType());
		result += " " + m.getName() + "(";

		for (int i = 0; i < m.getParameterCount(); i++) {
			Parameter p = m.getParameters()[i];
			result += getClassName(p.getType()) + (i + 1 == m.getParameterCount() ? "" : ", ");
		}

		result += ")";

		if (m.getExceptionTypes().length > 0) {
			result += " throws ";

			for (int i = 0; i < m.getExceptionTypes().length; i++) {
				Class e = m.getExceptionTypes()[i];
				result += getClassName(e) + (i + 1 == m.getExceptionTypes().length ? "" : ", ");
			}
		}

		return result;
	}



	private void printFields(InspectObj o) {
		println(getIndent(o.depth) + " Declared Fields: "
				+ ((o.c.getDeclaredFields().length == 0) ? "[N/A]" : ""));

		for (Field f : o.c.getDeclaredFields()) {
			println(getIndent(o.depth) + getFieldInfo(o, f));
		}
	}

	private String getFieldInfo(InspectObj o, Field f) {
		String result = " - ";

		result += Modifier.toString(f.getModifiers());
		result += " " + getClassName(f.getType());
		result += " " + f.getName() + " = ";

		Object ooo = null;

		try {
			f.setAccessible(true);
			ooo = f.get(o.obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ooo == null)
			return result + ooo;

		if (f.getType().isPrimitive()) {
			result += ooo;
		} else {
			result += getClassName(ooo.getClass()) + "@" + Integer.toHexString(ooo.hashCode());
		}

		if (f.getType().isArray()) {
			result += " " + getArrayContents(ooo);
		}

		// TODO: Add recursive inspection

		return result;
	}



	private String getClassName(Class c) {
		// May want to change to descriptive name at some point
		// return (c != null) ? c.getName() : "[N/A]";
		return (c != null) ? c.getSimpleName() : "[N/A]";
	}

	private String getIndent(int depth) {
		String indent = "";
		for (int i = 0; i < depth; i++)
			indent += "\t";
		return indent;
	}

	private String getArrayContents(Object a) {
		if (!a.getClass().isArray())
			return "";

		String result = "[";
		for (int i = 0; i < Array.getLength(a); i++) {
			result += Array.get(a, i) + (i + 1 < Array.getLength(a) ? ", " : "");
		}
		result += "]";

		return result;
	}

	private void print(Object arg) {
		System.out.print(arg);
	}

	private void println(Object arg) {
		System.out.println(arg);
	}



	public class InspectObj {

		// Don't care about access right now.
		// Ideally these would be private fields with get/ set, but I'm too lazy...
		public Class c;
		public Object obj;
		public boolean recursive;
		public int depth;

		public InspectObj(Class c, Object obj, boolean recursive, int depth) {
			this.c = c;
			this.obj = obj;
			this.recursive = recursive;
			this.depth = depth;
		}
	}
}

