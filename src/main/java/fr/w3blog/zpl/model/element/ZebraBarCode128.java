package fr.w3blog.zpl.model.element;

import fr.w3blog.zpl.model.PrinterOptions;
import fr.w3blog.zpl.utils.ZplUtils;
import fr.w3blog.zpl.utils.barcode.BarcodeGenerator;
import fr.w3blog.zpl.utils.barcode.BarcodeTypes;

import java.awt.*;

/**
 * Element to create a bar code 128
 * <p>
 * Zpl command : ^BC
 *
 * @author matthiasvets
 */
public class ZebraBarCode128 extends ZebraBarCode {

	private boolean checkDigit43 = false;

	public ZebraBarCode128(int positionX, int positionY, String text) {
		super(positionX, positionY, text);
	}

	public ZebraBarCode128(int positionX, int positionY, String text, int barCodeHeigth) {
		super(positionX, positionY, text, barCodeHeigth);
	}

	public ZebraBarCode128(int positionX, int positionY, String text, int barCodeHeigth, int barCodeWidth, int wideBarRatio) {
		super(positionX, positionY, text, barCodeHeigth, barCodeWidth, wideBarRatio);
	}

	public ZebraBarCode128(int positionX, int positionY, String text, int barCodeHeigth, boolean showTextInterpretation, int barCodeWidth, int wideBarRatio) {
		super(positionX, positionY, text, barCodeHeigth, showTextInterpretation, barCodeWidth, wideBarRatio);
	}

	public ZebraBarCode128(int positionX, int positionY, String text, int barCodeHeigth, int barCodeWidth, int wideBarRatio, boolean checkDigit43) {
		super(positionX, positionY, text, barCodeHeigth, barCodeWidth, wideBarRatio);
		this.setCheckDigit43(checkDigit43);
	}

	public ZebraBarCode128(int positionX, int positionY, String text, int barCodeHeigth, boolean showTextInterpretation, boolean showTextInterpretationAbove) {
		super(positionX, positionY, text, barCodeHeigth, showTextInterpretation, showTextInterpretationAbove);
	}

	@Override
	public String getZplCode(PrinterOptions printerOptions) {
		StringBuilder zpl = getStartZplCodeBuilder();
		zpl.append(ZplUtils.zplCommandSautLigne("BC", zebraRotation.getLetter(), barCodeHeigth, showTextInterpretation, showTextInterpretationAbove, checkDigit43));
		zpl.append("^FD");
		zpl.append(text);
		zpl.append(ZplUtils.zplCommandSautLigne("FS"));
		return zpl.toString();
	}

	public boolean isCheckDigit43() {
		return checkDigit43;
	}

	public ZebraBarCode128 setCheckDigit43(boolean checkDigit43) {
		this.checkDigit43 = checkDigit43;
		return this;
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
