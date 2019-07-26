package main;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathSample {

	public static void main(String[] args) {
		final Path path = Paths.get("/tmp/hue/aaa.txt");
		System.out.println(path.getFileName().toString());
		System.out.println(path.getParent().getFileName().toString());
	}

}
