package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.image.BufferedImage;

public class RedFilter extends RGBFilter {

	private static final int RED_FILTER_MASK = 0xFFFF0000;
	
	@Override
	public BufferedImage processImage(BufferedImage inputImage) {
		return createColorImage(inputImage, RED_FILTER_MASK);
	}
	 
}
