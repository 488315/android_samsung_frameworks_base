package com.android.systemui.communal.util;

import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DensityUtils {
    public static final Companion Companion = new Companion(null);
    public static final IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getAdjustedDp-u2uoSUM, reason: not valid java name */
        public static float m1089getAdjustedDpu2uoSUM(int i) {
            float f = i;
            Dp.Companion companion = Dp.Companion;
            return (DensityUtils.windowManagerService != null ? r0.getInitialDisplayDensity(0) / r0.getBaseDisplayDensity(0) : 1.0f) * f;
        }

        private Companion() {
        }
    }
}
