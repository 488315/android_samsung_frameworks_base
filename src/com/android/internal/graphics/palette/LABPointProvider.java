package com.android.internal.graphics.palette;

import android.graphics.Color;
import android.graphics.ColorSpace;

/* loaded from: classes5.dex */
public class LABPointProvider implements PointProvider {
    final ColorSpace.Connector mRgbToLab = ColorSpace.connect(ColorSpace.get(ColorSpace.Named.SRGB), ColorSpace.get(ColorSpace.Named.CIE_LAB));
    final ColorSpace.Connector mLabToRgb = ColorSpace.connect(ColorSpace.get(ColorSpace.Named.CIE_LAB), ColorSpace.get(ColorSpace.Named.SRGB));

    @Override // com.android.internal.graphics.palette.PointProvider
    public float[] fromInt(int i) {
        return this.mRgbToLab.transform(Color.red(i) / 255.0f, Color.green(i) / 255.0f, Color.blue(i) / 255.0f);
    }

    @Override // com.android.internal.graphics.palette.PointProvider
    public int toInt(float[] fArr) {
        float[] fArrTransform = this.mLabToRgb.transform(fArr);
        return Color.rgb(fArrTransform[0], fArrTransform[1], fArrTransform[2]);
    }

    @Override // com.android.internal.graphics.palette.PointProvider
    public float distance(float[] fArr, float[] fArr2) {
        double d = fArr[0] - fArr2[0];
        double d2 = fArr[1] - fArr2[1];
        double d3 = fArr[2] - fArr2[2];
        return (float) ((d * d) + (d2 * d2) + (d3 * d3));
    }
}
