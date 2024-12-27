package com.samsung.android.wallpaper.legibilitycolors;

import com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils;

public class ColorHSV {
    int avgColor = 0;
    float avgH = 0.0f;
    float avgS = 0.0f;
    float avgV = 0.0f;

    public static void colorToHSV(int color, float[] hsv) {
        float s;
        float h;
        float v;
        float h2;
        int c = 16777215 & color;
        int r = c >> 16;
        int g = (c >> 8) & 255;
        int b = c & 255;
        float min = Math.min(Math.min(r, g), b);
        float max = Math.max(Math.max(r, g), b);
        float delta = max - min;
        if (max != 0.0f) {
            if (delta == 0.0f) {
                h = 0.0f;
                s = 0.0f;
            } else {
                s = delta / max;
                if (r == max) {
                    h2 = (g - b) / delta;
                } else {
                    float h3 = g;
                    if (h3 == max) {
                        h2 = ((b - r) / delta) + 2.0f;
                    } else {
                        h2 = ((r - g) / delta) + 4.0f;
                    }
                }
                float h4 = h2 * 60.0f;
                if (h4 >= 0.0f) {
                    h = h4;
                } else {
                    h = 360.0f + h4;
                }
            }
            v = max / 255.0f;
        } else {
            s = 0.0f;
            h = 0.0f;
            v = 0.0f;
        }
        hsv[0] = h;
        hsv[1] = s;
        hsv[2] = v;
    }

    public void calcAvgColor(int[] pixels) {
        this.avgColor = IUXColorUtils.getAverageColor(pixels);
        float[] avgHSV = new float[3];
        colorToHSV(this.avgColor, avgHSV);
        this.avgH = avgHSV[0];
        this.avgS = avgHSV[1];
        this.avgV = avgHSV[2];
    }

    public int getAvgColor() {
        return this.avgColor;
    }

    public float getAvgH() {
        return this.avgH;
    }

    public float getAvgS() {
        return this.avgS;
    }

    public float getAvgV() {
        return this.avgV;
    }

    public void reset() {
        this.avgH = 0.0f;
        this.avgS = 0.0f;
        this.avgV = 0.0f;
    }
}
