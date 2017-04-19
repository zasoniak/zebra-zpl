package fr.w3blog.zpl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.element.*;
import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

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




	public void printImage(String photo) {

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("test.txt");
			for (int i = 0; i < photo.length(); i += 2) {
				int b = Character.digit(photo.charAt(i), 16) * 16 + Character.digit(photo.charAt(i + 1), 16);
				fos.write(b);
			}
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
