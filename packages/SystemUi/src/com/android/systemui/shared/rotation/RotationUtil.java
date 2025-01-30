package com.android.systemui.shared.rotation;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RotationUtil {
    public static final Companion Companion = new Companion(null);
    public static final boolean[][] ccwCheckArray = {new boolean[]{false, true, true, false}, new boolean[]{false, false, false, true}, new boolean[]{true, false, false, true}, new boolean[]{true, true, true, false}};
    public static int floatingButtonPosition;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
