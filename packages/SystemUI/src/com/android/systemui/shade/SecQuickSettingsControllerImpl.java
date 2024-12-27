package com.android.systemui.shade;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.NonInterceptingScrollView;
import com.android.systemui.qs.QSFragmentLegacy;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSImpl;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.buttons.QSButtonsContainerController;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.statusbar.events.BatteryChipAnimationUtils;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.DesktopManager;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SecQuickSettingsControllerImpl {
    public boolean canScrollDown;
    public boolean canScrollUp;
    public float deltaX;
    public float deltaY;
    public final Lazy desktopManager$delegate;
    public boolean isBackGestureAllowed;
    public int lastDisplayTopInset;
    public int lastNavigationBarBottomHeight;
    public final StringBuilder logBuilder;
    public final SecQuickSettingsControllerImpl$logProvider$1 logProvider;
    public final DoubleSupplier maxExpansionHeightSupplier;
    public final DoubleSupplier minExpansionHeightSupplier;
    public final SecQuickSettingsControllerImpl$modeChangedListener$1 modeChangedListener;
    public int naviBarGestureMode;
    public final Lazy navigationBarController$delegate;
    public final Lazy navigationModeController$delegate;
    public final NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public final Lazy panelExpansionStateInteractor$delegate;
    public final Lazy panelSplitHelper$delegate;
    public final Lazy panelTouchBlockHelper$delegate;
    public final dagger.Lazy panelViewControllerLazy;
    public final BooleanSupplier qsExpandedSupplier;
    public final Lazy qsExpansionStateInteractor$delegate;
    public final Supplier qsFrameLayoutSupplier;
    public NonInterceptingScrollView qsScrollView;
    public final Supplier qsSupplier;
    public final QuickPanelLogger quickPanelLogger;
    public final Lazy resourcePicker$delegate;
    public final Lazy shadeRepository$delegate;
    public final SecQuickTileChunkLayoutBarTouchHelper tileChunkLayoutBarTouchHelper;
    public final Consumer touchAboveFalsingThresholdConsumer;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SecQuickSettingsControllerImpl(final AmbientState ambientState, final Function<Float, Integer> function, final Function<Integer, Integer> function2, final DoubleSupplier doubleSupplier, Context context, final DoubleSupplier doubleSupplier2, final BooleanSupplier booleanSupplier, DoubleSupplier doubleSupplier3, final DoubleSupplier doubleSupplier4, ShadeHeaderController shadeHeaderController, BooleanSupplier booleanSupplier2, BooleanSupplier booleanSupplier3, DoubleSupplier doubleSupplier5, DoubleSupplier doubleSupplier6, NotificationStackScrollLayoutController notificationStackScrollLayoutController, dagger.Lazy lazy, BooleanSupplier booleanSupplier4, Supplier<FrameLayout> supplier, Supplier<QS> supplier2, Runnable runnable, DoubleSupplier doubleSupplier7, DoubleConsumer doubleConsumer, DoubleSupplier doubleSupplier8, DoubleConsumer doubleConsumer2, DoubleSupplier doubleSupplier9, Runnable runnable2, Consumer<MotionEvent> consumer, IntConsumer intConsumer, IntSupplier intSupplier, Consumer<Boolean> consumer2) {
        this.maxExpansionHeightSupplier = doubleSupplier5;
        this.minExpansionHeightSupplier = doubleSupplier6;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.panelViewControllerLazy = lazy;
        this.qsExpandedSupplier = booleanSupplier4;
        this.qsFrameLayoutSupplier = supplier;
        this.qsSupplier = supplier2;
        this.touchAboveFalsingThresholdConsumer = consumer2;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$blurController$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
            }
        });
        this.desktopManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$desktopManager$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class);
            }
        });
        this.logBuilder = new StringBuilder();
        this.logProvider = new PanelScreenShotLogger.LogProvider() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$logProvider$1
            @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
            public final ArrayList gatherState() {
                ArrayList arrayList = new ArrayList();
                DoubleSupplier doubleSupplier10 = doubleSupplier4;
                DoubleSupplier doubleSupplier11 = doubleSupplier;
                Function function3 = function;
                Function function4 = function2;
                DoubleSupplier doubleSupplier12 = doubleSupplier2;
                BooleanSupplier booleanSupplier5 = booleanSupplier;
                arrayList.add("SecQuickSettingsControllerImpl ============================================= ");
                double asDouble = doubleSupplier10.getAsDouble();
                SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this;
                arrayList.add(" expansionHeight: " + asDouble + " minExpansionHeight: " + secQuickSettingsControllerImpl.minExpansionHeightSupplier.getAsDouble() + " maxExpansionHeight: " + secQuickSettingsControllerImpl.maxExpansionHeightSupplier.getAsDouble());
                FrameLayout frameLayout = (FrameLayout) secQuickSettingsControllerImpl.qsFrameLayoutSupplier.get();
                arrayList.add(" enableClipping: " + booleanSupplier5.getAsBoolean() + " qsFrame[translationY: " + frameLayout.getTranslationY() + "  top: " + frameLayout.getTop() + "]");
                AmbientState ambientState2 = ambientState;
                float f = ambientState2.mExpansionFraction;
                float f2 = ambientState2.mStackY;
                int i = ambientState2.mStackTopMargin;
                int i2 = ambientState2.mScrollY;
                StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m(" ambientState[expansionFraction: ", f, " stackY: ", f2, " stackTopMargin: ");
                m.append(i);
                m.append(" scrollY: ");
                m.append(i2);
                m.append("]");
                arrayList.add(m.toString());
                float asDouble2 = (float) doubleSupplier11.getAsDouble();
                int intValue = ((Number) function3.apply(Float.valueOf(asDouble2))).intValue();
                int intValue2 = ((Number) function4.apply(Integer.valueOf(intValue))).intValue();
                arrayList.add(" notificationTop: " + doubleSupplier12.getAsDouble() + " expansionFraction: " + asDouble2 + " qsPanelBottomY: " + intValue + " top: " + intValue2);
                return arrayList;
            }
        };
        this.modeChangedListener = new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$modeChangedListener$1
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i) {
                SecQuickSettingsControllerImpl.this.naviBarGestureMode = i;
            }
        };
        this.navigationBarController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$navigationBarController$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
            }
        });
        this.navigationModeController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$navigationModeController$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class);
            }
        });
        this.panelExpansionStateInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$panelExpansionStateInteractor$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SecPanelExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecPanelExpansionStateInteractor.class);
            }
        });
        this.panelSplitHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$panelSplitHelper$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
            }
        });
        this.panelTouchBlockHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$panelTouchBlockHelper$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
            }
        });
        this.qsExpansionStateInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$qsExpansionStateInteractor$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
            }
        });
        this.resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$resourcePicker$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
            }
        });
        this.quickPanelLogger = new QuickPanelLogger("SQSCI");
        this.shadeRepository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$shadeRepository$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
            }
        });
        this.tileChunkLayoutBarTouchHelper = new SecQuickTileChunkLayoutBarTouchHelper(context, runnable, doubleSupplier7, doubleConsumer, doubleSupplier8, doubleConsumer2, doubleSupplier9, runnable2, booleanSupplier4, supplier2, consumer, intConsumer, intSupplier);
    }

    public final boolean checkIfScrollEnabled(float f, float f2) {
        if (Math.abs(f) <= f2) {
            return false;
        }
        return (((f > 0.0f ? 1 : (f == 0.0f ? 0 : -1)) < 0) && this.canScrollUp) || (((f > 0.0f ? 1 : (f == 0.0f ? 0 : -1)) > 0) && this.canScrollDown);
    }

    public final void closeQSTooltip() {
        QSImpl qSImpl;
        SecQSImpl secQSImpl;
        QSButtonsContainerController qSButtonsContainerController;
        Object obj = this.qsSupplier.get();
        QSFragmentLegacy qSFragmentLegacy = obj instanceof QSFragmentLegacy ? (QSFragmentLegacy) obj : null;
        if (qSFragmentLegacy == null || (qSImpl = qSFragmentLegacy.mQsImpl) == null || (secQSImpl = qSImpl.mSecQSImpl) == null || (qSButtonsContainerController = secQSImpl.qsButtonsContainerController) == null) {
            return;
        }
        qSButtonsContainerController.closeQSTooltip();
    }

    public final NonInterceptingScrollView getNonInterceptingScrollView() {
        View view;
        Object obj = this.qsSupplier.get();
        QSFragmentLegacy qSFragmentLegacy = obj instanceof QSFragmentLegacy ? (QSFragmentLegacy) obj : null;
        if (qSFragmentLegacy == null || (view = qSFragmentLegacy.getView()) == null) {
            return null;
        }
        return (NonInterceptingScrollView) view.findViewById(R.id.expanded_qs_scroll_view);
    }

    public final SecQSPanelController getQsPanelController() {
        QSImpl qSImpl;
        Object obj = this.qsSupplier.get();
        QSFragmentLegacy qSFragmentLegacy = obj instanceof QSFragmentLegacy ? (QSFragmentLegacy) obj : null;
        if (qSFragmentLegacy == null || (qSImpl = qSFragmentLegacy.mQsImpl) == null) {
            return null;
        }
        return qSImpl.mQSPanelController;
    }

    public final void updateScrollViewLocationDelta() {
        int i;
        NotificationPanelView notificationPanelView = ((NotificationPanelViewController) this.panelViewControllerLazy.get()).mView;
        BatteryChipAnimationUtils.Companion companion = BatteryChipAnimationUtils.Companion;
        Intrinsics.checkNotNull(notificationPanelView);
        companion.getClass();
        int[] iArr = new int[2];
        notificationPanelView.getLocationOnScreen(iArr);
        int i2 = 0;
        int i3 = iArr[0];
        NonInterceptingScrollView nonInterceptingScrollView = this.qsScrollView;
        if (nonInterceptingScrollView != null) {
            int[] iArr2 = new int[2];
            nonInterceptingScrollView.getLocationOnScreen(iArr2);
            i = iArr2[0];
        } else {
            i = 0;
        }
        this.deltaX = i3 - i;
        int[] iArr3 = new int[2];
        notificationPanelView.getLocationOnScreen(iArr3);
        int i4 = iArr3[1];
        NonInterceptingScrollView nonInterceptingScrollView2 = this.qsScrollView;
        if (nonInterceptingScrollView2 != null) {
            int[] iArr4 = new int[2];
            nonInterceptingScrollView2.getLocationOnScreen(iArr4);
            i2 = iArr4[1];
        }
        this.deltaY = i4 - i2;
    }

    public final void updateScrollableDirection(boolean z) {
        if (z) {
            this.canScrollUp = false;
            this.canScrollDown = false;
            return;
        }
        NonInterceptingScrollView nonInterceptingScrollView = this.qsScrollView;
        if (nonInterceptingScrollView != null) {
            this.canScrollUp = nonInterceptingScrollView.canScrollVertically(1);
            this.canScrollDown = nonInterceptingScrollView.canScrollVertically(-1);
        }
    }
}
