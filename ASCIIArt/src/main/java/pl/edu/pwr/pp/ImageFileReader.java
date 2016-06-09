package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InvalidPropertiesFormatException;
import java.util.Scanner;

public class ImageFileReader {

	/**
	 * Metoda czyta plik pgm i zwraca tablicę odcieni szarości.
	 * @param fileName nazwa pliku pgm
	 * @return tablica odcieni szarości odczytanych z pliku
	 * @throws URISyntaxException jeżeli plik nie istnieje
	 */
	public int[][] readPgmFile(String fileName) throws InvalidPropertiesFormatException {
		int columns = 0;
		int rows = 0;
		int[][] intensities = null;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			if (!reader.readLine().equals("P2")) {
				throw new InvalidPropertiesFormatException("First line must be equal to P2");
			}

			if (reader.readLine().charAt(0) != '#') {
				throw new InvalidPropertiesFormatException("Second line must start from #");
			}


			Scanner s = new Scanner(reader.readLine());
			if (s.hasNextInt()) {
				columns = s.nextInt();
			} else {
				throw new InvalidPropertiesFormatException("Cannot find number of cols");
			}
			if (s.hasNextInt()) {
				rows = s.nextInt();
			} else {
				throw new InvalidPropertiesFormatException("Cannot find number of rows");
			}

			if (!reader.readLine().equals("255")) {
				throw new InvalidPropertiesFormatException("Max contrast is not equal to 255");
			}


			// inicjalizacja tablicy na odcienie szarości
			intensities = new int[rows][];

			for (int i = 0; i < rows; i++) {
				intensities[i] = new int[columns];
			}

			// kolejne linijki pliku pgm zawierają odcienie szarości kolejnych
			// pikseli rozdzielone spacjami
			String line = null;
			int currentRow = 0;
			int currentColumn = 0;
			while ((line = reader.readLine()) != null) {
				String[] elements = line.split(" ");
				for (int i = 0; i < elements.length; i++) {
					intensities[currentRow][currentColumn] = Integer.parseInt(elements[i]);
					currentColumn++;
					currentRow += (int) currentColumn/columns;
					currentColumn %= columns;
				}
			}
		} catch (IOException e) {
			throw new InvalidPropertiesFormatException(e);
		}
		return intensities;
	}


	public BufferedImage getImage(String fileName) throws InvalidPropertiesFormatException
	{
		int[][] img = readPgmFile(fileName);
		BufferedImage image = new BufferedImage(img[0].length, img.length, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = image.getRaster();
		for (int y = 0; y < img.length; y++) {
			for (int x = 0; (x < img[0].length); x++) {
				raster.setSample(x, y, 0, img[y][x]);
			}
		}

		return image;
	}
}
