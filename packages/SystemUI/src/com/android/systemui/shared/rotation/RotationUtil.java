package com.android.systemui.shared.rotation;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class RotationUtil {
    public static final Companion Companion = new Companion(null);
    public static final boolean[][] ccwCheckArray = {new boolean[]{false, true, true, false}, new boolean[]{false, false, false, true}, new boolean[]{true, false, false, true}, new boolean[]{true, true, true, false}};
    public static int floatingButtonPosition;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
