package com.android.systemui.shade;

import android.util.Log;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecTabletHorizontalPanelPositionHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final IntSupplier barStateIntSupplier;
    public float controllerCenter;
    public int currentOrientation;
    public final DoubleSupplier expandedFractionSupplier;
    public RepeatWhenAttachedKt$repeatWhenAttached$1 handle;
    public final Lazy headsUpManager$delegate;
    public float horizontalPanelTranslation;
    public final BooleanSupplier isFullyCollapsedSupplier;
    public final BooleanSupplier isFullyExpandedSupplier;
    public float leftMost;
    public final NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public float panelCenter;
    public final dagger.Lazy panelViewControllerLazy;
    public float posRatio;
    public float posResult;
    public final IntSupplier positionMinSideMarginSupplier;
    public float prevTransitionX;
    public final Supplier qsFrameLayoutSupplier;
    public final Lazy qsUiDisplayModeInteractor$delegate;
    public final Lazy resourcePicker$delegate;
    public boolean reversed;
    public float rightMost;
    public final Lazy settingsHelper$delegate;
    public boolean transitionSwitchOn;
    public boolean transitioning;
    public final Supplier viewSupplier;
    public final Lazy wakefulnessLifecycle$delegate;
    public final SplitStateInteractor splitStateInteractor = (SplitStateInteractor) Dependency.sDependency.getDependencyInner(SplitStateInteractor.class);
    public SecQsUiDisplayModeInteractor.FoldState foldState = SecQsUiDisplayModeInteractor.FoldState.UNSET;
    public int isQsSTATE = -1;

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

    public SecTabletHorizontalPanelPositionHelper(DoubleSupplier doubleSupplier, BooleanSupplier booleanSupplier, BooleanSupplier booleanSupplier2, NotificationStackScrollLayoutController notificationStackScrollLayoutController, IntSupplier intSupplier, Supplier<FrameLayout> supplier, Supplier<NotificationPanelView> supplier2, dagger.Lazy lazy, IntSupplier intSupplier2) {
        this.expandedFractionSupplier = doubleSupplier;
        this.isFullyCollapsedSupplier = booleanSupplier;
        this.isFullyExpandedSupplier = booleanSupplier2;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.positionMinSideMarginSupplier = intSupplier;
        this.qsFrameLayoutSupplier = supplier;
        this.viewSupplier = supplier2;
        this.panelViewControllerLazy = lazy;
        this.barStateIntSupplier = intSupplier2;
        final int i = 0;
        this.qsUiDisplayModeInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
                    case 1:
                        int i3 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 2:
                        int i4 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 3:
                        int i5 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class);
                    default:
                        int i6 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                }
            }
        });
        final int i2 = 1;
        this.resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
                    case 1:
                        int i3 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 2:
                        int i4 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 3:
                        int i5 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class);
                    default:
                        int i6 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                }
            }
        });
        final int i3 = 2;
        this.settingsHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        int i22 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
                    case 1:
                        int i32 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 2:
                        int i4 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 3:
                        int i5 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class);
                    default:
                        int i6 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                }
            }
        });
        final int i4 = 3;
        this.wakefulnessLifecycle$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        int i22 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
                    case 1:
                        int i32 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 2:
                        int i42 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 3:
                        int i5 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class);
                    default:
                        int i6 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                }
            }
        });
        final int i5 = 4;
        this.headsUpManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        int i22 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
                    case 1:
                        int i32 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 2:
                        int i42 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    case 3:
                        int i52 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class);
                    default:
                        int i6 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                }
            }
        });
        this.currentOrientation = supplier2.get().getResources().getConfiguration().orientation;
    }

    public final void resetHorizontalPanelPosition(boolean z) {
        if (!((SecQsUiDisplayModeInteractor) this.qsUiDisplayModeInteractor$delegate.getValue()).isTablet() || ((float) this.expandedFractionSupplier.getAsDouble()) <= 0.01f || z) {
            this.posResult = 0.0f;
            this.posRatio = 0.0f;
            setHorizontalPanelTranslation(0.0f, true);
            return;
        }
        float f = this.horizontalPanelTranslation;
        boolean asBoolean = this.isFullyCollapsedSupplier.getAsBoolean();
        boolean asBoolean2 = this.isFullyExpandedSupplier.getAsBoolean();
        float asDouble = (float) this.expandedFractionSupplier.getAsDouble();
        StringBuilder sb = new StringBuilder("skip resetHorizontalPanelPosition(");
        sb.append(f);
        sb.append("), isFullyCollapsed():");
        sb.append(asBoolean);
        sb.append(", isFullyExpanded():");
        sb.append(asBoolean2);
        sb.append(", getExpandedFraction():");
        sb.append(asDouble);
        sb.append(",forceUpdate:");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "SecTabletHorizontalPanelPositionHelper");
    }

    public final void setHorizontalPanelTranslation(float f, boolean z) {
        float f2;
        float f3;
        if (!z && this.isQsSTATE == 0) {
            if (this.reversed) {
                f2 = this.leftMost;
                f3 = this.panelCenter;
            } else {
                f2 = this.rightMost;
                f3 = this.panelCenter;
            }
            f = f2 - f3;
        }
        this.horizontalPanelTranslation = f;
        Log.d("SecTabletHorizontalPanelPositionHelper", "setHorizontalPanelTranslation(" + f + "), force : " + z);
        this.notificationStackScrollLayoutController.mView.setTranslationX(this.horizontalPanelTranslation);
        ((FrameLayout) this.qsFrameLayoutSupplier.get()).setTranslationX(this.horizontalPanelTranslation);
        ((NotificationPanelViewController) this.panelViewControllerLazy.get()).mNotificationContainerParent.requestLayout();
        ((SecQSPanelResourcePicker) this.resourcePicker$delegate.getValue()).resourcePickHelper.getTargetPicker().qsTransitionX = this.horizontalPanelTranslation;
        this.posRatio = this.horizontalPanelTranslation / (this.rightMost - this.panelCenter);
        if (this.transitionSwitchOn) {
            this.transitionSwitchOn = false;
        }
    }
}
