package com.android.systemui.plugins;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum ClockTickRate {
    PER_MINUTE(2),
    PER_SECOND(1),
    PER_FRAME(0);

    private final int value;

    ClockTickRate(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
