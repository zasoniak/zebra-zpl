package fr.w3blog.zpl.model.element;

import fr.w3blog.zpl.model.PrinterOptions;
import fr.w3blog.zpl.model.ZebraElement;
import fr.w3blog.zpl.utils.ZplUtils;

import java.awt.*;

/**
 * Zebra element to create a box (or line)
 * <p>
 * Zpl command : ^GB
 *
 * @author matthiasvets
 * @author mzasonski
 */
public class ZebraGraficBox extends ZebraElement {

	private Integer width;
	private Integer height;
	private Integer borderThickness;
	private String lineColor;

	public ZebraGraficBox(int positionX, int positionY, Integer width, Integer height, Integer borderThickness, String lineColor) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.borderThickness = borderThickness;
		this.lineColor = lineColor;
	}


	/* (non-Javadoc)
	 * @see fr.w3blog.zpl.model.element.ZebraElement#getZplCode(fr.w3blog.zpl.model.PrinterOptions)
	 */
	@Override
	public String getZplCode(PrinterOptions printerOptions) {
		StringBuilder zpl = new StringBuilder();
		zpl.append(getZplCodePosition());
		zpl.append("\n");
		zpl.append(ZplUtils.zplCommand("GB", width, height, borderThickness, lineColor));
		zpl.append("^FS");
		zpl.append("\n");
		return zpl.toString();
	}

	protected String getZplCodePosition() {
		StringBuffer zpl = new StringBuffer("");
		if (positionX != null && positionY != null) {
			zpl.append(ZplUtils.zplCommand("FO", positionX, positionY));
		}
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
		graphic.setColor(Color.BLACK);
		graphic.setStroke(new BasicStroke(borderThickness));

		int thicknessCorrection = ZplUtils.convertPointInPixel((int) Math.floor(borderThickness / 2));

		int top = 0;
		int left = 0;
		if (positionX != null) {
			left = ZplUtils.convertPointInPixel(positionX)-thicknessCorrection;
		}
		if (positionY != null) {
			top = ZplUtils.convertPointInPixel(positionY)-thicknessCorrection;
		}

		if (width != 0) {
			width = width - thicknessCorrection;
		}
		if (height != 0) {
			height = height - thicknessCorrection;
		}

		graphic.drawRect(left + thicknessCorrection, top + thicknessCorrection, ZplUtils.convertPointInPixel(width), ZplUtils.convertPointInPixel(height));

	}


}
