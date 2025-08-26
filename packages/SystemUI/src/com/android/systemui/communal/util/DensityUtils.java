package com.android.systemui.communal.util;

import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class DensityUtils {
    public static final Companion Companion = new Companion(null);
    public static final IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getAdjustedDp-u2uoSUM, reason: not valid java name */
        public static float m1091getAdjustedDpu2uoSUM(int i) {
            float f = i;
            Dp.Companion companion = Dp.Companion;
            return (DensityUtils.windowManagerService != null ? r0.getInitialDisplayDensity(0) / r0.getBaseDisplayDensity(0) : 1.0f) * f;
        }

        private Companion() {
        }
    }
}
