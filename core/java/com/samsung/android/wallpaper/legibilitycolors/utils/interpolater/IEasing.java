package com.samsung.android.wallpaper.legibilitycolors.utils.interpolater;

/* loaded from: classes5.dex */
public interface IEasing {

    public enum EEasing {
        None,
        In,
        InOut,
        Out,
        OutIn
    }

    float ease(float f, float f2, float f3, float f4, EEasing eEasing);

    float easeIn(float f, float f2, float f3, float f4);

    float easeInOut(float f, float f2, float f3, float f4);

    float easeOut(float f, float f2, float f3, float f4);

    float easeOutIn(float f, float f2, float f3, float f4);
}
