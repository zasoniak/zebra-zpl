package com.pretius.zpl.model.element;

import com.pretius.zpl.constant.ZebraFont;
import com.pretius.zpl.constant.ZebraJustification;
import com.pretius.zpl.model.PrinterOptions;
import com.pretius.zpl.model.ZebraElement;
import com.pretius.zpl.constant.ZebraRotation;
import com.pretius.zpl.utils.ZplUtils;

import java.awt.*;

/**
 * Zebra element to add Text to specified position.
 *
 * @author ttropard
 */
public class ZebraText extends ZebraElement {

	ZebraFont zebraFont = null;

	/**
	 * Explain Font Size (11,13,14).
	 * Not in dots.
	 */
	Integer fontSize = null;
	Integer width = null;
	Integer height = null;

	ZebraRotation zebraRotation = ZebraRotation.NORMAL;

	String text;

	public ZebraText(String text) {
		this.text = text;
	}

	public ZebraText(String text, int fontSize) {
		this.fontSize = fontSize;
		this.text = text;
	}

	public ZebraText(String text, ZebraFont zebraFont, int fontSize) {
		this.zebraFont = zebraFont;
		this.fontSize = fontSize;
		this.text = text;
	}

	public ZebraText(String text, ZebraFont zebraFont, int fontSize, ZebraRotation zebraRotation) {
		this.zebraFont = zebraFont;
		this.zebraRotation = zebraRotation;
		this.fontSize = fontSize;
		this.text = text;
	}

	public ZebraText(int positionX, int positionY, String text) {
		this.text = text;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public ZebraText(int positionX, int positionY, String text, int fontSize) {
		this.fontSize = fontSize;
		this.text = text;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public ZebraText(int positionX, int positionY, String text, int fontSize, ZebraJustification justification) {
		this.fontSize = fontSize;
		this.text = text;
		this.positionX = positionX;
		this.positionY = positionY;
		this.justification = justification;
	}

	public ZebraText(int positionX, int positionY, String text, ZebraFont zebraFont, int fontSize, ZebraRotation zebraRotation) {
		this.zebraFont = zebraFont;
		this.fontSize = fontSize;
		this.zebraRotation = zebraRotation;
		this.text = text;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public ZebraText(int positionX, int positionY, String text, ZebraFont zebraFont, int fontSize) {
		this.zebraFont = zebraFont;
		this.fontSize = fontSize;
		this.text = text;
		this.positionX = positionX;
		this.positionY = positionY;
		this.zebraRotation = null;
	}

	public ZebraText(int positionX, int positionY, String text, ZebraFont zebraFont, int height, int width, ZebraRotation rotation, ZebraJustification justification) {
		this.zebraFont = zebraFont;
		this.height = height;
		this.width = width;
		this.text = text;
		this.positionX = positionX;
		this.positionY = positionY;
		this.zebraRotation = rotation;
		this.justification = justification;
	}

	/* (non-Javadoc)
	 * @see fr.w3blog.zpl.model.element.ZebraElement#getZplCode(fr.w3blog.zpl.model.PrinterOptions)
	 */
	@Override
	public String getZplCode(PrinterOptions printerOptions) {
		StringBuilder zpl = new StringBuilder();
		zpl.append(this.getZplCodePosition());

		if (fontSize != null && zebraFont != null && zebraRotation != null) {
			//This element has specified size, font and justification
			Integer[] dimension = ZplUtils.extractDotsFromFont(zebraFont, fontSize, printerOptions.getZebraPPP());
			zpl.append(ZplUtils.zplCommand("A", zebraFont.getLetter() + zebraRotation.getLetter(), dimension[0], dimension[1]));
		} else if (fontSize != null && zebraFont != null) {
			//This element has specified size and font
			Integer[] dimension = ZplUtils.extractDotsFromFont(zebraFont, fontSize, printerOptions.getZebraPPP());
			zpl.append(ZplUtils.zplCommand("A", zebraFont.getLetter(), dimension[0], dimension[1]));
		} else if (fontSize != null && printerOptions.getDefaultZebraFont() != null) {
			//This element has specified size, but with default font
			Integer[] dimension = ZplUtils.extractDotsFromFont(printerOptions.getDefaultZebraFont(), fontSize, printerOptions.getZebraPPP());
			zpl.append(ZplUtils.zplCommand("A", printerOptions.getDefaultZebraFont().getLetter() + zebraRotation.getLetter(), dimension[0], dimension[1]));
		}

		zpl.append("^FD");//We allow hexadecimal and start element
		zpl.append(ZplUtils.convertAccentToZplAsciiHexa(text));
		zpl.append(ZplUtils.zplCommandSautLigne("FS"));

		return zpl.toString();
	}

	/**
	 * Used to draw label preview.
	 * This method should be overloader by child class.
	 * <p>
	 * Default draw a rectangle
	 *
	 * @param graphic
	 */
	public void drawPreviewGraphic(PrinterOptions printerOptions, Graphics2D graphic) {
		if (defaultDrawGraphic) {
			int top = 0;
			int left = 0;
			if (positionX != null) {
				left = ZplUtils.convertPointInPixel(positionX);
			}
			if (positionY != null) {
				top = ZplUtils.convertPointInPixel(positionY);
			}

			Font font = null;

			if (fontSize != null && zebraFont != null) {
				//This element has specified size and font
				Integer[] dimension = ZplUtils.extractDotsFromFont(zebraFont, fontSize, printerOptions.getZebraPPP());

				font = new Font(ZebraFont.findBestEquivalentFontForPreview(zebraFont), Font.PLAIN, dimension[0]);
			} else if (fontSize != null && printerOptions.getDefaultZebraFont() != null) {
				//This element has specified size, but with default font
				Integer[] dimensionPoint = ZplUtils.extractDotsFromFont(printerOptions.getDefaultZebraFont(), fontSize, printerOptions.getZebraPPP());
				font = new Font(ZebraFont.findBestEquivalentFontForPreview(printerOptions.getDefaultZebraFont()), Font.PLAIN, Math.round(dimensionPoint[0] / 1.33F));
			} else {
				//Default font on Printer Zebra
				Integer[] dimensionPoint = ZplUtils.extractDotsFromFont(printerOptions.getDefaultZebraFont(), 15, printerOptions.getZebraPPP());

				font = new Font(ZebraFont.findBestEquivalentFontForPreview(ZebraFont.ZEBRA_A), Font.PLAIN, dimensionPoint[0]);
			}
			drawTopString(graphic, font, text, left, top);
		}
	}
}
