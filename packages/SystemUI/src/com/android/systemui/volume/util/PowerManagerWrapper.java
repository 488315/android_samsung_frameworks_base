package com.android.systemui.volume.util;

import android.os.PowerManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class PowerManagerWrapper {
    public PowerManager.WakeLock wakeLock;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }
}
