package com.samsung.android.wallpaper.legibilitycolors.utils.image;

import android.graphics.Bitmap;
import android.hardware.scontext.SContextConstants;

public class ImageConvolution extends BitmapImageProcessing {
    public double mFactor;
    public double mOffset;

    public ImageConvolution(Bitmap bitmap) {
        super(bitmap);
        this.mFactor = 1.0d;
        this.mOffset = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    public ImageConvolution(int[] pixels, int width, int height, Bitmap.Config config) {
        super(pixels, width, height, config);
        this.mFactor = 1.0d;
        this.mOffset = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    public void computeConvolution(double[][] kernel) {
        int[] pixelsBuf = new int[this.mPixels.length];
        computeConvolution(kernel, pixelsBuf);
        setPixels(pixelsBuf);
    }

    public void computeConvolution(double[][] kernel, int[] pixelsDst) {
        int ky;
        int p;
        int[] kernelOffsetY;
        int width = this.mImageWidth;
        int height = this.mImageHeight;
        int width_1 = width - 1;
        int height_1 = height - 1;
        int[] pixelsSrc = this.mPixels;
        int pixelLength = pixelsSrc.length;
        int kernelLength = kernel.length;
        int kernelSerialLength = kernelLength * kernelLength;
        int kernelHalf = kernelLength / 2;
        double offset = this.mOffset;
        double factorOffsetDivider = 1.0d / this.mFactor;
        double[] kernelSerial = new double[kernelSerialLength];
        int[] kernelOffsetX = new int[kernelSerialLength];
        int[] kernelOffsetY2 = new int[kernelSerialLength];
        for (int height2 = 0; height2 < kernelSerialLength; height2++) {
            int ky2 = height2 / kernelLength;
            int kx = height2 % kernelLength;
            kernelSerial[height2] = kernel[ky2][kx];
            kernelOffsetX[height2] = kx - kernelHalf;
            kernelOffsetY2[height2] = ky2 - kernelHalf;
        }
        int p2 = 0;
        while (p2 < pixelLength) {
            int x = p2 % width;
            int y = p2 / width;
            int i = 0;
            int pixelLength2 = pixelLength;
            float sumR = 0.0f;
            int kernelLength2 = kernelLength;
            float sumB = 0.0f;
            int kernelHalf2 = kernelHalf;
            float sumG = 0.0f;
            while (true) {
                ky = 0;
                p = p2;
                if (i >= kernelSerialLength) {
                    break;
                }
                int kx2 = x + kernelOffsetX[i];
                int kx3 = kx2 < 0 ? 0 : kx2 > width_1 ? width_1 : kx2;
                int ky3 = y + kernelOffsetY2[i];
                if (ky3 < 0) {
                    kernelOffsetY = kernelOffsetY2;
                } else {
                    kernelOffsetY = kernelOffsetY2;
                    ky = ky3 > height_1 ? height_1 : ky3;
                }
                int ky4 = pixelsSrc[kx3 + (ky * width)];
                double kernelValue = kernelSerial[i];
                int width_12 = width_1;
                int width_13 = ky4 >> 16;
                sumR = (float) (sumR + ((width_13 & 255) * kernelValue));
                sumG = (float) (sumG + (((ky4 >> 8) & 255) * kernelValue));
                sumB = (float) (sumB + ((ky4 & 255) * kernelValue));
                i++;
                p2 = p;
                kernelOffsetY2 = kernelOffsetY;
                width = width;
                width_1 = width_12;
                height_1 = height_1;
                pixelsSrc = pixelsSrc;
            }
            int[] kernelOffsetY3 = kernelOffsetY2;
            int width2 = width;
            int width_14 = width_1;
            int height_12 = height_1;
            int[] pixelsSrc2 = pixelsSrc;
            int r = (int) ((sumR * factorOffsetDivider) + offset);
            int r2 = r < 0 ? 0 : r > 255 ? 255 : r;
            int g = (int) ((sumG * factorOffsetDivider) + offset);
            int g2 = g < 0 ? 0 : g > 255 ? 255 : g;
            int b = (int) ((sumB * factorOffsetDivider) + offset);
            if (b >= 0) {
                ky = b > 255 ? 255 : b;
            }
            int a = pixelsSrc2[p] >>> 24;
            pixelsDst[p] = (a << 24) | (r2 << 16) | (g2 << 8) | ky;
            p2 = p + 1;
            pixelLength = pixelLength2;
            kernelLength = kernelLength2;
            kernelHalf = kernelHalf2;
            kernelOffsetY2 = kernelOffsetY3;
            width = width2;
            width_1 = width_14;
            height_1 = height_12;
            pixelsSrc = pixelsSrc2;
        }
    }
}
