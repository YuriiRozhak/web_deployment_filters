package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.image.BufferedImage;

public class BlueFilter extends RGBFilter {

	@Override
	public BufferedImage processImage(BufferedImage inputImage) {
		 return createColorImage(inputImage, 0xFF0000FF);
	}

	 
}
