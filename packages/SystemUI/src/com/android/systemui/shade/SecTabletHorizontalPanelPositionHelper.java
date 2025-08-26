package com.android.systemui.shade;

import android.util.Log;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.DeviceState;
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

/* loaded from: classes3.dex */
public final class SecTabletHorizontalPanelPositionHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final IntSupplier barStateIntSupplier;
    public float controllerCenter;
    public int currentOrientation;
    public final DoubleSupplier expandedFractionSupplier;
    public RepeatWhenAttachedKt.C09181 handle;
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
    public float rightMost;
    public final Lazy secPanelSplitHelper$delegate;
    public final Lazy settingsHelper$delegate;
    public boolean transitionSwitchOn;
    public boolean transitioning;
    public final Supplier viewSupplier;
    public final Lazy wakefulnessLifecycle$delegate;
    public final SplitStateInteractor splitStateInteractor = (SplitStateInteractor) Dependency.sDependency.getDependencyInner(SplitStateInteractor.class);
    public SecQsUiDisplayModeInteractor.FoldState foldState = SecQsUiDisplayModeInteractor.FoldState.UNSET;
    public int isQsSTATE = -1;
    public final SecTabletHorizontalPanelPositionHelper$onHeadsUpChangedListener$1 onHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onHeadsUpChangedListener$1
        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public final void onHeadsUpPinnedModeChanged(boolean z) {
            Log.d("SecTabletHorizontalPanelPositionHelper", "onHeadsUpPinnedModeChanged(" + z + ")");
            if (z) {
                int i = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                this.this$0.resetHorizontalPanelPosition(true);
            }
        }
    };

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

    /* JADX WARN: Type inference failed for: r1v23, types: [com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$onHeadsUpChangedListener$1] */
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
                    case 4:
                        int i6 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        int i7 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
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
                    case 4:
                        int i6 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        int i7 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
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
                    case 4:
                        int i6 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        int i7 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
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
                    case 4:
                        int i6 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        int i7 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                }
            }
        });
        final int i5 = 4;
        this.secPanelSplitHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$$ExternalSyntheticLambda0
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
                    case 4:
                        int i6 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        int i7 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                }
            }
        });
        final int i6 = 5;
        this.headsUpManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
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
                    case 4:
                        int i62 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        int i7 = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
                        return (HeadsUpManager) Dependency.sDependency.getDependencyInner(HeadsUpManager.class);
                }
            }
        });
        this.currentOrientation = supplier2.get().getResources().getConfiguration().orientation;
    }

    public final void resetHorizontalPanelPosition(boolean z) {
        if (!((SecQsUiDisplayModeInteractor) this.qsUiDisplayModeInteractor$delegate.getValue()).isTablet() || ((float) this.expandedFractionSupplier.getAsDouble()) <= 0.0f || z) {
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
        Lazy lazy = this.settingsHelper$delegate;
        if (!z && this.isQsSTATE == 0) {
            boolean z2 = ((NotificationPanelView) this.viewSupplier.get()).getContext().getResources().getConfiguration().getLayoutDirection() == 1;
            boolean zIsPanelSplitReversed = ((SettingsHelper) lazy.getValue()).isPanelSplitReversed();
            SecPanelSplitHelper.Companion.getClass();
            if (!SecPanelSplitHelper.isEnabled || ((!z2 || zIsPanelSplitReversed) && (z2 || !zIsPanelSplitReversed))) {
                f2 = this.rightMost;
                f3 = this.panelCenter;
            } else {
                f2 = this.leftMost;
                f3 = this.panelCenter;
            }
            f = f2 - f3;
        }
        this.horizontalPanelTranslation = f;
        Log.d("SecTabletHorizontalPanelPositionHelper", "setHorizontalPanelTranslation(" + f + "), force : " + z);
        float f4 = this.horizontalPanelTranslation;
        NotificationStackScrollLayout notificationStackScrollLayout = this.notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mPreviousTranslationX = f4;
        notificationStackScrollLayout.setTranslationX(f4);
        ((FrameLayout) this.qsFrameLayoutSupplier.get()).setTranslationX(this.horizontalPanelTranslation);
        ((NotificationPanelViewController) this.panelViewControllerLazy.get()).mNotificationContainerParent.requestLayout();
        ((SecQSPanelResourcePicker) this.resourcePicker$delegate.getValue()).resourcePickHelper.getTargetPicker().qsTransitionX = this.horizontalPanelTranslation;
        this.posRatio = this.horizontalPanelTranslation / (this.rightMost - this.panelCenter);
        if (this.transitionSwitchOn) {
            this.transitionSwitchOn = false;
        }
        if (((SecQsUiDisplayModeInteractor) this.qsUiDisplayModeInteractor$delegate.getValue()).isTablet()) {
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled && ((SettingsHelper) lazy.getValue()).isRemoveAnimation()) {
                ((SecPanelSplitHelper) this.secPanelSplitHelper$delegate.getValue()).updateTransitionVisibility(this.isQsSTATE);
            }
        }
    }

    public final void updateResources() {
        float displayWidth = DeviceState.getDisplayWidth(((NotificationPanelView) this.viewSupplier.get()).getContext());
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.notificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getClass();
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        float width = notificationStackScrollLayoutController.mView.getWidth();
        int popOverMargin = ((SecQSPanelResourcePicker) this.resourcePicker$delegate.getValue()).getPopOverMargin(((NotificationPanelView) this.viewSupplier.get()).getContext());
        float f = 2;
        this.panelCenter = displayWidth / f;
        this.controllerCenter = width / f;
        float asInt = this.positionMinSideMarginSupplier.getAsInt() + this.controllerCenter + popOverMargin;
        this.leftMost = asInt;
        this.rightMost = displayWidth - asInt;
    }
}
