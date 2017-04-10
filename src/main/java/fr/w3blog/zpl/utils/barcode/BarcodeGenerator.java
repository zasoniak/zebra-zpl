package fr.w3blog.zpl.utils.barcode;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.krysalis.barcode4j.BarcodeException;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Copyright 2017 Pretius
 * <p>
 * Created by mzasonski on 10.04.2017.
 */
public class BarcodeGenerator {
	private org.krysalis.barcode4j.BarcodeGenerator generator;

	public BarcodeGenerator(BarcodeTypes barcodeType) throws SAXException, IOException, ConfigurationException, BarcodeException {
		DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
		Configuration cfg;

		switch (barcodeType) {
			case DHL_CODE_39:
				cfg = builder.buildFromFile(new File(getClass().getClassLoader().getResource("barcode-code39.xml").getFile()));
				generator = BarcodeUtil.getInstance().createBarcodeGenerator(cfg);
				break;
			case DHL_CODE_128:
				cfg = builder.buildFromFile(new File(getClass().getClassLoader().getResource("barcode-code128.xml").getFile()));
				generator = BarcodeUtil.getInstance().createBarcodeGenerator(cfg);
				break;
		}
	}


	public void printBarcode(Graphics2D graphic, String msg, Integer positionX, Integer positionY) {
		graphic.translate(positionX, positionY);
		graphic.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		Java2DCanvasProvider provider = new Java2DCanvasProvider(graphic, 0);
		generator.generateBarcode(provider, msg);
		graphic.translate(-positionX, -positionY);
	}
}
