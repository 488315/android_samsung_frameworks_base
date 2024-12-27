package com.android.systemui.statusbar.events;

import android.graphics.Rect;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class BatteryChipAnimationUtils {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        private Companion() {
        }

        public static Rect getBounds(View view) {
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int i = iArr[0];
            return new Rect(i, iArr[1], view.getWidth() + i, view.getHeight() + iArr[1]);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
