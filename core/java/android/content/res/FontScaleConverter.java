package android.content.res;

public interface FontScaleConverter {
    float convertDpToSp(float f);

    float convertSpToDp(float f);

    static boolean isNonLinearFontScalingActive(float fontScale) {
        return FontScaleConverterFactory.isNonLinearFontScalingActive(fontScale);
    }

    static FontScaleConverter forScale(float fontScale) {
        return FontScaleConverterFactory.forScale(fontScale);
    }
}
