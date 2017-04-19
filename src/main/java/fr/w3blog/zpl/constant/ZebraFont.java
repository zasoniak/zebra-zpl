package fr.w3blog.zpl.constant;

public enum ZebraFont {

	ZEBRA_ZERO("0"),
	ZEBRA_A("A"),
	ZEBRA_B("B"),
	ZEBRA_C("C"),
	ZEBRA_D("D"),
	ZEBRA_F("F"),
	ZEBRA_G("G");

	String letter;

	private ZebraFont(String letter) {
		this.letter = letter;
	}

	/**
	 * @return the letter
	 */
	public String getLetter() {
		return letter;
	}

	/**
	 * Fonction use for preview to find an equivalent font compatible with Graphic2D
	 *
	 * @param zebraFont
	 * @return
	 */
	public static String findBestEquivalentFontForPreview(ZebraFont zebraFont) {
		switch (zebraFont) {
			case ZEBRA_ZERO:
				return "Arial";
			case ZEBRA_A:
				break;
			case ZEBRA_B:
				break;
			case ZEBRA_C:
				break;
			case ZEBRA_D:
				break;
			case ZEBRA_F:
				break;
			case ZEBRA_G:
				break;
			default:
				return "Arial";
		}
		return "Arial";
	}

}
