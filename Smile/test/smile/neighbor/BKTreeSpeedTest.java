/******************************************************************************
 *                   Confidential Proprietary                                 *
 *         (c) Copyright Haifeng Li 2011, All Rights Reserved                 *
 ******************************************************************************/

package smile.neighbor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import smile.math.distance.EditDistance;

/**
 *
 * @author Haifeng Li
 */
public class BKTreeSpeedTest {

    List<String> words = new ArrayList<String>();
    BKTree<String> bktree;

    public BKTreeSpeedTest() {
        long start = System.currentTimeMillis();
        try {
            InputStream stream = this.getClass().getResourceAsStream("/smile/neighbor/index.noun");
            BufferedReader input = new BufferedReader(new InputStreamReader(stream));
            String line = input.readLine();
            while (line != null) {
                if (!line.startsWith(" ")) {
                    String[] w = line.split("\\s");
                    words.add(w[0].replace('_', ' '));
                }
                line = input.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        double time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("Loading data: %.2fs\n", time);

        String[] data = words.toArray(new String[words.size()]);

        start = System.currentTimeMillis();
        bktree = new BKTree<String>(new EditDistance(50, true));
        bktree.add(data);
        time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("Building BK-tree: %.2fs\n", time);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of range method, of class BKTree.
     */
    @Test
    public void testBKTreeSpeed() {
        System.out.println("BK-Tree range 1 speed");
        long start = System.currentTimeMillis();
        List<Neighbor<String, String>> neighbors = new ArrayList<Neighbor<String, String>>();
        for (int i = 1000; i < 1100; i++) {
            bktree.range(words.get(i), 1, neighbors);
            neighbors.clear();
        }
        double time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("BK-tree range 1 search: %.2fs\n", time);

        start = System.currentTimeMillis();
        for (int i = 1000; i < 1100; i++) {
            bktree.range(words.get(i), 2, neighbors);
            neighbors.clear();
        }
        time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("BK-tree range 2 search: %.2fs\n", time);

        start = System.currentTimeMillis();
        for (int i = 1000; i < 1100; i++) {
            bktree.range(words.get(i), 3, neighbors);
            neighbors.clear();
        }
        time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("BK-tree range 3 search: %.2fs\n", time);

        start = System.currentTimeMillis();
        for (int i = 1000; i < 1100; i++) {
            bktree.range(words.get(i), 4, neighbors);
            neighbors.clear();
        }
        time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("BK-tree range 4 search: %.2fs\n", time);
    }
}