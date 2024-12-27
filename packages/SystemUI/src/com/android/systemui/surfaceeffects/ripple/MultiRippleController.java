package com.android.systemui.surfaceeffects.ripple;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MultiRippleController {
    public final MultiRippleView multipleRippleView;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getMAX_RIPPLE_NUMBER$annotations() {
        }
    }

    static {
        new Companion(null);
    }

    public MultiRippleController(MultiRippleView multiRippleView) {
        this.multipleRippleView = multiRippleView;
    }
}
