package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.function.Predicate;

import com.rozhak.selfeducation.photofilter.filters.Filter;

public class BlackWhiteFilter implements Filter {
	private int width;
	private int height;

	@Override
	public BufferedImage processImage(BufferedImage inputImage) {
		width = inputImage.getWidth();
		height = inputImage.getHeight();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color c = new Color(inputImage.getRGB(j, i));
				int red = (int) (c.getRed() * 0.299);
				int green = (int) (c.getGreen() * 0.587);
				int blue = (int) (c.getBlue() * 0.114);
				Color newColor = new Color(red + green + blue, red + green + blue, red + green + blue);
				inputImage.setRGB(j, i, newColor.getRGB());
			}
		}

		return inputImage;
	}
	public static Predicate<Integer> isBigger(Integer than) {
		return i -> i > than;
	}
}
