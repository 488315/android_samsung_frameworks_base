package com.android.systemui.statusbar.events;

import android.graphics.Rect;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BatteryChipAnimationUtils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static Rect getBounds(View view) {
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int i = iArr[0];
            return new Rect(i, iArr[1], view.getMeasuredWidth() + i, view.getMeasuredHeight() + iArr[1]);
        }

        private Companion() {
        }
    }
}
