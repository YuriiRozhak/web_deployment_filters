package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.rozhak.selfeducation.photofilter.filters.Filter;

public class BlackWhiteFilter implements Filter {
	private static final double RED_COEF = 0.299;
	private static final double GREEN_COEF = 0.587;
	private static final double BLUE_COEF = 0.114;
	
	private int width;
	private int height;

	@Override
	public BufferedImage processImage(BufferedImage inputImage) {
		width = inputImage.getWidth();
		height = inputImage.getHeight();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color c = new Color(inputImage.getRGB(j, i));
				int red = (int) (c.getRed() * RED_COEF);
				int green = (int) (c.getGreen() * GREEN_COEF);
				int blue = (int) (c.getBlue() * BLUE_COEF);
				Color newColor = new Color(red + green + blue, red + green + blue, red + green + blue);
				inputImage.setRGB(j, i, newColor.getRGB());
			}
		}

		return inputImage;
	}
	
}
