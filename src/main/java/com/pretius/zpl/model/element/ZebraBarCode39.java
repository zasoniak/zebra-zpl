package com.pretius.zpl.model.element;

import com.pretius.zpl.model.PrinterOptions;
import com.pretius.zpl.utils.barcode.BarcodeGenerator;
import com.pretius.zpl.utils.ZplUtils;
import com.pretius.zpl.utils.barcode.BarcodeTypes;

import java.awt.*;

/**
 * Element to create a bar code 39
 * <p>
 * Zpl command : ^B3 and ^BY
 *
 * @author ttropard
 */
public class ZebraBarCode39 extends ZebraBarCode {

	private boolean checkDigit43 = false;

	public ZebraBarCode39(int positionX, int positionY, String text, int barCodeHeigth) {
		super(positionX, positionY, text, barCodeHeigth);
	}

	public ZebraBarCode39(int positionX, int positionY, String text, int barCodeHeigth, int barCodeWidth, int wideBarRatio) {
		super(positionX, positionY, text, barCodeHeigth, barCodeWidth, wideBarRatio);
	}

	public ZebraBarCode39(int positionX, int positionY, String text, int barCodeHeigth, int barCodeWidth, int wideBarRatio, boolean checkDigit43) {
		super(positionX, positionY, text, barCodeHeigth, barCodeWidth, wideBarRatio);
		this.setCheckDigit43(checkDigit43);
	}

	public ZebraBarCode39(int positionX, int positionY, String text, int barCodeHeigth, boolean showTextInterpretation, boolean showTextInterpretationAbove) {
		super(positionX, positionY, text, barCodeHeigth, showTextInterpretation, showTextInterpretationAbove);
	}

	@Override
	public String getZplCode(PrinterOptions printerOptions) {
		StringBuilder zpl = getStartZplCodeBuilder();
		zpl.append(ZplUtils.zplCommand("B3", zebraRotation.getLetter(),checkDigit43, barCodeHeigth, showTextInterpretation, showTextInterpretationAbove));
		zpl.append("^FD");
		zpl.append(text);
		zpl.append(ZplUtils.zplCommandSautLigne("FS"));
		return zpl.toString();
	}

	public boolean isCheckDigit43() {
		return checkDigit43;
	}

	public ZebraBarCode39 setCheckDigit43(boolean checkDigit43) {
		this.checkDigit43 = checkDigit43;
		return this;
	}

	public void drawPreviewGraphic(PrinterOptions printerOptions, Graphics2D graphic) {
		try {
//			BarcodeGenerator generator = new BarcodeGenerator(BarcodeTypes.DHL_CODE_39, barCodeHeigth, 3, 2);
			graphic.setColor(Color.BLACK);
			int top = 0;
			int left = 0;
			if (positionX != null) {
				left = ZplUtils.convertPointInPixel(positionX);
			}
			if (positionY != null) {
				top = ZplUtils.convertPointInPixel(positionY);
			}
			BarcodeGenerator.printBarcode(BarcodeTypes.DHL_CODE_39, barCodeHeigth, null, 3, 2, graphic, text, left, top);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
