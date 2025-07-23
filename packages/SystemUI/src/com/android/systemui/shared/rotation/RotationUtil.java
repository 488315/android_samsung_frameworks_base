package com.android.systemui.shared.rotation;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RotationUtil {
    public static final Companion Companion = new Companion(null);
    public static final boolean[][] ccwCheckArray = {new boolean[]{false, true, true, false}, new boolean[]{false, false, false, true}, new boolean[]{true, false, false, true}, new boolean[]{true, true, true, false}};
    public static int floatingButtonPosition;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
