package android.content.res;

/* loaded from: classes.dex */
public interface FontScaleConverter {
    float convertDpToSp(float f);

    float convertSpToDp(float f);

    static boolean isNonLinearFontScalingActive(float f) {
        return FontScaleConverterFactory.isNonLinearFontScalingActive(f);
    }

    static FontScaleConverter forScale(float f) {
        return FontScaleConverterFactory.forScale(f);
    }
}
