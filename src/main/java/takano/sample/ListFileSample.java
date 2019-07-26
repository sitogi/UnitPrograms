package takano.sample;

import java.io.File;
		import java.util.Objects;

public class ListFileSample {

	public static void main(String[] args) {
		final File dir = new File("/dir");
		for (final File file : Objects.requireNonNull(dir.listFiles())) {
			System.out.println(file.getName());
		}
	}

}
