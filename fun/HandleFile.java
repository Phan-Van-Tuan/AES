package main.fun;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HandleFile {
	public static byte[] readFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		return Files.readAllBytes(path);
	}

	public static void writeFile(String filePath, byte[] data) throws IOException {
		Path path = Paths.get(filePath);
		Files.write(path, data);
	}

}
