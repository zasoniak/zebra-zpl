package com.pretius.zpl.constant;

/**
 * Copyright 2017 Pretius
 * <p>
 * Created by mzasonski on 24.04.2017.
 */
public enum BarCode128Mode {
	NO_SELECTED_MODE("N"),
	UCC_CASE_MODE("U"),
	AUTOMATIC_MODE("A"),
	UCC_EAN_MODE("D");

	private String letter;

	BarCode128Mode(String letter) {
		this.letter = letter;
	}

	public String getLetter() {
		return letter;
	}
}

