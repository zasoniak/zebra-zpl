package com.pretius.zpl.model;

import com.pretius.zpl.constant.ZebraJustification;
import com.pretius.zpl.utils.ZplUtils;

import java.awt.*;

public abstract class ZebraElement {

	/**
	 * x-axis location (in dots)
	 */
	protected Integer positionX;
	/**
	 * y-axis location (in dots)
	 */
	protected Integer positionY;
	/**
	 * justification (left, right or auto)
	 */
	protected ZebraJustification justification = null;

	/**
	 * Will draw a default box on the graphic if drawGraphic method is not overload
	 */
	protected boolean defaultDrawGraphic = true;

	/**
	 * @return the positionX
	 */
	public int getPositionX() {
		return positionX;
	}

	/**
	 * @param positionX the positionX to set
	 */
	public ZebraElement setPositionX(int positionX) {
		this.positionX = positionX;
		return this;
	}

	/**
	 * @return the positionY
	 */
	public int getPositionY() {
		return positionY;
	}

	/**
	 * @param positionY the positionY to set
	 */
	public ZebraElement setPositionY(int positionY) {
		this.positionY = positionY;
		return this;
	}

	/**
	 * @return the justification
	 */
	public ZebraJustification getJustification() {
		return justification;
	}

	/**
	 * @param justification the justification to set
	 */
	public ZebraElement setJustification(ZebraJustification justification) {
		this.justification = justification;
		return this;
	}

	/**
	 * Return Zpl code for this Element
	 *
	 * @return
	 */
	public String getZplCode(PrinterOptions printerOptions) {
		return "";
	}

	/**
	 * Function used by child class if you want to set position before draw your element.
	 *
	 * @return
	 */
	protected String getZplCodePosition() {
		StringBuilder zpl = new StringBuilder("");
		if (positionX != null && positionY != null) {
			if (justification != null) {
				zpl.append(ZplUtils.zplCommand("FO", positionX, positionY, justification));
			} else {
				zpl.append(ZplUtils.zplCommand("FO", positionX, positionY));
			}
		}
		return zpl.toString();
	}

	/**
	 * Used to draw label preview.
	 * This method should be overloader by child class.
	 * <p>
	 * Default draw a rectangle
	 *
	 * @param printerOptions TODO
	 * @param graphic
	 */
	public void drawPreviewGraphic(PrinterOptions printerOptions, Graphics2D graphic) {
		if (defaultDrawGraphic) {
			int top = 0;
			int left = 0;
			if (positionX != null) {
				left = Math.round((positionX / printerOptions.getZebraPPP().getDotByMm()) * 10);
			}
			if (positionY != null) {
				top = Math.round((positionY / printerOptions.getZebraPPP().getDotByMm()) * 10);
			}
			graphic.setColor(Color.BLACK);
			graphic.drawRect(left, top, 100, 20);
			drawTopString(graphic, new Font("Arial", Font.BOLD, 11), "Default", left, top);
		}
	}

	/**
	 * Function to draw Element, based on top position.
	 * <p>
	 * Default drawString write text on vertical middle (Zebra not)
	 *
	 * @param graphic
	 * @param font
	 * @param text
	 * @param positionX
	 * @param positionY
	 */
	protected void drawTopString(Graphics2D graphic, Font font, String text, int positionX, int positionY) {
		graphic.setFont(font);
		FontMetrics fm = graphic.getFontMetrics(font);
		graphic.drawString(text, positionX, positionY + fm.getAscent()); // Draw the string.
	}
}
