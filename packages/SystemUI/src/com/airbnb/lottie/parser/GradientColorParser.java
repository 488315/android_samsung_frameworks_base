package com.airbnb.lottie.parser;

import android.graphics.Color;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.GammaEvaluator;
import com.airbnb.lottie.utils.MiscUtils;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class GradientColorParser implements ValueParser {
    public int colorPoints;

    public GradientColorParser(int i) {
        this.colorPoints = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.airbnb.lottie.parser.ValueParser
    public final Object parse(JsonReader jsonReader, float f) {
        float[] fArr;
        int i;
        int argb;
        float f2;
        float lerp;
        int i2;
        ArrayList arrayList = new ArrayList();
        int i3 = 1;
        boolean z = false;
        byte b = jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY;
        if (b != false) {
            jsonReader.beginArray();
        }
        while (jsonReader.hasNext()) {
            arrayList.add(Float.valueOf((float) jsonReader.nextDouble()));
        }
        int i4 = 2;
        if (arrayList.size() == 4 && ((Float) arrayList.get(0)).floatValue() == 1.0f) {
            arrayList.set(0, Float.valueOf(0.0f));
            arrayList.add(Float.valueOf(1.0f));
            arrayList.add((Float) arrayList.get(1));
            arrayList.add((Float) arrayList.get(2));
            arrayList.add((Float) arrayList.get(3));
            this.colorPoints = 2;
        }
        if (b != false) {
            jsonReader.endArray();
        }
        if (this.colorPoints == -1) {
            this.colorPoints = arrayList.size() / 4;
        }
        int i5 = this.colorPoints;
        float[] fArr2 = new float[i5];
        int[] iArr = new int[i5];
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i6 < this.colorPoints * 4) {
            int i9 = i6 / 4;
            double floatValue = ((Float) arrayList.get(i6)).floatValue();
            int i10 = i6 % 4;
            if (i10 != 0) {
                if (i10 == i3) {
                    i7 = (int) (floatValue * 255.0d);
                } else if (i10 == 2) {
                    i8 = (int) (floatValue * 255.0d);
                } else if (i10 == 3) {
                    iArr[i9] = Color.argb(255, i7, i8, (int) (floatValue * 255.0d));
                }
                i2 = i3;
            } else {
                if (i9 > 0) {
                    i2 = i3;
                    float f3 = (float) floatValue;
                    if (fArr2[i9 - 1] >= f3) {
                        fArr2[i9] = f3 + 0.01f;
                    }
                } else {
                    i2 = i3;
                }
                fArr2[i9] = (float) floatValue;
            }
            i6++;
            i3 = i2;
        }
        int i11 = i3;
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
                float f4 = i15 < fArr5.length ? fArr5[i15] : Float.NaN;
                float f5 = i16 < size ? fArr3[i16] : Float.NaN;
                if (Float.isNaN(f5) || f4 < f5) {
                    fArr[i17] = f4;
                    i15++;
                } else if (Float.isNaN(f4) || f5 < f4) {
                    fArr[i17] = f5;
                    i16++;
                } else {
                    fArr[i17] = f4;
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
            float f6 = fArr[i18];
            int binarySearch = Arrays.binarySearch(fArr5, f6);
            int binarySearch2 = Arrays.binarySearch(fArr3, f6);
            boolean z2 = z;
            int[] iArr3 = gradientColor.colors;
            if (binarySearch < 0 || binarySearch2 > 0) {
                if (binarySearch2 < 0) {
                    binarySearch2 = -(binarySearch2 + 1);
                }
                float f7 = fArr4[binarySearch2];
                if (iArr3.length >= i4 && f6 != fArr5[z2 ? 1 : 0]) {
                    for (int i19 = i11; i19 < fArr5.length; i19++) {
                        float f8 = fArr5[i19];
                        if (f8 >= f6 || i19 == fArr5.length - 1) {
                            int i20 = i19 - 1;
                            float f9 = fArr5[i20];
                            float f10 = (f6 - f9) / (f8 - f9);
                            int i21 = iArr3[i19];
                            int i22 = iArr3[i20];
                            i = Color.argb((int) (f7 * 255.0f), GammaEvaluator.evaluate(f10, Color.red(i22), Color.red(i21)), GammaEvaluator.evaluate(f10, Color.green(i22), Color.green(i21)), GammaEvaluator.evaluate(f10, Color.blue(i22), Color.blue(i21)));
                        }
                    }
                    throw new IllegalArgumentException("Unreachable code.");
                }
                i = iArr3[z2 ? 1 : 0];
                iArr2[i18] = i;
            } else {
                int i23 = iArr3[binarySearch];
                if (size >= i4 && f6 > fArr3[z2 ? 1 : 0]) {
                    for (int i24 = i11; i24 < size; i24++) {
                        float f11 = fArr3[i24];
                        if (f11 < f6) {
                            f2 = 255.0f;
                            if (i24 != size - 1) {
                            }
                        } else {
                            f2 = 255.0f;
                        }
                        if (f11 <= f6) {
                            lerp = fArr4[i24];
                        } else {
                            int i25 = i24 - 1;
                            float f12 = fArr3[i25];
                            lerp = MiscUtils.lerp(fArr4[i25], fArr4[i24], (f6 - f12) / (f11 - f12));
                        }
                        argb = Color.argb((int) (lerp * f2), Color.red(i23), Color.green(i23), Color.blue(i23));
                    }
                    throw new IllegalArgumentException("Unreachable code.");
                }
                argb = Color.argb((int) (fArr4[z2 ? 1 : 0] * 255.0f), Color.red(i23), Color.green(i23), Color.blue(i23));
                iArr2[i18] = argb;
            }
            i18++;
            z = z2 ? 1 : 0;
            i4 = 2;
        }
        return new GradientColor(fArr, iArr2);
    }
}
