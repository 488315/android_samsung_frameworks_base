package com.android.wm.shell.windowdecor.tiling;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Binder;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;
import com.android.wm.shell.windowdecor.DragResizeWindowGeometry;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopTilingWindowDecoration$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ DesktopTilingWindowDecoration f$0;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ DesktopTilingWindowDecoration$$ExternalSyntheticLambda2(DesktopTilingWindowDecoration desktopTilingWindowDecoration, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        this.f$0 = desktopTilingWindowDecoration;
        this.f$1 = runningTaskInfo;
        this.f$2 = z;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        DesktopModeWindowDecoration desktopModeWindowDecoration;
        DesktopModeWindowDecoration desktopModeWindowDecoration2;
        final DesktopTilingDividerWindowManager desktopTilingDividerWindowManager;
        DesktopModeWindowDecoration desktopModeWindowDecoration3;
        final SurfaceControl surfaceControl;
        Rect rect;
        Rect rect2;
        Rect rect3;
        String str;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.f$1;
        String str2 = DesktopTilingWindowDecoration.TAG;
        Configuration configuration = runningTaskInfo.configuration;
        DesktopTilingWindowDecoration desktopTilingWindowDecoration = this.f$0;
        if (desktopTilingWindowDecoration.leftTaskResizingHelper != null && desktopTilingWindowDecoration.rightTaskResizingHelper != null) {
            if (!desktopTilingWindowDecoration.isTilingManagerInitialised) {
                DisplayController displayController = desktopTilingWindowDecoration.displayController;
                int i = desktopTilingWindowDecoration.displayId;
                DisplayLayout displayLayout = displayController.getDisplayLayout(i);
                SurfaceControl.Builder builder = new SurfaceControl.Builder();
                desktopTilingWindowDecoration.rootTdaOrganizer.attachToDisplayArea(i, builder);
                SurfaceControl build = builder.setName("Tiling Divider").setContainerLayer().build();
                Context displayContext = displayController.getDisplayContext(i);
                String str3 = null;
                if (displayContext == null) {
                    desktopTilingDividerWindowManager = null;
                } else {
                    if (displayLayout != null) {
                        Rect rect4 = new Rect();
                        displayLayout.getStableBounds(rect4, false);
                        DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper = desktopTilingWindowDecoration.leftTaskResizingHelper;
                        if (appResizingHelper == null || (rect2 = appResizingHelper.bounds) == null) {
                            rect = new Rect();
                        } else {
                            int i2 = rect2.right;
                            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper2 = desktopTilingWindowDecoration.rightTaskResizingHelper;
                            rect = (appResizingHelper2 == null || (rect3 = appResizingHelper2.bounds) == null) ? new Rect() : new Rect(i2, rect4.top, rect3.left, rect4.bottom);
                        }
                        desktopTilingWindowDecoration.dividerBounds = rect;
                        Supplier supplier = desktopTilingWindowDecoration.transactionSupplier;
                        Rect rect5 = desktopTilingWindowDecoration.dividerBounds;
                        desktopTilingDividerWindowManager = new DesktopTilingDividerWindowManager(configuration, DesktopTilingWindowDecoration.TAG, build, desktopTilingWindowDecoration.syncQueue, desktopTilingWindowDecoration, supplier, rect5 == null ? null : rect5, displayContext, desktopTilingWindowDecoration.isDarkMode);
                    } else {
                        desktopTilingDividerWindowManager = null;
                    }
                    DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper3 = desktopTilingWindowDecoration.leftTaskResizingHelper;
                    if (appResizingHelper3 != null && (desktopModeWindowDecoration3 = appResizingHelper3.desktopModeWindowDecoration) != null && (surfaceControl = desktopModeWindowDecoration3.mTaskSurface) != null && desktopTilingDividerWindowManager != null) {
                        Context context = desktopTilingDividerWindowManager.displayContext;
                        SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(context, context.getDisplay(), desktopTilingDividerWindowManager, "DesktopTilingManager");
                        TilingDividerView tilingDividerView = (TilingDividerView) LayoutInflater.from(desktopTilingDividerWindowManager.displayContext).inflate(R.layout.mw_desktop_mode_tile_divider, (ViewGroup) null);
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams((desktopTilingDividerWindowManager.maxRoundedCornerRadius * 2) + desktopTilingDividerWindowManager.dividerBounds.width(), desktopTilingDividerWindowManager.dividerBounds.height(), 2034, 537133096, -3);
                        layoutParams.token = new Binder();
                        layoutParams.setTitle(desktopTilingDividerWindowManager.windowName);
                        layoutParams.privateFlags |= 536870976;
                        surfaceControlViewHost.setView(tilingDividerView, layoutParams);
                        Rect rect6 = new Rect();
                        rect6.set(desktopTilingDividerWindowManager.dividerBounds);
                        tilingDividerView.setup(desktopTilingDividerWindowManager, rect6, desktopTilingDividerWindowManager.handleRegionSize, desktopTilingDividerWindowManager.isDarkMode);
                        final SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) desktopTilingDividerWindowManager.transactionSupplier.get();
                        final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                        ofFloat.setDuration(300L);
                        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.tiling.DesktopTilingDividerWindowManager$generateViewHost$dividerAnimator$1$1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                transaction.setAlpha(desktopTilingDividerWindowManager.leash, ((Float) ofFloat.getAnimatedValue()).floatValue()).apply();
                            }
                        });
                        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.tiling.DesktopTilingDividerWindowManager$generateViewHost$dividerAnimator$1$2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                transaction.setAlpha(desktopTilingDividerWindowManager.leash, 1.0f).apply();
                                desktopTilingDividerWindowManager.dividerShown = true;
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                                SurfaceControl.Transaction relativeLayer = transaction.setRelativeLayer(desktopTilingDividerWindowManager.leash, surfaceControl, 1);
                                DesktopTilingDividerWindowManager desktopTilingDividerWindowManager2 = desktopTilingDividerWindowManager;
                                SurfaceControl surfaceControl2 = desktopTilingDividerWindowManager2.leash;
                                Rect rect7 = desktopTilingDividerWindowManager2.dividerBounds;
                                relativeLayer.setPosition(surfaceControl2, rect7.left - desktopTilingDividerWindowManager2.maxRoundedCornerRadius, rect7.top).setAlpha(desktopTilingDividerWindowManager.leash, 0.0f).show(desktopTilingDividerWindowManager.leash).apply();
                            }
                        });
                        ofFloat.start();
                        desktopTilingDividerWindowManager.viewHost = surfaceControlViewHost;
                        desktopTilingDividerWindowManager.tilingDividerView = tilingDividerView;
                        desktopTilingDividerWindowManager.updateTouchRegion();
                        tilingDividerView.addOnLayoutChangeListener(desktopTilingDividerWindowManager);
                    }
                }
                desktopTilingWindowDecoration.desktopTilingDividerWindowManager = desktopTilingDividerWindowManager;
                desktopTilingWindowDecoration.isTilingManagerInitialised = true;
                desktopTilingWindowDecoration.focusTransitionObserver.setLocalFocusTransitionListener(desktopTilingWindowDecoration, desktopTilingWindowDecoration.mainExecutor);
                if (CoreRune.MW_SA_LOGGING) {
                    DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper4 = desktopTilingWindowDecoration.leftTaskResizingHelper;
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = appResizingHelper4 != null ? appResizingHelper4.taskInfo : null;
                    if ((runningTaskInfo2 != null ? runningTaskInfo2.topActivity : null) != null) {
                        ComponentName componentName = runningTaskInfo2.topActivity;
                        str = componentName != null ? componentName.getPackageName() : null;
                    } else {
                        str = "";
                    }
                    DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper5 = desktopTilingWindowDecoration.rightTaskResizingHelper;
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = appResizingHelper5 != null ? appResizingHelper5.taskInfo : null;
                    if ((runningTaskInfo3 != null ? runningTaskInfo3.topActivity : null) != null) {
                        ComponentName componentName2 = runningTaskInfo3.topActivity;
                        if (componentName2 != null) {
                            str3 = componentName2.getPackageName();
                        }
                    } else {
                        str3 = "";
                    }
                    CoreSaLogger.logForAdvanced("3108", AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ",", str3), i == 0 ? 1 : 2);
                }
            }
            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper6 = desktopTilingWindowDecoration.leftTaskResizingHelper;
            if (appResizingHelper6 != null) {
                appResizingHelper6.initIfNeeded();
            }
            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper7 = desktopTilingWindowDecoration.rightTaskResizingHelper;
            if (appResizingHelper7 != null) {
                appResizingHelper7.initIfNeeded();
            }
            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper8 = desktopTilingWindowDecoration.leftTaskResizingHelper;
            if (appResizingHelper8 != null && (desktopModeWindowDecoration2 = appResizingHelper8.desktopModeWindowDecoration) != null) {
                desktopModeWindowDecoration2.updateDisabledResizingEdge(DragResizeWindowGeometry.DisabledEdge.RIGHT, false);
            }
            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper9 = desktopTilingWindowDecoration.rightTaskResizingHelper;
            if (appResizingHelper9 != null && (desktopModeWindowDecoration = appResizingHelper9.desktopModeWindowDecoration) != null) {
                desktopModeWindowDecoration.updateDisabledResizingEdge(DragResizeWindowGeometry.DisabledEdge.LEFT, false);
            }
        } else if (this.f$2) {
            desktopTilingWindowDecoration.shellTaskOrganizer.addTaskVanishedListener(desktopTilingWindowDecoration);
        }
        return Unit.INSTANCE;
    }
}
