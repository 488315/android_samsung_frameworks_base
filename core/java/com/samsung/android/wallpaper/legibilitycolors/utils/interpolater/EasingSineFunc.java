package com.samsung.android.wallpaper.legibilitycolors.utils.interpolater;


public class EasingSineFunc implements IEasing {
    private static EasingSineFunc mInstance = null;

    private EasingSineFunc() {}

    public static EasingSineFunc getInstance() {
        if (mInstance == null) {
            mInstance = new EasingSineFunc();
        }
        return mInstance;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeIn(float t, float b, float c, float d) {
        return ((-c) * ((float) Math.cos((t / d) * 1.5707963267948966d))) + c + b;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeInOut(float t, float b, float c, float d) {
        return (((-c) / 2.0f) * (((float) Math.cos((t * 3.141592653589793d) / d)) - 1.0f)) + b;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeOut(float t, float b, float c, float d) {
        return (((float) Math.sin((t / d) * 1.5707963267948966d)) * c) + b;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeOutIn(float t, float b, float c, float d) {
        return t < d / 2.0f
                ? ((c / 2.0f) * ((float) Math.sin(((2.0f * t) / d) * 1.5707963267948966d))) + b
                : ((-(c / 2.0f)) * ((float) Math.cos((((t * 2.0f) - d) / d) * 1.5707963267948966d)))
                        + (c / 2.0f)
                        + (c / 2.0f)
                        + b;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float ease(float t, float b, float c, float d, IEasing.EEasing easing) {
        switch (easing) {
            case In:
                return easeIn(t, b, c, d);
            case Out:
                return easeOut(t, b, c, d);
            case InOut:
                return easeInOut(t, b, c, d);
            case OutIn:
                return easeOutIn(t, b, c, d);
            default:
                return ((c * t) / d) + b;
        }
    }
}
