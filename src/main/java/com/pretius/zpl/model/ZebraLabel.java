package com.pretius.zpl.model;

import com.pretius.zpl.constant.ZebraFont;
import com.pretius.zpl.constant.ZebraPrintMode;
import com.pretius.zpl.utils.ZplUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ZebraLabel {

	/**
	 * Width explain in dots
	 */
	private Integer widthDots;
	/**
	 * Height explain in dots
	 */
	private Integer heightDots;

	private ZebraPrintMode zebraPrintMode = ZebraPrintMode.TEAR_OFF;

	private PrinterOptions printerOptions = new PrinterOptions();

	private List<ZebraElement> zebraElements = new ArrayList<ZebraElement>();

	public ZebraLabel() {
		super();
		zebraElements = new ArrayList<ZebraElement>();
	}

	public ZebraLabel(PrinterOptions printerOptions) {
		super();
		this.printerOptions = printerOptions;
	}

	/**
	 * Create label with size
	 *
	 * @param heightDots height explain in dots
	 * @param widthDots  width explain in dots
	 */
	public ZebraLabel(int widthDots, int heightDots) {
		super();
		this.widthDots = widthDots;
		this.heightDots = heightDots;
	}

	/**
	 * @param heightDots     height explain in dots
	 * @param widthDots      width explain in dots
	 * @param printerOptions
	 */
	public ZebraLabel(int widthDots, int heightDots, PrinterOptions printerOptions) {
		super();
		this.widthDots = widthDots;
		this.heightDots = heightDots;
		this.printerOptions = printerOptions;
	}

	/**
	 * Function to add Element on etiquette.
	 * <p>
	 * Element is abstract, you should use one of child Element( ZebraText, ZebraBarcode, etc)
	 *
	 * @param zebraElement
	 * @return
	 */
	public ZebraLabel addElement(ZebraElement zebraElement) {
		zebraElements.add(zebraElement);
		return this;
	}

	/**
	 * Use to define a default Zebra font on the label
	 *
	 * @param defaultZebraFont the defaultZebraFont to set
	 */
	public ZebraLabel setDefaultZebraFont(ZebraFont defaultZebraFont) {
		printerOptions.setDefaultZebraFont(defaultZebraFont);
		return this;
	}

	/**
	 * Use to define a default Zebra font size on the label (11,13,14).
	 * Not explain in dots (convertion is processed by library)
	 *
	 * @param defaultFontSize the defaultFontSize to set
	 */
	public ZebraLabel setDefaultFontSize(Integer defaultFontSize) {
		printerOptions.setDefaultFontSize(defaultFontSize);
		return this;
	}

	public Integer getWidthDots() {
		return widthDots;
	}

	public ZebraLabel setWidthDots(Integer widthDots) {
		this.widthDots = widthDots;
		return this;
	}

	public Integer getHeightDots() {
		return heightDots;
	}

	public ZebraLabel setHeightDots(Integer heightDots) {
		this.heightDots = heightDots;
		return this;
	}

	public PrinterOptions getPrinterOptions() {
		return printerOptions;
	}

	public void setPrinterOptions(PrinterOptions printerOptions) {
		this.printerOptions = printerOptions;
	}

	/**
	 * @return the zebraPrintMode
	 */
	public ZebraPrintMode getZebraPrintMode() {
		return zebraPrintMode;
	}

	/**
	 * @param zebraPrintMode the zebraPrintMode to set
	 */
	public ZebraLabel setZebraPrintMode(ZebraPrintMode zebraPrintMode) {
		this.zebraPrintMode = zebraPrintMode;
		return this;
	}

	/**
	 * @return the zebraElements
	 */
	public List<ZebraElement> getZebraElements() {
		return zebraElements;
	}

	/**
	 * @param zebraElements the zebraElements to set
	 */
	public void setZebraElements(List<ZebraElement> zebraElements) {
		this.zebraElements = zebraElements;
	}

	public String getZplCode() {
		StringBuilder zpl = new StringBuilder();

		zpl.append(ZplUtils.zplCommandSautLigne("XA"));//Start Label
		zpl.append(zebraPrintMode.getZplCode());

		if (widthDots != null) {
			//Define width for label
			zpl.append(ZplUtils.zplCommandSautLigne("PW", widthDots));
		}

		if (heightDots != null) {
			zpl.append(ZplUtils.zplCommandSautLigne("LL", heightDots));
		}

		//Default Font and Size
		if (printerOptions.getDefaultZebraFont() != null && printerOptions.getDefaultFontSize() != null) {
			zpl.append(ZplUtils.zplCommandSautLigne("CF", (Object[]) ZplUtils.extractDotsFromFont(printerOptions.getDefaultZebraFont(), printerOptions.getDefaultFontSize(), printerOptions.getZebraPPP())));
		}

		for (ZebraElement zebraElement : zebraElements) {
			zpl.append(zebraElement.getZplCode(printerOptions));
		}
		zpl.append(ZplUtils.zplCommandSautLigne("XZ"));//End Label
		return zpl.toString();
	}

	/**
	 * Function use to have a preview of label rendering (not reflects reality).
	 * <p>
	 * Use it just to see disposition on label
	 *
	 * @return Graphics2D
	 */
	public BufferedImage getImagePreview() {
		if (widthDots != null && heightDots != null) {
			int widthPx = ZplUtils.convertPointInPixel(widthDots);
			int heightPx = ZplUtils.convertPointInPixel(heightDots);
			BufferedImage image = new BufferedImage(widthPx, heightPx, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphic = image.createGraphics();
			graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			graphic.setComposite(AlphaComposite.Src);
			graphic.fillRect(0, 0, widthPx, heightPx);

			graphic.setColor(Color.BLACK);
			graphic.setFont(new Font("Arial", Font.BOLD, 11));
			for (ZebraElement zebraElement : zebraElements) {
				zebraElement.drawPreviewGraphic(printerOptions, graphic);
			}
			return image;
		} else {
			throw new UnsupportedOperationException("Graphics Preview is only available ont label sized");
		}
	}

	public void saveToFile(String format, String path) throws IOException {
		switch (format) {
			case "PDF":
				float transformRate = 0.352777778f * this.getPrinterOptions().getZebraPPP().getDotByMm();
				PDDocument document = new PDDocument();
				PDRectangle rectangle = new PDRectangle(this.getWidthDots() / transformRate, this.getHeightDots() / transformRate);
				PDPage page = new PDPage(rectangle);
				document.addPage(page);
				PDPageContentStream contentStream = new PDPageContentStream(document, page);

				PDImageXObject imageXObject = JPEGFactory.createFromImage(document, this.getImagePreview(), 1.0f, 203);

				contentStream.drawImage(imageXObject, 0, 0, this.getWidthDots() / transformRate, this.getHeightDots() / transformRate);
				contentStream.close();
				document.save("test.pdf");
				document.close();
				break;
			case "PNG":
				File outputFilePng = new File(path);
				ImageIO.write(this.getImagePreview(), "png", outputFilePng);
				break;
			case "JPEG":
				File outputFileJpeg = new File(path);
				ImageIO.write(this.getImagePreview(), "jpeg", outputFileJpeg);
				break;
			case "ZPL":
				try (PrintWriter out = new PrintWriter(path)) {
					out.print(this.getZplCode());
				}
				break;
		}
	}

}
