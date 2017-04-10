package fr.w3blog.zpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.element.ZebraBarCode128;
import fr.w3blog.zpl.model.element.ZebraBarCode39;
import fr.w3blog.zpl.model.element.ZebraGraficBox;
import fr.w3blog.zpl.model.element.ZebraNativeZpl;
import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class ZebraLabelTest
		extends TestCase {

	/**
	 * Test with only label without element
	 */
	public void testZebraLabelAlone() {
		ZebraLabel zebraLabel = new ZebraLabel();
		assertEquals("^XA\n^MMT\n^XZ\n", zebraLabel.getZplCode());
	}

	/**
	 * Test with only label without element
	 */
	public void testZebraLabelSize() {
		ZebraLabel zebraLabel = new ZebraLabel(500, 760);
		assertEquals("^XA\n^MMT\n^PW500\n^LL760\n^XZ\n", zebraLabel.getZplCode());
	}
	
}
