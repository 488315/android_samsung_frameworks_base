package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.PointF;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.GammaEvaluator;
import com.airbnb.lottie.utils.MiscUtils;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GradientColorParser implements ValueParser {
    public int colorPoints;

    public GradientColorParser(int i) {
        this.colorPoints = i;
    }

    @Override // com.airbnb.lottie.parser.ValueParser
    public final Object parse(JsonReader jsonReader, float f) {
        float[] fArr;
        int i;
        int i2;
        int argb;
        int i3;
        ArrayList arrayList = new ArrayList();
        char c = 0;
        int i4 = 1;
        boolean z = jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY;
        if (z) {
            jsonReader.beginArray();
        }
        while (jsonReader.hasNext()) {
            arrayList.add(Float.valueOf((float) jsonReader.nextDouble()));
        }
        int i5 = 2;
        if (arrayList.size() == 4 && ((Float) arrayList.get(0)).floatValue() == 1.0f) {
            arrayList.set(0, Float.valueOf(0.0f));
            arrayList.add(Float.valueOf(1.0f));
            arrayList.add((Float) arrayList.get(1));
            arrayList.add((Float) arrayList.get(2));
            arrayList.add((Float) arrayList.get(3));
            this.colorPoints = 2;
        }
        if (z) {
            jsonReader.endArray();
        }
        if (this.colorPoints == -1) {
            this.colorPoints = arrayList.size() / 4;
        }
        int i6 = this.colorPoints;
        float[] fArr2 = new float[i6];
        int[] iArr = new int[i6];
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i7 < this.colorPoints * 4) {
            int i10 = i7 / 4;
            double floatValue = ((Float) arrayList.get(i7)).floatValue();
            int i11 = i7 % 4;
            if (i11 == 0) {
                if (i10 > 0) {
                    float f2 = (float) floatValue;
                    if (fArr2[i10 - 1] >= f2) {
                        fArr2[i10] = f2 + 0.01f;
                    }
                }
                fArr2[i10] = (float) floatValue;
            } else if (i11 == i4) {
                i8 = (int) (floatValue * 255.0d);
            } else if (i11 == 2) {
                i9 = (int) (floatValue * 255.0d);
            } else if (i11 == 3) {
                iArr[i10] = Color.argb(255, i8, i9, (int) (floatValue * 255.0d));
            }
            i7++;
            i4 = 1;
        }
        GradientColor gradientColor = new GradientColor(fArr2, iArr);
        int i12 = this.colorPoints * 4;
        if (arrayList.size() <= i12) {
            return gradientColor;
        }
        int size = (arrayList.size() - i12) / 2;
        float[] fArr3 = new float[size];
        float[] fArr4 = new float[size];
        int i13 = 0;
        while (i12 < arrayList.size()) {
            if (i12 % 2 == 0) {
                fArr3[i13] = ((Float) arrayList.get(i12)).floatValue();
            } else {
                fArr4[i13] = ((Float) arrayList.get(i12)).floatValue();
                i13++;
            }
            i12++;
        }
        float[] fArr5 = gradientColor.positions;
        if (fArr5.length == 0) {
            fArr = fArr3;
        } else if (size == 0) {
            fArr = fArr5;
        } else {
            int length = fArr5.length + size;
            fArr = new float[length];
            int i14 = 0;
            int i15 = 0;
            int i16 = 0;
            for (int i17 = 0; i17 < length; i17++) {
                float f3 = i15 < fArr5.length ? fArr5[i15] : Float.NaN;
                float f4 = i16 < size ? fArr3[i16] : Float.NaN;
                if (Float.isNaN(f4) || f3 < f4) {
                    fArr[i17] = f3;
                    i15++;
                } else if (Float.isNaN(f3) || f4 < f3) {
                    fArr[i17] = f4;
                    i16++;
                } else {
                    fArr[i17] = f3;
                    i15++;
                    i16++;
                    i14++;
                }
            }
            if (i14 != 0) {
                fArr = Arrays.copyOf(fArr, length - i14);
            }
        }
        int length2 = fArr.length;
        int[] iArr2 = new int[length2];
        int i18 = 0;
        while (i18 < length2) {
            float f5 = fArr[i18];
            int binarySearch = Arrays.binarySearch(fArr5, f5);
            int binarySearch2 = Arrays.binarySearch(fArr3, f5);
            int[] iArr3 = gradientColor.colors;
            if (binarySearch < 0 || binarySearch2 > 0) {
                if (binarySearch2 < 0) {
                    binarySearch2 = -(binarySearch2 + 1);
                }
                float f6 = fArr4[binarySearch2];
                if (iArr3.length >= 2 && f5 != fArr5[c]) {
                    while (i2 < fArr5.length) {
                        float f7 = fArr5[i2];
                        i2 = (f7 < f5 && i2 != fArr5.length + (-1)) ? i2 + 1 : 1;
                        int i19 = i2 - 1;
                        float f8 = fArr5[i19];
                        float f9 = (f5 - f8) / (f7 - f8);
                        int i20 = iArr3[i2];
                        int i21 = iArr3[i19];
                        i = Color.argb((int) (f6 * 255.0f), GammaEvaluator.evaluate(f9, Color.red(i21), Color.red(i20)), GammaEvaluator.evaluate(f9, Color.green(i21), Color.green(i20)), GammaEvaluator.evaluate(f9, Color.blue(i21), Color.blue(i20)));
                        c = 0;
                    }
                    throw new IllegalArgumentException("Unreachable code.");
                }
                i = iArr3[c];
                iArr2[i18] = i;
            } else {
                int i22 = iArr3[binarySearch];
                if (size >= i5 && f5 > fArr3[c]) {
                    for (int i23 = 1; i23 < size; i23++) {
                        float f10 = fArr3[i23];
                        if (f10 >= f5 || i23 == size - 1) {
                            if (f10 <= f5) {
                                i3 = (int) (fArr4[i23] * 255.0f);
                            } else {
                                int i24 = i23 - 1;
                                float f11 = fArr3[i24];
                                float f12 = (f5 - f11) / (f10 - f11);
                                float f13 = fArr4[i24];
                                float f14 = fArr4[i23];
                                PointF pointF = MiscUtils.pathFromDataCurrentPoint;
                                i3 = (int) ((((f14 - f13) * f12) + f13) * 255.0f);
                            }
                            argb = Color.argb(i3, Color.red(i22), Color.green(i22), Color.blue(i22));
                        }
                    }
                    throw new IllegalArgumentException("Unreachable code.");
                }
                argb = Color.argb((int) (fArr4[c] * 255.0f), Color.red(i22), Color.green(i22), Color.blue(i22));
                iArr2[i18] = argb;
            }
            i18++;
            i5 = 2;
        }
        return new GradientColor(fArr, iArr2);
    }
}
