package takano.sample;

import java.util.Arrays;

public class Array2DDemo {

    public static void main(String[] args) {
        final String[][] inputData = createInputData("1", "aaa", "bbb");
        System.out.println(Arrays.deepToString(inputData));
        final String[][] aaa = {
                { "aaa", "bbb" },
                { "ccc", "ddd" },
        };
        System.out.println(Arrays.deepToString(aaa));
    }

    private static String[][] createInputData(final String... args) {
        return new String[][] { args };
    }

}
