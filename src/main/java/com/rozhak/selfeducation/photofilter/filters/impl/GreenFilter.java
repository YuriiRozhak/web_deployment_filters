package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.image.BufferedImage;

public class GreenFilter extends RGBFilter {

	private static final int GREEN_FILTER_MASK = 0xFF00FF00;
	
	@Override
	public BufferedImage processImage(BufferedImage inputImage) {
		return createColorImage(inputImage, GREEN_FILTER_MASK);
	}
	
	

}
