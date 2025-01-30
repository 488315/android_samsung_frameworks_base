package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum DevicePosture {
    UNKNOWN,
    CLOSED,
    HALF_OPENED,
    OPENED,
    FLIPPED;

    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static DevicePosture toPosture(int i) {
            return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? DevicePosture.UNKNOWN : DevicePosture.FLIPPED : DevicePosture.OPENED : DevicePosture.HALF_OPENED : DevicePosture.CLOSED : DevicePosture.UNKNOWN;
        }
    }
}
