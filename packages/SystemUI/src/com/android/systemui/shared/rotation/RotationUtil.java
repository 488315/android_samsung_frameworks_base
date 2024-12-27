package com.android.systemui.shared.rotation;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RotationUtil {
    public static final Companion Companion = new Companion(null);
    public static final boolean[][] ccwCheckArray = {new boolean[]{false, true, true, false}, new boolean[]{false, false, false, true}, new boolean[]{true, false, false, true}, new boolean[]{true, true, true, false}};
    public static int floatingButtonPosition;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
