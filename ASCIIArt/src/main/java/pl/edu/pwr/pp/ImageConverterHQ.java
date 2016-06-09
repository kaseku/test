package pl.edu.pwr.pp;

public class ImageConverterHQ implements ImageConverterI {

	/**
	 * Znaki odpowiadające kolejnym poziomom odcieni szarości - od czarnego (0)
	 * do białego (255).
	 */
	public static String INTENSITY_2_ASCII = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";

	/**
	 * Metoda zwraca znak odpowiadający danemu odcieniowi szarości. Odcienie
	 * szarości
	 * mogą przyjmować wartości z zakresu [0,255]. Zakres jest dzielony
	 * na równe przedziały, liczba przedziałów jest równa ilości znaków w
	 * {@value #INTENSITY_2_ASCII}. Zwracany znak jest znakiem dla przedziału,
	 * do którego należy zadany odcień szarości.
	 * 
	 * 
	 * @param intensity
	 *            odcień szarości w zakresie od 0 do 255
	 * @return znak odpowiadający zadanemu odcieniowi szarości
	 */
	public static char intensityToAscii(int intensity) {
		System.out.println("HQ");
		double ints = (double) intensity;
		for (int i = 0; i < INTENSITY_2_ASCII.length(); i++) {
			ints -= 3.65714286;

			if (ints <= 0) {
				return INTENSITY_2_ASCII.charAt(i);
			}
		}

		return INTENSITY_2_ASCII.charAt(INTENSITY_2_ASCII.length()-1);
	}

	/**
	 * Metoda zwraca dwuwymiarową tablicę znaków ASCII mając dwuwymiarową
	 * tablicę odcieni szarości. Metoda iteruje po elementach tablicy odcieni
	 * szarości i dla każdego elementu wywołuje {@ref #intensityToAscii(int)}.
	 * 
	 * @param intensities
	 *            tablica odcieni szarości obrazu
	 * @return tablica znaków ASCII
	 */
	public static char[][] intensitiesToAscii(int[][] intensities) {
		System.out.println("High Quality Converter");
		char[][] chars = new char[intensities.length][intensities[0].length];

		for (int i = 0; i < intensities.length; i++) {
			for (int j = 0; j < intensities[i].length; j++) {
				chars[i][j] = intensityToAscii(intensities[i][j]);
			}
		}

		return chars;
	}

}
