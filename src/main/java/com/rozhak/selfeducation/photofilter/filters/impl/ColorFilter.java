package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.function.Predicate;

import com.rozhak.selfeducation.photofilter.filters.Filter;

public class ColorFilter implements Filter {

	@Override
	public BufferedImage processImage(BufferedImage inputImage) {

		float[][] colorMatrix = { { 1f, 0f, 0f }, { 0.5f, 1.0f, 0.5f }, { 0.2f, 0.4f, 0.6f } };
		BandCombineOp changeColors = new BandCombineOp(colorMatrix, null);
		Raster sourceRaster = inputImage.getRaster();
		WritableRaster displayRaster = sourceRaster.createCompatibleWritableRaster();
		changeColors.filter(sourceRaster, displayRaster);
		BufferedImage res = new BufferedImage(inputImage.getColorModel(), displayRaster, true, null);

		return res;

	}
	public static Predicate<Integer> isBigger(Integer than) {
		return i -> i > than;
	}

}
