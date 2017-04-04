package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.image.BufferedImage;

public class BlueFilter extends RGBFilter {

	private static final int BLUE_FILTER_MASK = 0xFF0000FF;

	@Override
	public BufferedImage processImage(BufferedImage inputImage) {
		 return createColorImage(inputImage, BLUE_FILTER_MASK);
	}

	 
}
