package com.android.systemui.qs.panelresource;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.util.SettingsHelper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSPanelResourceCommon {
    public static final Companion Companion = new Companion(null);
    public final boolean isEmergencyMode;
    public final Lazy knoxStateMonitor$delegate;
    public final Lazy settingsHelper$delegate;
    public final Lazy shadeHeaderController$delegate;
    public float tileExpandedWidthRatio;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static int dp(int i, Context context) {
            return context.getResources().getDimensionPixelSize(i);
        }

        /* renamed from: float, reason: not valid java name */
        public static float m2886float(int i, Context context) {
            return context.getResources().getFloat(i);
        }

        /* renamed from: int, reason: not valid java name */
        public static int m2887int(int i, Context context) {
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

        private Companion() {
        }
    }

    public SecQSPanelResourceCommon() {
        final int i = 0;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.panelresource.SecQSPanelResourceCommon$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 1:
                        SecQSPanelResourceCommon.Companion companion2 = SecQSPanelResourceCommon.Companion;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    default:
                        SecQSPanelResourceCommon.Companion companion3 = SecQSPanelResourceCommon.Companion;
                        return (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
                }
            }
        });
        this.settingsHelper$delegate = lazy;
        final int i2 = 1;
        this.shadeHeaderController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.panelresource.SecQSPanelResourceCommon$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 1:
                        SecQSPanelResourceCommon.Companion companion2 = SecQSPanelResourceCommon.Companion;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    default:
                        SecQSPanelResourceCommon.Companion companion3 = SecQSPanelResourceCommon.Companion;
                        return (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
                }
            }
        });
        final int i3 = 2;
        this.knoxStateMonitor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.panelresource.SecQSPanelResourceCommon$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 1:
                        SecQSPanelResourceCommon.Companion companion2 = SecQSPanelResourceCommon.Companion;
                        return (ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class);
                    default:
                        SecQSPanelResourceCommon.Companion companion3 = SecQSPanelResourceCommon.Companion;
                        return (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
                }
            }
        });
        this.isEmergencyMode = ((SettingsHelper) lazy.getValue()).isEmergencyMode();
        this.tileExpandedWidthRatio = 1.0f;
    }
}
