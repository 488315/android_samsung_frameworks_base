package com.samsung.android.wallpaper.legibilitycolors.utils.interpolater;

import com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing;

/* loaded from: classes6.dex */
public class EasingSineFunc implements IEasing {
    private static EasingSineFunc mInstance;

    private EasingSineFunc() {
    }

    public static EasingSineFunc getInstance() {
        if (mInstance == null) {
            mInstance = new EasingSineFunc();
        }
        return mInstance;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeIn(float f, float f2, float f3, float f4) {
        return ((-f3) * ((float) Math.cos((f / f4) * 1.5707963267948966d))) + f3 + f2;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeInOut(float f, float f2, float f3, float f4) {
        return (((-f3) / 2.0f) * (((float) Math.cos((f * 3.141592653589793d) / f4)) - 1.0f)) + f2;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeOut(float f, float f2, float f3, float f4) {
        return (f3 * ((float) Math.sin((f / f4) * 1.5707963267948966d))) + f2;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeOutIn(float f, float f2, float f3, float f4) {
        if (f < f4 / 2.0f) {
            return ((f3 / 2.0f) * ((float) Math.sin(((f * 2.0f) / f4) * 1.5707963267948966d))) + f2;
        }
        float f5 = f3 / 2.0f;
        return ((-f5) * ((float) Math.cos((((f * 2.0f) - f4) / f4) * 1.5707963267948966d))) + f5 + f2 + f5;
    }

    /* renamed from: com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.EasingSineFunc$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$interpolater$IEasing$EEasing;

        static {
            int[] iArr = new int[IEasing.EEasing.values().length];
            $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$interpolater$IEasing$EEasing = iArr;
            try {
                iArr[IEasing.EEasing.In.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$interpolater$IEasing$EEasing[IEasing.EEasing.Out.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$interpolater$IEasing$EEasing[IEasing.EEasing.InOut.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$interpolater$IEasing$EEasing[IEasing.EEasing.OutIn.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float ease(float f, float f2, float f3, float f4, IEasing.EEasing eEasing) {
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$wallpaper$legibilitycolors$utils$interpolater$IEasing$EEasing[eEasing.ordinal()];
        if (i == 1) {
            return easeIn(f, f2, f3, f4);
        }
        if (i == 2) {
            return easeOut(f, f2, f3, f4);
        }
        if (i != 3) {
            return i != 4 ? ((f3 * f) / f4) + f2 : easeOutIn(f, f2, f3, f4);
        }
        return easeInOut(f, f2, f3, f4);
    }
}
