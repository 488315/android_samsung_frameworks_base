package com.android.systemui.facewidget.dex;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.plugins.keyguardstatusview.PluginClockProvider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class DexClockControllerImpl implements DexClockController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public DexClockControllerCallback callback;
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
            FrameLayout frameLayout = this.f$0.rootView;
            Object systemService = (frameLayout == null || (context = frameLayout.getContext()) == null) ? null : context.getSystemService("wallpaper");
            if (systemService instanceof WallpaperManager) {
                return (WallpaperManager) systemService;
            }
            return null;
        }
    });

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

    public final void addDexClock(final FrameLayout frameLayout) {
        PluginClockProvider pluginClockProvider;
        Context context = frameLayout.getContext();
        if (context == null) {
            return;
        }
        Lazy lazy = this.externalClockProvider$delegate;
        ExternalClockProvider externalClockProvider = (ExternalClockProvider) lazy.getValue();
        externalClockProvider.getClass();
        try {
            pluginClockProvider = externalClockProvider.mClockProvider;
        } catch (Throwable unused) {
        }
        View dexClockView = pluginClockProvider != null ? pluginClockProvider.getDexClockView(context, 22) : null;
        if (dexClockView != null) {
            String str = this.tag;
            Log.i(str, "addDexClock: clockView=" + dexClockView);
            this.dexClockView = dexClockView;
            DexClockControllerCallback dexClockControllerCallback = this.callback;
            if (dexClockControllerCallback != null) {
                dexClockControllerCallback.onDexClockChanged(dexClockView);
            }
            View view = this.dexClockView;
            if (view != null) {
                int fontColor = 0;
                if (!this.isWireless) {
                    WallpaperManager wallpaperManager = (WallpaperManager) this.wallpaperManager$delegate.getValue();
                    SemWallpaperColors semWallpaperColorsSemGetWallpaperColors = wallpaperManager != null ? wallpaperManager.semGetWallpaperColors(10) : null;
                    SemWallpaperColors semWallpaperColors = semWallpaperColorsSemGetWallpaperColors != null ? semWallpaperColorsSemGetWallpaperColors : null;
                    if (semWallpaperColors != null) {
                        SemWallpaperColors.Item item = semWallpaperColors.get(32L);
                        Log.i(str, "getFontColor: bodyTopItem=" + item);
                        if (item != null) {
                            fontColor = item.getFontColor();
                        }
                    }
                }
                Log.i(str, "updateDexClockColor: " + fontColor);
                ExternalClockProvider externalClockProvider2 = (ExternalClockProvider) lazy.getValue();
                externalClockProvider2.getClass();
                try {
                    PluginClockProvider pluginClockProvider2 = externalClockProvider2.mClockProvider;
                    if (pluginClockProvider2 != null) {
                        pluginClockProvider2.updateDexClockColor(view, fontColor);
                    }
                } catch (Throwable unused2) {
                }
            }
            if (!dexClockView.isLaidOut() || dexClockView.isLayoutRequested()) {
                dexClockView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.facewidget.dex.DexClockControllerImpl$addDexClock$lambda$7$$inlined$doOnLayout$1
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        view2.removeOnLayoutChangeListener(this);
                        DexClockControllerImpl dexClockControllerImpl = this.this$0;
                        FrameLayout frameLayout2 = frameLayout;
                        int i9 = DexClockControllerImpl.$r8$clinit;
                        dexClockControllerImpl.getClass();
                        float f = frameLayout2.getContext().getResources().getDisplayMetrics().heightPixels * 0.23f;
                        view2.setPivotX(view2.getWidth() / 2.0f);
                        view2.setPivotY(0.0f);
                        float height = ((float) view2.getHeight()) > f ? f / view2.getHeight() : 1.0f;
                        view2.setScaleX(height);
                        view2.setScaleY(height);
                        String str2 = this.this$0.tag;
                        StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("doOnLayout: maxHeight=", f, ", pivotX = ", view2.getPivotX(), ", scale=");
                        sbM.append(height);
                        Log.i(str2, sbM.toString());
                    }
                });
            } else {
                float f = frameLayout.getContext().getResources().getDisplayMetrics().heightPixels * 0.23f;
                dexClockView.setPivotX(dexClockView.getWidth() / 2.0f);
                dexClockView.setPivotY(0.0f);
                float height = ((float) dexClockView.getHeight()) > f ? f / dexClockView.getHeight() : 1.0f;
                dexClockView.setScaleX(height);
                dexClockView.setScaleY(height);
                StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("doOnLayout: maxHeight=", f, ", pivotX = ", dexClockView.getPivotX(), ", scale=");
                sbM.append(height);
                Log.i(str, sbM.toString());
            }
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2, 49);
            int i = (int) (frameLayout.getContext().getResources().getDisplayMetrics().heightPixels * 0.15f);
            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "addDexClock: topMargin=", str);
            layoutParams.topMargin = i;
            Unit unit = Unit.INSTANCE;
            frameLayout.addView(dexClockView, layoutParams);
        }
    }

    public final void initDexClock(final FrameLayout frameLayout, final Display display, DexClockControllerCallback dexClockControllerCallback, boolean z) {
        Log.i(this.tag, "initDexClock: rootView=" + frameLayout + ", display=" + display);
        this.isWireless = z;
        this.rootView = frameLayout;
        this.callback = dexClockControllerCallback;
        if (!((PluginFaceWidgetManager) this.faceWidgetManager$delegate.getValue()).mIsConnected) {
            ((PluginAODManager) this.pluginAODManager$delegate.getValue()).addConnectionRunnable(new Runnable() { // from class: com.android.systemui.facewidget.dex.DexClockControllerImpl.initDexClock.1
                @Override // java.lang.Runnable
                public final void run() {
                    DexClockControllerImpl dexClockControllerImpl = DexClockControllerImpl.this;
                    FrameLayout frameLayout2 = frameLayout;
                    display.getDisplayId();
                    int i = DexClockControllerImpl.$r8$clinit;
                    dexClockControllerImpl.addDexClock(frameLayout2);
                }
            });
        } else {
            display.getDisplayId();
            addDexClock(frameLayout);
        }
    }
}
