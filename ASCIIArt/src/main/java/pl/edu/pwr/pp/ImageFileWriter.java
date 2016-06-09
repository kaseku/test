package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;

public class ImageFileWriter {

	public void saveToTxtFile(char[][] ascii, String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		PrintWriter pw = new PrintWriter(file);

		for (int i = 0; i < ascii.length; i++) {
			for (int j = 0; j < ascii[i].length; j++) {
				pw.write(ascii[i][j]);
			}
			pw.println();
		}

		pw.close();
	}
}
