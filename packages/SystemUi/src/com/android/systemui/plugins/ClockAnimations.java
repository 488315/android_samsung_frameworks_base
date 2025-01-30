package com.android.systemui.plugins;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ClockAnimations {
    void charge();

    void doze(float f);

    void enter();

    void fold(float f);

    void onPickerCarouselSwiping(float f);

    void onPositionUpdated(int i, int i2, float f);
}
