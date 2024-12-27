package com.android.systemui.plugins.clocks;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ClockAnimations {
    void charge();

    void doze(float f);

    void enter();

    void fold(float f);

    void onPickerCarouselSwiping(float f);

    void onPositionUpdated(float f, float f2);

    void onPositionUpdated(int i, int i2, float f);
}
