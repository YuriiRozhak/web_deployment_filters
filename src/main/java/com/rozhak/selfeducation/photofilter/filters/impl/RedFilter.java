package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.image.BufferedImage;

public class RedFilter extends RGBFilter {

	@Override
	public BufferedImage processImage(BufferedImage inputImage) {
		return createColorImage(inputImage, 0xFFFF0000);
	}
	 
}
