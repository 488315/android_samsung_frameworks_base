package com.android.systemui.plugins.clocks;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ClockAnimations {
    void charge();

    void doze(float f);

    void enter();

    void fold(float f);

    void onFidgetTap(float f, float f2);

    void onFontAxesChanged(ClockAxisStyle clockAxisStyle);

    void onPickerCarouselSwiping(float f);

    void onPositionUpdated(float f, float f2);

    void onPositionUpdated(int i, int i2, float f);
}
