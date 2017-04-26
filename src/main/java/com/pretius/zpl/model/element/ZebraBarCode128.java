package com.pretius.zpl.model.element;

import com.pretius.zpl.constant.BarCode128Mode;
import com.pretius.zpl.model.PrinterOptions;
import com.pretius.zpl.utils.barcode.BarcodeGenerator;
import com.pretius.zpl.utils.ZplUtils;
import com.pretius.zpl.utils.barcode.BarcodeTypes;

import java.awt.*;

/**
 * Element to create a bar code 128
 * <p>
 * Zpl command : ^BC
 *
 * @author matthiasvets
 */
public class ZebraBarCode128 extends ZebraBarCode {

	private boolean uCCCheck = false;
	private BarCode128Mode mode = BarCode128Mode.NO_SELECTED_MODE;

	public ZebraBarCode128(int positionX, int positionY, String text) {
		super(positionX, positionY, text);
	}

	public ZebraBarCode128(int positionX, int positionY, String text, int barCodeHeigth) {
		super(positionX, positionY, text, barCodeHeigth);
	}

	public ZebraBarCode128(int positionX, int positionY, String text, int barCodeHeigth, int barCodeWidth, int wideBarRatio) {
		super(positionX, positionY, text, barCodeHeigth, barCodeWidth, wideBarRatio);
	}

	public ZebraBarCode128(int positionX, int positionY, String text, int barCodeHeigth, int barCodeWidth, int wideBarRatio, boolean uCCCheck, Character mode) {
		super(positionX, positionY, text, barCodeHeigth, barCodeWidth, wideBarRatio);
	}

	public ZebraBarCode128(int positionX, int positionY, String text, int barCodeHeigth, boolean showTextInterpretation, int barCodeWidth, int wideBarRatio) {
		super(positionX, positionY, text, barCodeHeigth, showTextInterpretation, barCodeWidth, wideBarRatio);
	}

	public ZebraBarCode128(int positionX, int positionY, String text, int barCodeHeigth, int barCodeWidth, int wideBarRatio, boolean uCCCheck) {
		super(positionX, positionY, text, barCodeHeigth, barCodeWidth, wideBarRatio);
		this.setuCCCheck(uCCCheck);
	}

	public ZebraBarCode128(int positionX, int positionY, String text, int barCodeHeigth, boolean showTextInterpretation, boolean showTextInterpretationAbove) {
		super(positionX, positionY, text, barCodeHeigth, showTextInterpretation, showTextInterpretationAbove);
	}

	@Override
	public String getZplCode(PrinterOptions printerOptions) {
		StringBuilder zpl = new StringBuilder();
		zpl.append(getZplCodePosition());
		zpl.append(ZplUtils.zplCommand("BY", moduleWidth, wideBarRatio, barCodeHeigth));
		zpl.append(ZplUtils.zplCommand("BC", zebraRotation.getLetter(), barCodeHeigth, showTextInterpretation, showTextInterpretationAbove, uCCCheck, mode.getLetter()));
		zpl.append("^FD");
		zpl.append(text);
		zpl.append(ZplUtils.zplCommandSautLigne("FS"));
		return zpl.toString();
	}

	public boolean isuCCCheck() {
		return uCCCheck;
	}

	public void setuCCCheck(boolean uCCCheck) {
		this.uCCCheck = uCCCheck;
	}

	public void drawPreviewGraphic(PrinterOptions printerOptions, Graphics2D graphic) {
		try {
//			BarcodeGenerator generator = new BarcodeGenerator(BarcodeTypes.DHL_CODE_128, barCodeHeigth, 3, 2);
			graphic.setColor(Color.BLACK);
			int top = 0;
			int left = 0;
			if (positionX != null) {
				left = ZplUtils.convertPointInPixel(positionX);
			}
			if (positionY != null) {
				top = ZplUtils.convertPointInPixel(positionY);
			}
			BarcodeGenerator.printBarcode(BarcodeTypes.DHL_CODE_128, barCodeHeigth, 640.0, 3, 2, graphic, text, left, top);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
