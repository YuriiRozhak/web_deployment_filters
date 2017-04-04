package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import com.rozhak.selfeducation.photofilter.filters.Filter;

public class InvertFilter implements Filter {

	@Override
	public BufferedImage processImage(BufferedImage inputImage) {
		byte[] invertArray = new byte[256];

		for (int counter = 0; counter < 256; counter++)
			invertArray[counter] = (byte) (255 - counter);

		BufferedImageOp invertFilter = new LookupOp(new ByteLookupTable(0, invertArray), null);
		BufferedImage res = invertFilter.filter(inputImage, null);

		return res;
	}
	public static Predicate<Integer> isBigger(Integer than) {
		return i -> i > than;
	}

}
