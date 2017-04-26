package com.pretius.zpl.model.element;

import com.pretius.zpl.model.PrinterOptions;
import com.pretius.zpl.model.ZebraElement;

/**
 * Object use if you want add Zpl Code not supported by this library
 * 
 * @author ttropard
 * 
 */
public class ZebraNativeZpl extends ZebraElement {

	private String zplCode;

	public ZebraNativeZpl(String zplCode) {
		super();
		this.zplCode = zplCode;
		this.defaultDrawGraphic = false;
	}

	/* (non-Javadoc)
	 * @see fr.w3blog.zpl.model.ZPLElement#getZplCode()
	 */
	@Override
	public String getZplCode(PrinterOptions printerOptions) {
		return zplCode;
	}
}
