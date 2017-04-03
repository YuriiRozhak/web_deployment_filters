package com.rozhak.selfeducation.photofilter.filters.impl;

import java.awt.image.BufferedImage;
import com.rozhak.selfeducation.photofilter.filters.Filter;


public abstract class RGBFilter implements Filter {

    protected BufferedImage createColorImage(BufferedImage originalImage, int mask) {
        BufferedImage colorImage = new BufferedImage(originalImage.getWidth(),
                originalImage.getHeight(), originalImage.getType());

        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int pixel = originalImage.getRGB(x, y) & mask;
                colorImage.setRGB(x, y, pixel);
            }
        }

        return colorImage;
    }


}
