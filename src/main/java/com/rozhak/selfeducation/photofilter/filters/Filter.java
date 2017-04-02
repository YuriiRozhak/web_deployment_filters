package com.rozhak.selfeducation.photofilter.filters;

import java.awt.image.BufferedImage;

public interface Filter {

	BufferedImage processImage(BufferedImage inputImage);

}
