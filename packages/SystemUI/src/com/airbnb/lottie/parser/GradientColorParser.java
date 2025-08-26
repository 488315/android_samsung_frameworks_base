package com.airbnb.lottie.parser;

import android.graphics.Color;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.GammaEvaluator;
import com.airbnb.lottie.utils.MiscUtils;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public class GradientColorParser implements ValueParser {
    public int colorPoints;

    public GradientColorParser(int i) {
        this.colorPoints = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.airbnb.lottie.parser.ValueParser
    public final Object parse(JsonReader jsonReader, float f) {
        float[] fArrCopyOf;
        int iArgb;
        int iArgb2;
        float f2;
        float fLerp;
        int i;
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        boolean z = false;
        Object[] objArr = jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY;
        if (objArr != false) {
            jsonReader.beginArray();
        }
        while (jsonReader.hasNext()) {
            arrayList.add(Float.valueOf((float) jsonReader.nextDouble()));
        }
        int i3 = 2;
        if (arrayList.size() == 4 && ((Float) arrayList.get(0)).floatValue() == 1.0f) {
            arrayList.set(0, Float.valueOf(0.0f));
            arrayList.add(Float.valueOf(1.0f));
            arrayList.add((Float) arrayList.get(1));
            arrayList.add((Float) arrayList.get(2));
            arrayList.add((Float) arrayList.get(3));
            this.colorPoints = 2;
        }
        if (objArr != false) {
            jsonReader.endArray();
        }
        if (this.colorPoints == -1) {
            this.colorPoints = arrayList.size() / 4;
        }
        int i4 = this.colorPoints;
        float[] fArr = new float[i4];
        int[] iArr = new int[i4];
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i5 < this.colorPoints * 4) {
            int i8 = i5 / 4;
            double dFloatValue = ((Float) arrayList.get(i5)).floatValue();
            int i9 = i5 % 4;
            if (i9 != 0) {
                if (i9 == i2) {
                    i6 = (int) (dFloatValue * 255.0d);
                } else if (i9 == 2) {
                    i7 = (int) (dFloatValue * 255.0d);
                } else if (i9 == 3) {
                    iArr[i8] = Color.argb(255, i6, i7, (int) (dFloatValue * 255.0d));
                }
                i = i2;
            } else {
                if (i8 > 0) {
                    i = i2;
                    float f3 = (float) dFloatValue;
                    if (fArr[i8 - 1] >= f3) {
                        fArr[i8] = f3 + 0.01f;
                    }
                } else {
                    i = i2;
                }
                fArr[i8] = (float) dFloatValue;
            }
            i5++;
            i2 = i;
        }
        int i10 = i2;
        GradientColor gradientColor = new GradientColor(fArr, iArr);
        int i11 = this.colorPoints * 4;
        if (arrayList.size() <= i11) {
            return gradientColor;
        }
        int size = (arrayList.size() - i11) / 2;
        float[] fArr2 = new float[size];
        float[] fArr3 = new float[size];
        int i12 = 0;
        while (i11 < arrayList.size()) {
            if (i11 % 2 == 0) {
                fArr2[i12] = ((Float) arrayList.get(i11)).floatValue();
            } else {
                fArr3[i12] = ((Float) arrayList.get(i11)).floatValue();
                i12++;
            }
            i11++;
        }
        float[] fArr4 = gradientColor.positions;
        if (fArr4.length == 0) {
            fArrCopyOf = fArr2;
        } else if (size == 0) {
            fArrCopyOf = fArr4;
        } else {
            int length = fArr4.length + size;
            fArrCopyOf = new float[length];
            int i13 = 0;
            int i14 = 0;
            int i15 = 0;
            for (int i16 = 0; i16 < length; i16++) {
                float f4 = i14 < fArr4.length ? fArr4[i14] : Float.NaN;
                float f5 = i15 < size ? fArr2[i15] : Float.NaN;
                if (Float.isNaN(f5) || f4 < f5) {
                    fArrCopyOf[i16] = f4;
                    i14++;
                } else if (Float.isNaN(f4) || f5 < f4) {
                    fArrCopyOf[i16] = f5;
                    i15++;
                } else {
                    fArrCopyOf[i16] = f4;
                    i14++;
                    i15++;
                    i13++;
                }
            }
            if (i13 != 0) {
                fArrCopyOf = Arrays.copyOf(fArrCopyOf, length - i13);
            }
        }
        int length2 = fArrCopyOf.length;
        int[] iArr2 = new int[length2];
        int i17 = 0;
        while (i17 < length2) {
            float f6 = fArrCopyOf[i17];
            int iBinarySearch = Arrays.binarySearch(fArr4, f6);
            int iBinarySearch2 = Arrays.binarySearch(fArr2, f6);
            boolean z2 = z;
            int[] iArr3 = gradientColor.colors;
            if (iBinarySearch < 0 || iBinarySearch2 > 0) {
                if (iBinarySearch2 < 0) {
                    iBinarySearch2 = -(iBinarySearch2 + 1);
                }
                float f7 = fArr3[iBinarySearch2];
                if (iArr3.length >= i3 && f6 != fArr4[z2 ? 1 : 0]) {
                    for (int i18 = i10; i18 < fArr4.length; i18++) {
                        float f8 = fArr4[i18];
                        if (f8 >= f6 || i18 == fArr4.length - 1) {
                            int i19 = i18 - 1;
                            float f9 = fArr4[i19];
                            float f10 = (f6 - f9) / (f8 - f9);
                            int i20 = iArr3[i18];
                            int i21 = iArr3[i19];
                            iArgb = Color.argb((int) (f7 * 255.0f), GammaEvaluator.evaluate(f10, Color.red(i21), Color.red(i20)), GammaEvaluator.evaluate(f10, Color.green(i21), Color.green(i20)), GammaEvaluator.evaluate(f10, Color.blue(i21), Color.blue(i20)));
                        }
                    }
                    throw new IllegalArgumentException("Unreachable code.");
                }
                iArgb = iArr3[z2 ? 1 : 0];
                iArr2[i17] = iArgb;
            } else {
                int i22 = iArr3[iBinarySearch];
                if (size >= i3 && f6 > fArr2[z2 ? 1 : 0]) {
                    for (int i23 = i10; i23 < size; i23++) {
                        float f11 = fArr2[i23];
                        if (f11 < f6) {
                            f2 = 255.0f;
                            if (i23 != size - 1) {
                            }
                        } else {
                            f2 = 255.0f;
                        }
                        if (f11 <= f6) {
                            fLerp = fArr3[i23];
                        } else {
                            int i24 = i23 - 1;
                            float f12 = fArr2[i24];
                            fLerp = MiscUtils.lerp(fArr3[i24], fArr3[i23], (f6 - f12) / (f11 - f12));
                        }
                        iArgb2 = Color.argb((int) (fLerp * f2), Color.red(i22), Color.green(i22), Color.blue(i22));
                    }
                    throw new IllegalArgumentException("Unreachable code.");
                }
                iArgb2 = Color.argb((int) (fArr3[z2 ? 1 : 0] * 255.0f), Color.red(i22), Color.green(i22), Color.blue(i22));
                iArr2[i17] = iArgb2;
            }
            i17++;
            z = z2 ? 1 : 0;
            i3 = 2;
        }
        return new GradientColor(fArrCopyOf, iArr2);
    }
}
