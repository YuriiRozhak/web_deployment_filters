package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.image.BufferedImage;
import java.util.function.Predicate;

import com.rozhak.selfeducation.photofilter.filters.Filter;

public class SepiaFilter implements Filter {
	private static final int MAX_COLOUR_VALUE = 255;

	private static final double RED_OUTPUT_RED_COEF = 0.393;
	private static final double RED_OUTPUT_BLUE_COEF = 0.189;
	private static final double RED_OUTPUT_GREEN_COEF = 0.769;

	private static final double GREEN_OUTPUT_RED_COEF = 0.349;
	private static final double GREEN_OUTPUT_BLUE_COEF = 0.168;
	private static final double GREEN_OUTPUT_GREEN_COEF = 0.686;

	private static final double BLUE_OUTPUT_RED_COEF = 0.272;
	private static final double BLUE_OUTPUT_BLUE_COEF = 0.131;
	private static final double BLUE_OUTPUT_GREEN_COEF = 0.534;
	
	private static final int ALPHA_SHIFT = 24;
	private static final int RED_SHIFT = 16;
	private static final int GREEN_SHIFT = 8;

	@Override
	public BufferedImage processImage(BufferedImage inputImage) {
		
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		
		Predicate<Integer> biggetThanAllowedColour = p -> p > MAX_COLOUR_VALUE;
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = inputImage.getRGB(x, y);

				int a = (p >> ALPHA_SHIFT) & 0xff;
				int r = (p >> RED_SHIFT) & 0xff;
				int g = (p >> GREEN_SHIFT) & 0xff;
				int b = p & 0xff;

				int tr = (int) (RED_OUTPUT_RED_COEF * r + 
						RED_OUTPUT_GREEN_COEF * g + RED_OUTPUT_BLUE_COEF * b);
				int tg = (int) (GREEN_OUTPUT_RED_COEF * r + 
						GREEN_OUTPUT_GREEN_COEF * g + GREEN_OUTPUT_BLUE_COEF * b);
				int tb = (int) (BLUE_OUTPUT_RED_COEF * r +
						BLUE_OUTPUT_GREEN_COEF * g + BLUE_OUTPUT_BLUE_COEF * b);

				if (biggetThanAllowedColour.test(tr)) {
					r = MAX_COLOUR_VALUE;
				} else {
					r = tr;
				}
				
				if (biggetThanAllowedColour.test(tg)) {
					g = MAX_COLOUR_VALUE;
				} else {
					g = tg;
				}

				if (biggetThanAllowedColour.test(tb)) {
					b = MAX_COLOUR_VALUE;
				} else {
					b = tb;
				}

				p = (a << ALPHA_SHIFT) | (r << RED_SHIFT) | (g << GREEN_SHIFT) | b;
				inputImage.setRGB(x, y, p);
			}
		}

		return inputImage;
	}

	public static Predicate<Integer> isBigger(Integer than) {
		return i -> i > than;
	}
}