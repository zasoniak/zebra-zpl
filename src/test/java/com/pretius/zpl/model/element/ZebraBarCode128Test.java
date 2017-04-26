package com.pretius.zpl.model.element;

import junit.framework.TestCase;

public class ZebraBarCode128Test extends TestCase {

    public void testZplOutput() {
        ZebraBarCode128 barcode = new ZebraBarCode128(70, 1000, "0235600703875191516022937128", 190, false, 4, 2);
        assertEquals("^FO70,1000^BY4,2,190^BCN,190,N,N,N,N^FD0235600703875191516022937128^FS\n", barcode.getZplCode(null));
    }
}
