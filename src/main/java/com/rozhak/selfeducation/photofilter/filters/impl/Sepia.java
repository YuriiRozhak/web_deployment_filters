package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.image.BufferedImage;
import java.util.function.Predicate;

import com.rozhak.selfeducation.photofilter.filters.Filter;

public class Sepia implements Filter {

	@Override
	public BufferedImage processImage(BufferedImage inputImage) {

		// get width and height of the image
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		Predicate<Integer> biggetThanAllowedColour = p -> p > 255;
		// convert to sepia
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = inputImage.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;

				// calculate tr, tg, tb
				int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
				int tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
				int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);

				// check condition
				if (biggetThanAllowedColour.test(tr)) {
					r = 255;
				} else {
					r = tr;
				}

				if (biggetThanAllowedColour.test(tg)) {
					g = 255;
				} else {
					g = tg;
				}

				if (biggetThanAllowedColour.test(tb)) {
					b = 255;
				} else {
					b = tb;
				}

				// set new RGB value
				p = (a << 24) | (r << 16) | (g << 8) | b;

				inputImage.setRGB(x, y, p);
			}
		}

		return inputImage;
	}

	public static Predicate<Integer> isBigger(Integer than) {
		return i -> i > than;
	}
}