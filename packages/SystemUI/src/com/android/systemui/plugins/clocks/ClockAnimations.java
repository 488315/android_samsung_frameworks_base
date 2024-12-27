package com.android.systemui.plugins.clocks;

public interface ClockAnimations {
    void charge();

    void doze(float f);

    void enter();

    void fold(float f);

    void onPickerCarouselSwiping(float f);

    void onPositionUpdated(float f, float f2);

    void onPositionUpdated(int i, int i2, float f);
}
