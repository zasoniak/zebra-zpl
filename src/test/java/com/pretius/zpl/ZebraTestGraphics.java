package com.pretius.zpl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pretius.zpl.constant.ZebraFont;
import com.pretius.zpl.model.ZebraLabel;
import com.pretius.zpl.model.element.ZebraBarCode39;
import com.pretius.zpl.model.element.ZebraText;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class ZebraTestGraphics
		extends TestCase
{

	/**
	 * Test with only label without element
	 * 
	 * @throws IOException
	 */
	public void testZebraLabelAlone() throws IOException
	{
		ZebraLabel zebraLabel = new ZebraLabel(912, 912);
		zebraLabel.setDefaultZebraFont(ZebraFont.ZEBRA_ZERO);

		zebraLabel.addElement(new ZebraText(10, 84, "Product:", 14));
		zebraLabel.addElement(new ZebraText(395, 85, "Camera", 14));

		zebraLabel.addElement(new ZebraText(10, 161, "CA201212AA", 14));

		zebraLabel.addElement(new ZebraBarCode39(10, 297, "CA201212AA", 118, 2, 2));

		zebraLabel.addElement(new ZebraText(10, 365, "Qté:", 11));
		zebraLabel.addElement(new ZebraText(180, 365, "3", 11));
		zebraLabel.addElement(new ZebraText(317, 365, "QA", 11));

		zebraLabel.addElement(new ZebraText(10, 520, "Ref log:", 11));
		zebraLabel.addElement(new ZebraText(180, 520, "0035", 11));
		zebraLabel.addElement(new ZebraText(10, 596, "Ref client:", 11));
		zebraLabel.addElement(new ZebraText(180, 599, "1234", 11));

		BufferedImage image = zebraLabel.getImagePreview();
		ImageIO.write(image, "png", new File("zpl.graph"));
	}
}
