package com.android.systemui.facewidget.dex;

import android.app.WallpaperManager;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$dexClockChangedCallback$1;
import com.android.systemui.Dependency;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DexClockControllerImpl implements DexClockController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ConnectedDisplayKeyguardPresentation$dexClockChangedCallback$1 callback;
    public View dexClockView;
    public final Lazy externalClockProvider$delegate;
    public final Lazy faceWidgetManager$delegate;
    public boolean isWireless;
    public final Lazy pluginAODManager$delegate;
    public FrameLayout rootView;
    public final String tag = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(hashCode() % 10000, "DexClockController(", ")");
    public final Lazy wallpaperManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.facewidget.dex.DexClockControllerImpl$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Context context;
            FrameLayout frameLayout = DexClockControllerImpl.this.rootView;
            Object systemService = (frameLayout == null || (context = frameLayout.getContext()) == null) ? null : context.getSystemService("wallpaper");
            if (systemService instanceof WallpaperManager) {
                return (WallpaperManager) systemService;
            }
            return null;
        }
    });

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DexClockControllerImpl() {
        final int i = 0;
        this.externalClockProvider$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.facewidget.dex.DexClockControllerImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = DexClockControllerImpl.$r8$clinit;
                        return (ExternalClockProvider) Dependency.sDependency.getDependencyInner(ExternalClockProvider.class);
                    case 1:
                        int i3 = DexClockControllerImpl.$r8$clinit;
                        return (PluginAODManager) Dependency.sDependency.getDependencyInner(PluginAODManager.class);
                    default:
                        int i4 = DexClockControllerImpl.$r8$clinit;
                        return (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
                }
            }
        });
        final int i2 = 1;
        this.pluginAODManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.facewidget.dex.DexClockControllerImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = DexClockControllerImpl.$r8$clinit;
                        return (ExternalClockProvider) Dependency.sDependency.getDependencyInner(ExternalClockProvider.class);
                    case 1:
                        int i3 = DexClockControllerImpl.$r8$clinit;
                        return (PluginAODManager) Dependency.sDependency.getDependencyInner(PluginAODManager.class);
                    default:
                        int i4 = DexClockControllerImpl.$r8$clinit;
                        return (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
                }
            }
        });
        final int i3 = 2;
        this.faceWidgetManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.facewidget.dex.DexClockControllerImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        int i22 = DexClockControllerImpl.$r8$clinit;
                        return (ExternalClockProvider) Dependency.sDependency.getDependencyInner(ExternalClockProvider.class);
                    case 1:
                        int i32 = DexClockControllerImpl.$r8$clinit;
                        return (PluginAODManager) Dependency.sDependency.getDependencyInner(PluginAODManager.class);
                    default:
                        int i4 = DexClockControllerImpl.$r8$clinit;
                        return (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addDexClock(final android.widget.FrameLayout r10) {
        /*
            Method dump skipped, instructions count: 324
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.facewidget.dex.DexClockControllerImpl.addDexClock(android.widget.FrameLayout):void");
    }
}
