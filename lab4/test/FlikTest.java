package test;

import flik.Flik;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FlikTest {
    @Test
    public void filkTest01() {
        for (int i = 0; i < 129; i++) {
            assertTrue(Flik.isSameNumber(i, i));
        }
    }
}
