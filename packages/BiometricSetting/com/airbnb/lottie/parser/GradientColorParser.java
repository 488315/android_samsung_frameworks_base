package com.airbnb.lottie.parser;

import android.graphics.Color;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class GradientColorParser implements ValueParser<GradientColor> {
    private int colorPoints;

    public GradientColorParser(int i) {
        this.colorPoints = i;
    }

    @Override // com.airbnb.lottie.parser.ValueParser
    public final GradientColor parse(JsonReader jsonReader, float f) throws IOException {
        int i;
        int i2;
        ArrayList arrayList = new ArrayList();
        boolean z = jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY;
        if (z) {
            jsonReader.beginArray();
        }
        while (jsonReader.hasNext()) {
            arrayList.add(Float.valueOf((float) jsonReader.nextDouble()));
        }
        if (z) {
            jsonReader.endArray();
        }
        if (this.colorPoints == -1) {
            this.colorPoints = arrayList.size() / 4;
        }
        int i3 = this.colorPoints;
        float[] fArr = new float[i3];
        int[] iArr = new int[i3];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            i = this.colorPoints * 4;
            if (i4 >= i) {
                break;
            }
            int i7 = i4 / 4;
            double floatValue = ((Float) arrayList.get(i4)).floatValue();
            int i8 = i4 % 4;
            if (i8 == 0) {
                fArr[i7] = (float) floatValue;
            } else if (i8 == 1) {
                i5 = (int) (floatValue * 255.0d);
            } else if (i8 == 2) {
                i6 = (int) (floatValue * 255.0d);
            } else if (i8 == 3) {
                iArr[i7] = Color.argb(255, i5, i6, (int) (floatValue * 255.0d));
            }
            i4++;
        }
        GradientColor gradientColor = new GradientColor(fArr, iArr);
        if (arrayList.size() > i) {
            int size = (arrayList.size() - i) / 2;
            double[] dArr = new double[size];
            double[] dArr2 = new double[size];
            int i9 = 0;
            while (i < arrayList.size()) {
                if (i % 2 == 0) {
                    dArr[i9] = ((Float) arrayList.get(i)).floatValue();
                } else {
                    dArr2[i9] = ((Float) arrayList.get(i)).floatValue();
                    i9++;
                }
                i++;
            }
            for (int i10 = 0; i10 < gradientColor.getSize(); i10++) {
                int i11 = gradientColor.getColors()[i10];
                double d = gradientColor.getPositions()[i10];
                int i12 = 1;
                while (true) {
                    if (i12 >= size) {
                        i2 = (int) (dArr2[size - 1] * 255.0d);
                        break;
                    }
                    int i13 = i12 - 1;
                    double d2 = dArr[i13];
                    double d3 = dArr[i12];
                    if (d3 >= d) {
                        double d4 = (d - d2) / (d3 - d2);
                        int i14 = MiscUtils.$r8$clinit;
                        double max = Math.max(0.0d, Math.min(1.0d, d4));
                        double d5 = dArr2[i13];
                        i2 = (int) ((((dArr2[i12] - d5) * max) + d5) * 255.0d);
                        break;
                    }
                    i12++;
                }
                gradientColor.getColors()[i10] = Color.argb(i2, Color.red(i11), Color.green(i11), Color.blue(i11));
            }
        }
        return gradientColor;
    }
}
