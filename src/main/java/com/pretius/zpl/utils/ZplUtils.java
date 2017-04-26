package com.pretius.zpl.utils;

import com.pretius.zpl.constant.ZebraFont;
import com.pretius.zpl.constant.ZebraPPP;

/**
 * Common method used to manipulate ZPL
 *
 * @author ttropard
 */
public class ZplUtils {

	/**
	 * Fonction called by zplCommand to cast variable object and ajust for zpl code
	 *
	 * @param object
	 */
	private static String variableObjectToZplCode(Object object) {
		if (object != null) {
			if (object instanceof Integer) {
				return (Integer.toString((Integer) object));
			} else if (object instanceof Boolean) {
				if (((Boolean) object).booleanValue()) {
					return "Y";
				} else {
					return "N";
				}
			} else {
				return object.toString();
			}
		} else {
			return "";
		}
	}

	/**
	 * Method to quickly generate zpl code with command and variable
	 *
	 * @param command   Command (without ^)
	 * @param variables list variable
	 * @return
	 */
	public static StringBuilder zplCommand(String command) {
		StringBuilder zpl = new StringBuilder();
		zpl.append("^");
		zpl.append(command);
		return zpl;
	}

	/**
	 * Method to quickly generate zpl code with command and variable
	 *
	 * @param command   Command (without ^)
	 * @param variables list variable
	 * @return
	 */
	public static StringBuilder zplCommandSautLigne(String command) {
		StringBuilder zpl = zplCommand(command);
		zpl.append("\n");
		return zpl;
	}

	/**
	 * Method to quickly generate zpl code with command and variable
	 *
	 * @param command   Command (without ^)
	 * @param variables list variable
	 * @return
	 */
	public static StringBuilder zplCommand(String command, Object... variables) {
		StringBuilder zpl = new StringBuilder();
		zpl.append("^");
		zpl.append(command);
		if (variables.length > 1) {
			zpl.append(variableObjectToZplCode(variables[0]));
			for (int i = 1; i < variables.length; i++) {
				zpl.append(",");
				zpl.append(variableObjectToZplCode(variables[i]));
			}
		} else if (variables.length == 1) {
			//Only one element in variables
			zpl.append(variableObjectToZplCode(variables[0]));
		}
		return zpl;
	}

	/**
	 * Method to quickly generate zpl code with command and variable
	 *
	 * @param command   Command (without ^)
	 * @param variables list variable
	 * @return
	 */
	public static StringBuilder zplCommandSautLigne(String command, Object... variables) {
		StringBuilder zpl = zplCommand(command, variables);
		zpl.append("\n");
		return zpl;
	}

	/**
	 * Extract from font, fontSize and PPP the height and width in dots.
	 * <p>
	 * Fonts and PPP are not all supported.
	 * Please complete this method or use dot in yous params
	 *
	 * @param zebraFont
	 * @param fontSize
	 * @param zebraPPP
	 * @return array[height, width] in dots
	 */
	public static Integer[] extractDotsFromFont(ZebraFont zebraFont, int fontSize, ZebraPPP zebraPPP) {
		Integer[] array = new Integer[2];

		if (ZebraFont.ZEBRA_ZERO.equals(zebraFont)) {
			switch (zebraPPP) {
				case DPI_203:
					array[0] = Math.round(fontSize * 0.8F);//Heigth
					array[1] = Math.round(fontSize * 0.7808F);//With
//					array[0] = Math.round(fontSize / 2.8224F);//Heigth
//				    array[1] = Math.round(fontSize / 2.7546F);//With
					break;
				case DPI_300:
					array[0] = Math.round(fontSize / 4.2336F);//Heigth
					array[1] = Math.round(fontSize / 4.1318F);//With
					break;
				case DPI_600:
					array[0] = Math.round(fontSize / 8.4672F);//Heigth
					array[1] = Math.round(fontSize / 8.2637F);//With
					break;
				default:
					array[0] = Math.round(fontSize);//Heigth
					array[1] = Math.round(fontSize);//With
			}
		} else {
			throw new UnsupportedOperationException("This PPP and this font are not yet supported. Please use ZebraAFontElement.");
		}
		return array;
	}

	/**
	 * Convert point(pt) in pixel(px)
	 *
	 * @param point
	 * @return
	 */
	public static Integer convertPointInPixel(int point) {
		return point;
//		return Math.round(point * 1.33F);
	}

	/**
	 * Function used to converted ASCII >127 in \hexaCode accepted by ZPL language
	 *
	 * @param str str
	 * @return string with charactere remove
	 */
	public static String convertAccentToZplAsciiHexa(String str) {
		if (str != null) {
			str = str.replace("é", "\\82");
			str = str.replace("à", "\\85");
			str = str.replace("è", "\\8A");
		}
		return str;
	}

	public static String zplDownLoadGraphics(String name, Integer totalBytesNumber, Integer bytesPerRow, String hexValue) {
		StringBuilder zpl = new StringBuilder();
		zpl.append("~DGR");
		zpl.append(":");
		zpl.append(name);
		zpl.append(",");
		zpl.append(totalBytesNumber);
		zpl.append(",");
		zpl.append(bytesPerRow);
		zpl.append(",");
		zpl.append(hexValue);
		return zpl.toString();
	}

	public static String zplReferenceGraphics(String name, Double widthRatio, Double heightRatio) {
		StringBuilder zpl = new StringBuilder();
		zpl.append("^XGR");
		zpl.append(":");
		zpl.append(name);
		zpl.append(",");
		zpl.append(widthRatio);
		zpl.append(",");
		zpl.append(heightRatio);
		return zpl.toString();
	}
}
