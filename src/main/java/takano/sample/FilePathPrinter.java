package takano.sample;

import java.io.File;
import java.util.Objects;
import java.util.function.Function;

public class FilePathPrinter {

	public static void main(String[] args) {
		final String dirPath = "";

		Function<String, String> func1 = s -> s.replace("\\", "/");
		Function<String, String> func2 = s -> s.replace("\\", "/");
		Function<String, String> func3 = s -> s.replace("\\", "/");

		printChildFilePath(new File(dirPath), func1, func2, func3);
	}

	private static void printChildFilePath(final File dir, final Function<String, String>... filters) {
		for (final File file : Objects.requireNonNull(dir.listFiles())) {
			if (file.isDirectory()) {
				printChildFilePath(file, filters);
			}

			String result = file.getAbsolutePath();
			for (final Function<String, String> filter : filters) {
				result = filter.apply(result);
			}
			System.out.println(result);
		}
	}

}
