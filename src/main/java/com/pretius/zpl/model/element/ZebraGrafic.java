package com.pretius.zpl.model.element;

import com.pretius.zpl.model.PrinterOptions;
import com.pretius.zpl.model.ZebraElement;
import com.pretius.zpl.utils.ZplUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Zebra element to create a box (or line)
 * <p>
 * Zpl command : ^GB
 *
 * @author matthiasvets
 * @author mzasonski
 */
public class ZebraGrafic extends ZebraElement {

	private static final int WORD_LENGTH = 4;
	private Double widthRatio;
	private Double heightRatio;
	private String name;
	private Integer totalBytesNumber;
	private Integer bytesPerRow;
	private String hexValue;
	private Integer rowNumber;

	public ZebraGrafic(int positionX, int positionY, String name, Double widthRatio, Double heightRatio, String lineColor, String hexValue, int totalBytesNumber, int bytesPerRow) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.widthRatio = widthRatio;
		this.heightRatio = heightRatio;
		this.totalBytesNumber = totalBytesNumber;
		this.bytesPerRow = bytesPerRow;
		this.hexValue = hexValue;
		this.name = name;
		this.rowNumber = totalBytesNumber / bytesPerRow;
	}


	/* (non-Javadoc)
	 * @see fr.w3blog.zpl.model.element.ZebraElement#getZplCode(fr.w3blog.zpl.model.PrinterOptions)
	 */
	@Override
	public String getZplCode(PrinterOptions printerOptions) {
		StringBuilder zpl = new StringBuilder();
		zpl.append(ZplUtils.zplDownLoadGraphics(name, totalBytesNumber, bytesPerRow, hexValue));
		zpl.append("^FS");
		zpl.append(getZplCodePosition());
		zpl.append(ZplUtils.zplReferenceGraphics(name, widthRatio, heightRatio));
		zpl.append(ZplUtils.zplCommandSautLigne("FS"));
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

		BufferedImage bufferedImage = new BufferedImage(bytesPerRow * 8, rowNumber, BufferedImage.TYPE_BYTE_BINARY);
		WritableRaster compatibleWritableRaster = bufferedImage.getData().createCompatibleWritableRaster();

		int totalWithPadding = 2 * (totalBytesNumber + (WORD_LENGTH - totalBytesNumber % WORD_LENGTH));
		boolean[] pixels = new boolean[totalWithPadding * 8];
		int pixelCounter = 0;
		long value = 0;
		for (int wordIndex = 0; wordIndex < totalWithPadding; wordIndex += WORD_LENGTH * 2) {
			if (hexValue.length() >= wordIndex + WORD_LENGTH * 2) {
				value = Long.parseLong(hexValue.substring(wordIndex, wordIndex + WORD_LENGTH * 2), 16);
			} else if (hexValue.length() > wordIndex) {
				StringBuilder endingString = new StringBuilder(hexValue.substring(wordIndex));
				for (int i = 0; i < endingString.length() - WORD_LENGTH * 2; i++) {
					endingString.append("0");
				}
				value = Long.parseLong(endingString.toString(), 16);
			}
			for (long i = WORD_LENGTH * 8 - 1; i >= 0; i--) {
				pixels[pixelCounter++] = ((value & (1L << i)) != 0);
			}
		}

		for (int rowIndex = 0; rowIndex < rowNumber; rowIndex++) {
			for (int columnIndex = 0; columnIndex < bytesPerRow * 8; columnIndex++) {
				if (pixels[rowIndex * bytesPerRow * 8 + columnIndex]) {
					compatibleWritableRaster.setPixel(columnIndex, rowIndex, new int[]{0, 0, 0});
				} else {
					compatibleWritableRaster.setPixel(columnIndex, rowIndex, new int[]{255, 255, 255});
				}
			}
		}

		bufferedImage.setData(compatibleWritableRaster);
		graphic.drawImage(bufferedImage, positionX, positionY, null);

	}


}
