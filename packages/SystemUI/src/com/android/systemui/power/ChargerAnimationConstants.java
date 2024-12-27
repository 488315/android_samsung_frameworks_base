package com.android.systemui.power;

import com.airbnb.lottie.model.KeyPath;

public final class ChargerAnimationConstants {
    public static final ChargerAnimationConstants INSTANCE = new ChargerAnimationConstants();
    public static final KeyPath WAVE_KEY_PATH = new KeyPath("**", "wave", "Rectangle 1", "Fill 1");

    private ChargerAnimationConstants() {
    }
}
