package com.android.systemui.qs.panelresource;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.util.SettingsHelper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SecQSPanelResourceCommon {
    public static final Companion Companion = new Companion(null);
    public final boolean isEmergencyMode;
    public final Lazy knoxStateMonitor$delegate;
    public final Lazy settingsHelper$delegate;
    public final Lazy shadeHeaderController$delegate;
    public float tileExpandedWidthRatio;

    public final class Companion {
        private Companion() {
        }

        public static int dp(int i, Context context) {
            return context.getResources().getDimensionPixelSize(i);
        }

        /* renamed from: float, reason: not valid java name */
        public static float m2073float(int i, Context context) {
            return context.getResources().getFloat(i);
        }

        /* renamed from: int, reason: not valid java name */
        public static int m2074int(int i, Context context) {
            return context.getResources().getInteger(i);
        }

        public static boolean isLandscape(Context context) {
            return context.getResources().getConfiguration().orientation == 2;
        }

        public static boolean isPortrait(Context context) {
            return context.getResources().getConfiguration().orientation == 1;
        }

        public static String string(int i, Context context) {
            return context.getResources().getString(i);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SecQSPanelResourceCommon() {
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.panelresource.SecQSPanelResourceCommon$settingsHelper$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
            }
        });
        this.settingsHelper$delegate = lazy;
        this.shadeHeaderController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.panelresource.SecQSPanelResourceCommon$shadeHeaderController$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
            }
        });
        this.knoxStateMonitor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.panelresource.SecQSPanelResourceCommon$knoxStateMonitor$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
            }
        });
        this.isEmergencyMode = ((SettingsHelper) lazy.getValue()).isEmergencyMode();
        this.tileExpandedWidthRatio = 1.0f;
    }
}
