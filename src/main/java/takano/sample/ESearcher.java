package takano.sample;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class ESearcher {

	public static void main(String[] args) throws IOException {
		final Path baseDir = Paths.get("");

		final Properties prop_ja = new Properties();
		final Properties prop_en = new Properties();

		Files.walkFileTree(baseDir, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
			    // TODO 順番を変えればパフォーマンス向上するはず
                System.out.println(path.toAbsolutePath().toString());
				if (path.toString().contains("json_adapter")) {
					return FileVisitResult.CONTINUE;
				}
				if ("messages_ja.properties".equals(path.getFileName().toString())) {
					prop_ja.load(Files.newInputStream(path));
				}
				if ("messages_en.properties".equals(path.getFileName().toString())) {
					prop_en.load(Files.newInputStream(path));
				}
				return FileVisitResult.CONTINUE;
			}
		});

		final Properties properties = new Properties();
		properties.load(Files.newInputStream(Paths.get("")));

		final Map<String, List<String>> jpEnMap = new HashMap<>();

		for (final Map.Entry<Object, Object> jsonEntry : properties.entrySet()) {
			final String value = (String) jsonEntry.getValue();
			final List<String> jpKeyList = new ArrayList<>();

			for (final Map.Entry<Object, Object> ooEntry : prop_ja.entrySet()) {
				final String val = (String) ooEntry.getValue();
				final Pattern pattern = Pattern.compile(value);
				if (pattern.matcher(val).matches()) {
					jpKeyList.add((String) ooEntry.getKey());
				}
			}

			final List<String> enValList = new ArrayList<>();

			for (final String jpKey : jpKeyList) {
				final String enVal = (String) prop_en.get(jpKey);
				if (enVal != null) {
					enValList.add(enVal);
				}
			}

			jpEnMap.put(value, enValList);
		}

		for (final Map.Entry<String, List<String>> jpEnEntry : jpEnMap.entrySet()) {
			final List<String> enValList = jpEnEntry.getValue();
			if (enValList.size() == 0) {
				continue;
			}

			enValList.sort(Comparator.naturalOrder());
			System.out.println(jpEnEntry.getKey());
			String preVal = "";
			int count = 0;
			for (final String enVal : enValList) {
				if ("".equals(preVal) || preVal.equals(enVal)) {
					count++;
					preVal = enVal;
				} else {
					System.out.println(String.format("    [%d] %s", count, preVal));
					preVal = enVal;
					count = 1;
				}
			}
			System.out.println(String.format("    [%d] %s", count, preVal));
		}
	}

}
