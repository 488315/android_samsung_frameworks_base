package com.samsung.android.wallpaper.legibilitycolors.utils.interpolater;

import com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing;

/* loaded from: classes5.dex */
public class EasingSineFunc implements IEasing {
    private static EasingSineFunc mInstance = null;

    private EasingSineFunc() {
    }

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
        return t < d / 2.0f ? ((c / 2.0f) * ((float) Math.sin(((2.0f * t) / d) * 1.5707963267948966d))) + b : ((-(c / 2.0f)) * ((float) Math.cos((((t * 2.0f) - d) / d) * 1.5707963267948966d))) + (c / 2.0f) + (c / 2.0f) + b;
    }

    /* renamed from: com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.EasingSineFunc$1 */
    static /* synthetic */ class C53241 {

        /* renamed from: $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$interpolater$IEasing$EEasing */
        static final /* synthetic */ int[] f3085xed974fcc;

        static {
            int[] iArr = new int[IEasing.EEasing.values().length];
            f3085xed974fcc = iArr;
            try {
                iArr[IEasing.EEasing.In.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f3085xed974fcc[IEasing.EEasing.Out.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f3085xed974fcc[IEasing.EEasing.InOut.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f3085xed974fcc[IEasing.EEasing.OutIn.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float ease(float t, float b, float c, float d, IEasing.EEasing easing) {
        switch (C53241.f3085xed974fcc[easing.ordinal()]) {
            case 1:
                return easeIn(t, b, c, d);
            case 2:
                return easeOut(t, b, c, d);
            case 3:
                return easeInOut(t, b, c, d);
            case 4:
                return easeOutIn(t, b, c, d);
            default:
                return ((c * t) / d) + b;
        }
    }
}
