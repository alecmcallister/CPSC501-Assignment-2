
import static org.junit.Assert.*;
import org.junit.Test;

public class UnitTests {

	private Inspector i;

	public void setUp() {
		i = new Inspector();
	}

	public void tearDown() {
		i = null;
	}

	@Test
	public void TestIndent() {
		String expected = "\t\t\t\t\t";
		assertEquals(expected, i.getIndent(5));
	}

	@Test
	public void TestArrayContents() {
		Object array = new String[] {"First", "Second"};
		String expected = "(Length: 2) [First, Second]";
		assertEquals(expected, i.getArrayContents(array));
	}

	@Test
	public void TestSeparateListWithCommas() {
		Class[] array = new Class[] {int.class, Integer.class};
		String expected = "int, java.lang.Integer";
		assertEquals(expected, i.separateListWithCommas(array));
	}
}
