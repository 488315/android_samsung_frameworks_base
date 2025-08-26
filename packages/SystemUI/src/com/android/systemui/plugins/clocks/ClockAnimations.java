package com.android.systemui.plugins.clocks;

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
