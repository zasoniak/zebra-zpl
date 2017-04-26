package com.pretius.zpl;

import java.io.IOException;

import junit.framework.TestCase;
import com.pretius.zpl.utils.ZplUtils;

/**
 * Test char convertion
 * 
 * @author ttropard
 * 
 */
public class ZplAccentTest extends TestCase {

	/**
	 * Test with many instructions (based on real case)
	 * 
	 * @throws IOException
	 */
	public void testZebraLibrary1() throws IOException
	{
		assertEquals("Qt\\82", ZplUtils.convertAccentToZplAsciiHexa("Qté"));
		assertEquals("\\85", ZplUtils.convertAccentToZplAsciiHexa("à"));

	}
}
