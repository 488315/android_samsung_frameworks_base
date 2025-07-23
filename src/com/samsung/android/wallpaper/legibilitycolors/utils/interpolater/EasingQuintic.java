package com.samsung.android.wallpaper.legibilitycolors.utils.interpolater;

import com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing;

/* loaded from: classes6.dex */
public class EasingQuintic implements IEasing {
    private static EasingQuintic mInstance;

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeIn(float f, float f2, float f3, float f4) {
        float f5 = f / f4;
        return (f3 * f5 * f5 * f5 * f5 * f5) + f2;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeInOut(float f, float f2, float f3, float f4) {
        float f5;
        float f6 = f / (f4 / 2.0f);
        if (f6 < 1.0f) {
            f5 = (f3 / 2.0f) * f6 * f6 * f6 * f6 * f6;
        } else {
            float f7 = f6 - 2.0f;
            f5 = (f3 / 2.0f) * ((f7 * f7 * f7 * f7 * f7) + 2.0f);
        }
        return f5 + f2;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeOut(float f, float f2, float f3, float f4) {
        float f5 = (f / f4) - 1.0f;
        return (f3 * ((f5 * f5 * f5 * f5 * f5) + 1.0f)) + f2;
    }

    @Override // com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.IEasing
    public float easeOutIn(float f, float f2, float f3, float f4) {
        if (f < f4 / 2.0f) {
            float f5 = ((f * 2.0f) / f4) - 1.0f;
            return ((f3 / 2.0f) * ((f5 * f5 * f5 * f5 * f5) + 1.0f)) + f2;
        }
        float f6 = f3 / 2.0f;
        float f7 = ((f * 2.0f) - f4) / f4;
        return (f6 * f7 * f7 * f7 * f7 * f7) + f2 + f6;
    }

    private EasingQuintic() {
    }

    public static EasingQuintic getInstance() {
        if (mInstance == null) {
            mInstance = new EasingQuintic();
        }
        return mInstance;
    }

    /* renamed from: com.samsung.android.wallpaper.legibilitycolors.utils.interpolater.EasingQuintic$1, reason: invalid class name */
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
