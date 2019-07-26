package takano.sample;

public class EnumSample {

	public static void main(String[] args) {
		FRUITS.valueOf("APPLE").printName();
	}

	private enum FRUITS {

		APPLE("りんご"), ORANGE("オレンジ"), BANANA("バナナ");

		private final String jpName;

		FRUITS(final String jpName) {
			this.jpName = jpName;
		}

		void printName() {
			System.out.println(jpName);
		}

	}

}
