package com.android.systemui.navigationbar.util;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MarqueeLogic {
    public int horizontalShift;
    public float scaleFactor;
    public int verticalShift;
    public int horizontalMoved = 1;
    public int verticalMoved = 1;

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
