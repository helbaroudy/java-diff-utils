package diffutils.rows;

import difflib.algorithm.DiffException;
import difflib.text.DiffRow;
import difflib.text.DiffRowGenerator;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DiffRowGeneratorTest {

    @Test
    public void testGenerator_Default() throws DiffException {
        String first = "anything \n \nother";
        String second = "anything\n\nother";

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
                .columnWidth(Integer.MAX_VALUE) // do not wrap
                .build();
        List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
        print(rows);

        assertEquals(3, rows.size());
    }

    @Test
    public void testGenerator_InlineDiff() throws DiffException {
        String first = "anything \n \nother";
        String second = "anything\n\nother";

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
                .showInlineDiffs(true)
                .columnWidth(Integer.MAX_VALUE) // do not wrap
                .build();
        List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
        print(rows);

        assertEquals(3, rows.size());
        assertTrue(rows.get(0).getOldLine().indexOf("<span") > 0);
    }

    @Test
    public void testGenerator_IgnoreWhitespaces() throws DiffException {
        String first = "anything \n \nother\nmore lines";
        String second = "anything\n\nother\nsome more lines";

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
                .ignoreWhiteSpaces(true)
                .columnWidth(Integer.MAX_VALUE) // do not wrap
                .build();
        List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
        print(rows);

        assertEquals(4, rows.size());
        assertEquals(rows.get(0).getTag(), DiffRow.Tag.EQUAL);
        assertEquals(rows.get(1).getTag(), DiffRow.Tag.EQUAL);
        assertEquals(rows.get(2).getTag(), DiffRow.Tag.EQUAL);
        assertEquals(rows.get(3).getTag(), DiffRow.Tag.CHANGE);
    }

    private List<String> split(String content) {
        return Arrays.asList(content.split("\n"));
    }

    private void print(List<DiffRow> diffRows) {
        for (DiffRow row : diffRows) {
            System.out.println(row);
        }
    }
}
