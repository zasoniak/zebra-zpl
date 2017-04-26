package com.pretius.zpl;

import com.pretius.zpl.constant.ZebraFont;
import com.pretius.zpl.model.ZebraLabel;
import com.pretius.zpl.model.element.*;
import junit.framework.TestCase;

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
