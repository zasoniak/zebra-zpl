package fr.w3blog.zpl.utils.barcode;

import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;

import java.awt.*;

/**
 * Copyright 2017 Pretius
 * <p>
 * Created by mzasonski on 10.04.2017.
 */
public class BarcodeGenerator {
	private BarcodeTypes barcodeType;
	private org.krysalis.barcode4j.BarcodeGenerator generator;


	public BarcodeGenerator(BarcodeTypes barcodeType, double barHeight, double moduleWidth, double wideFactor) {
		switch (barcodeType) {
			case DHL_CODE_39:
				Code39Bean code39Bean = new Code39Bean();
				code39Bean.setBarHeight(barHeight);
				code39Bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
				code39Bean.setModuleWidth(moduleWidth);
				code39Bean.setWideFactor(wideFactor);
				generator = code39Bean;
				break;
			case DHL_CODE_128:
				Code128Bean code128Bean = new Code128Bean();
				code128Bean.setBarHeight(barHeight);
				code128Bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
				code128Bean.setModuleWidth(moduleWidth);
				generator = code128Bean;
				break;
		}
	}

	public BarcodeGenerator(BarcodeTypes barcodeType) {
		switch (barcodeType) {
			case DHL_CODE_39:
				Code39Bean code39Bean = new Code39Bean();
				code39Bean.setBarHeight(3.125);
				code39Bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
				code39Bean.setModuleWidth(3);
				code39Bean.setWideFactor(2);
				generator = code39Bean;
				break;
			case DHL_CODE_128:
				Code128Bean code128Bean = new Code128Bean();
				code128Bean.setBarHeight(25);
				code128Bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
				code128Bean.setModuleWidth(3);
				generator = code128Bean;
				break;
		}
	}


	public static void printBarcode(BarcodeTypes barcodeType, double barHeight, Double barWidth, double moduleWidth, double wideFactor, Graphics2D graphic, String msg, Integer positionX, Integer positionY) {
		graphic.translate(positionX, positionY);
		graphic.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		Java2DCanvasProvider provider = new Java2DCanvasProvider(graphic, 0);
		switch (barcodeType) {
			case DHL_CODE_39:
				Code39Bean code39Bean = new Code39Bean();
				code39Bean.setBarHeight(barHeight);
				code39Bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
				code39Bean.setModuleWidth(moduleWidth);
				code39Bean.setWideFactor(wideFactor);
				code39Bean.generateBarcode(provider, msg);
				break;
			case DHL_CODE_128:
			default:
				Code128Bean code128Bean = new Code128Bean();
				code128Bean.setBarHeight(barHeight);
				code128Bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
				code128Bean.setModuleWidth(moduleWidth);
				BarcodeDimension barcodeDimension = code128Bean.calcDimensions(msg);
				if (barWidth != null && barcodeDimension.getWidth() < barWidth) {
					code128Bean.setModuleWidth(code128Bean.getModuleWidth() * barWidth / barcodeDimension.getWidth());
				}
				code128Bean.generateBarcode(provider, msg);

				break;
		}
		graphic.translate(-positionX, -positionY);
	}
}
