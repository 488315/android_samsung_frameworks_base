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
import com.android.systemui.qs.QSContainerImpl;
import com.android.systemui.qs.QSFragmentLegacy;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSImpl;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.buttons.QSButtonsContainerController;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.statusbar.events.BatteryChipAnimationUtils;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecQuickSettingsControllerImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int barState;
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
    public final SecPanelExpansionStateInteractor panelExpansionStateInteractor;
    public final Lazy panelSplitHelper$delegate;
    public final Lazy panelTouchBlockHelper$delegate;
    public final dagger.Lazy panelViewControllerLazy;
    public QSContainerImpl qsContainerImpl;
    public final BooleanSupplier qsExpandedSupplier;
    public final Lazy qsExpansionStateInteractor$delegate;
    public final Supplier qsFrameLayoutSupplier;
    public NonInterceptingScrollView qsScrollView;
    public final Supplier qsSupplier;
    public final QuickPanelLogger quickPanelLogger;
    public final Lazy resourcePicker$delegate;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    public final Lazy shadeRepository$delegate;
    public final Lazy tabletHorizontalPanelPositionHelper$delegate;
    public final SecQuickTileChunkLayoutBarTouchHelper tileChunkLayoutBarTouchHelper;
    public final Consumer touchAboveFalsingThresholdConsumer;

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

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.shade.SecQuickSettingsControllerImpl$logProvider$1] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.systemui.shade.SecQuickSettingsControllerImpl$modeChangedListener$1] */
    public SecQuickSettingsControllerImpl(final AmbientState ambientState, final Function<Float, Integer> function, final Function<Integer, Integer> function2, final DoubleSupplier doubleSupplier, final Context context, final DoubleSupplier doubleSupplier2, final BooleanSupplier booleanSupplier, final DoubleSupplier doubleSupplier3, final DoubleSupplier doubleSupplier4, ShadeHeaderController shadeHeaderController, final BooleanSupplier booleanSupplier2, final BooleanSupplier booleanSupplier3, DoubleSupplier doubleSupplier5, DoubleSupplier doubleSupplier6, NotificationStackScrollLayoutController notificationStackScrollLayoutController, dagger.Lazy lazy, BooleanSupplier booleanSupplier4, Supplier<FrameLayout> supplier, Supplier<QS> supplier2, final Supplier<NotificationPanelView> supplier3, Runnable runnable, DoubleSupplier doubleSupplier7, DoubleConsumer doubleConsumer, DoubleSupplier doubleSupplier8, DoubleConsumer doubleConsumer2, DoubleSupplier doubleSupplier9, Runnable runnable2, Consumer<MotionEvent> consumer, IntConsumer intConsumer, IntSupplier intSupplier, Consumer<Boolean> consumer2, SecQSPanelComposeAdapter secQSPanelComposeAdapter, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.maxExpansionHeightSupplier = doubleSupplier5;
        this.minExpansionHeightSupplier = doubleSupplier6;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.panelViewControllerLazy = lazy;
        this.qsExpandedSupplier = booleanSupplier4;
        this.qsFrameLayoutSupplier = supplier;
        this.qsSupplier = supplier2;
        this.touchAboveFalsingThresholdConsumer = consumer2;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        final int i = 0;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
                    case 1:
                        int i3 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return null;
                    case 2:
                        int i4 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                    case 3:
                        int i5 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class);
                    case 4:
                        int i6 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 5:
                        int i7 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                    case 6:
                        int i8 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
                    case 7:
                        int i9 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    default:
                        int i10 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        final int i2 = 1;
        this.desktopManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
                    case 1:
                        int i3 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return null;
                    case 2:
                        int i4 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                    case 3:
                        int i5 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class);
                    case 4:
                        int i6 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 5:
                        int i7 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                    case 6:
                        int i8 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
                    case 7:
                        int i9 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    default:
                        int i10 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
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
                View requireViewById = ((NotificationPanelViewController) secQuickSettingsControllerImpl.panelViewControllerLazy.get()).mView.requireViewById(R.id.notification_container_parent);
                arrayList.add(" notifsQsContainer[alpha: " + requireViewById.getAlpha() + " visibility: " + requireViewById.getVisibility() + "]");
                FrameLayout frameLayout = (FrameLayout) secQuickSettingsControllerImpl.qsFrameLayoutSupplier.get();
                arrayList.add(" enableClipping: " + booleanSupplier5.getAsBoolean() + " qsFrame[translationY: " + frameLayout.getTranslationY() + "  top: " + frameLayout.getTop() + " alpha: " + frameLayout.getAlpha() + " visibility: " + frameLayout.getVisibility() + "]");
                QSContainerImpl qSContainerImpl = secQuickSettingsControllerImpl.qsContainerImpl;
                if (qSContainerImpl != null) {
                    arrayList.add(" qsContainer[alpha: " + qSContainerImpl.getAlpha() + " visibility: " + qSContainerImpl.getVisibility() + "]");
                }
                NonInterceptingScrollView nonInterceptingScrollView = secQuickSettingsControllerImpl.qsScrollView;
                if (nonInterceptingScrollView != null) {
                    arrayList.add(" qsScrollView[alpha: " + nonInterceptingScrollView.getAlpha() + " visibility: " + nonInterceptingScrollView.getVisibility() + "]");
                }
                AmbientState ambientState2 = ambientState;
                float f = ambientState2.mExpansionFraction;
                float stackY = ambientState2.getStackY();
                int i3 = ambientState2.mStackTopMargin;
                int i4 = ambientState2.mScrollY;
                StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m(" ambientState[expansionFraction: ", f, " stackY: ", stackY, " stackTopMargin: ");
                m.append(i3);
                m.append(" scrollY: ");
                m.append(i4);
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
            public final void onNavigationModeChanged(int i3) {
                SecQuickSettingsControllerImpl.this.naviBarGestureMode = i3;
            }
        };
        final int i3 = 2;
        this.navigationBarController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        int i22 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
                    case 1:
                        int i32 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return null;
                    case 2:
                        int i4 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                    case 3:
                        int i5 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class);
                    case 4:
                        int i6 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 5:
                        int i7 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                    case 6:
                        int i8 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
                    case 7:
                        int i9 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    default:
                        int i10 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        final int i4 = 3;
        this.navigationModeController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        int i22 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
                    case 1:
                        int i32 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return null;
                    case 2:
                        int i42 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                    case 3:
                        int i5 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class);
                    case 4:
                        int i6 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 5:
                        int i7 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                    case 6:
                        int i8 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
                    case 7:
                        int i9 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    default:
                        int i10 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        this.panelExpansionStateInteractor = (SecPanelExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecPanelExpansionStateInteractor.class);
        final int i5 = 4;
        this.panelSplitHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        int i22 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
                    case 1:
                        int i32 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return null;
                    case 2:
                        int i42 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                    case 3:
                        int i52 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class);
                    case 4:
                        int i6 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 5:
                        int i7 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                    case 6:
                        int i8 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
                    case 7:
                        int i9 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    default:
                        int i10 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        final int i6 = 5;
        this.panelTouchBlockHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
                    case 0:
                        int i22 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
                    case 1:
                        int i32 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return null;
                    case 2:
                        int i42 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                    case 3:
                        int i52 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class);
                    case 4:
                        int i62 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 5:
                        int i7 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                    case 6:
                        int i8 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
                    case 7:
                        int i9 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    default:
                        int i10 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        final int i7 = 6;
        this.qsExpansionStateInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i7) {
                    case 0:
                        int i22 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
                    case 1:
                        int i32 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return null;
                    case 2:
                        int i42 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                    case 3:
                        int i52 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class);
                    case 4:
                        int i62 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 5:
                        int i72 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                    case 6:
                        int i8 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
                    case 7:
                        int i9 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    default:
                        int i10 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        final int i8 = 7;
        this.resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i8) {
                    case 0:
                        int i22 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
                    case 1:
                        int i32 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return null;
                    case 2:
                        int i42 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                    case 3:
                        int i52 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class);
                    case 4:
                        int i62 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 5:
                        int i72 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                    case 6:
                        int i82 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
                    case 7:
                        int i9 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    default:
                        int i10 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        this.quickPanelLogger = new QuickPanelLogger("SQSCI");
        final int i9 = 8;
        this.shadeRepository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i9) {
                    case 0:
                        int i22 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
                    case 1:
                        int i32 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return null;
                    case 2:
                        int i42 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                    case 3:
                        int i52 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class);
                    case 4:
                        int i62 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 5:
                        int i72 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                    case 6:
                        int i82 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecQSExpansionStateInteractor.class);
                    case 7:
                        int i92 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    default:
                        int i10 = SecQuickSettingsControllerImpl.$r8$clinit;
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                }
            }
        });
        this.tileChunkLayoutBarTouchHelper = new SecQuickTileChunkLayoutBarTouchHelper(context, runnable, doubleSupplier7, doubleConsumer, doubleSupplier8, doubleConsumer2, doubleSupplier9, runnable2, booleanSupplier4, supplier2, consumer, intConsumer, intSupplier);
        this.tabletHorizontalPanelPositionHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                DoubleSupplier doubleSupplier10 = doubleSupplier3;
                BooleanSupplier booleanSupplier5 = booleanSupplier2;
                BooleanSupplier booleanSupplier6 = booleanSupplier3;
                Supplier supplier4 = supplier3;
                final Context context2 = context;
                int i10 = SecQuickSettingsControllerImpl.$r8$clinit;
                final SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this;
                return new SecTabletHorizontalPanelPositionHelper(doubleSupplier10, booleanSupplier5, booleanSupplier6, secQuickSettingsControllerImpl.notificationStackScrollLayoutController, new IntSupplier() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$tabletHorizontalPanelPositionHelper$2$1
                    @Override // java.util.function.IntSupplier
                    public final int getAsInt() {
                        return context2.getResources().getDimensionPixelSize(R.dimen.notification_panel_min_side_margin);
                    }
                }, secQuickSettingsControllerImpl.qsFrameLayoutSupplier, supplier4, secQuickSettingsControllerImpl.panelViewControllerLazy, new IntSupplier() { // from class: com.android.systemui.shade.SecQuickSettingsControllerImpl$tabletHorizontalPanelPositionHelper$2$2
                    @Override // java.util.function.IntSupplier
                    public final int getAsInt() {
                        return SecQuickSettingsControllerImpl.this.barState;
                    }
                });
            }
        });
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

    public final SecTabletHorizontalPanelPositionHelper getTabletHorizontalPanelPositionHelper() {
        return (SecTabletHorizontalPanelPositionHelper) this.tabletHorizontalPanelPositionHelper$delegate.getValue();
    }

    public final boolean isInTouchQsArea(float f) {
        FrameLayout frameLayout = (FrameLayout) this.qsFrameLayoutSupplier.get();
        return f >= frameLayout.getX() && f <= frameLayout.getX() + ((float) frameLayout.getWidth());
    }

    public final void onTouch(MotionEvent motionEvent) {
        float f;
        SecTabletHorizontalPanelPositionHelper tabletHorizontalPanelPositionHelper = getTabletHorizontalPanelPositionHelper();
        float displayWidth = DeviceState.getDisplayWidth(((NotificationPanelView) tabletHorizontalPanelPositionHelper.viewSupplier.get()).getContext());
        float width = tabletHorizontalPanelPositionHelper.notificationStackScrollLayoutController.getWidth();
        float f2 = 2;
        tabletHorizontalPanelPositionHelper.panelCenter = displayWidth / f2;
        tabletHorizontalPanelPositionHelper.controllerCenter = width / f2;
        float asInt = tabletHorizontalPanelPositionHelper.positionMinSideMarginSupplier.getAsInt() + tabletHorizontalPanelPositionHelper.controllerCenter;
        tabletHorizontalPanelPositionHelper.leftMost = asInt;
        tabletHorizontalPanelPositionHelper.rightMost = displayWidth - asInt;
        int measuredHeight = ((ShadeHeaderController) ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().common.shadeHeaderController$delegate.getValue()).header.getMeasuredHeight();
        tabletHorizontalPanelPositionHelper.prevTransitionX = tabletHorizontalPanelPositionHelper.posResult;
        if (motionEvent.getY() > measuredHeight || motionEvent.getActionMasked() == 4 || ((((SettingsHelper) tabletHorizontalPanelPositionHelper.settingsHelper$delegate.getValue()).isNotificationAsCard() && tabletHorizontalPanelPositionHelper.barStateIntSupplier.getAsInt() == 1) || ((HeadsUpManagerImpl) ((HeadsUpManager) tabletHorizontalPanelPositionHelper.headsUpManager$delegate.getValue())).mHasPinnedNotification)) {
            f = 0.0f;
        } else {
            float f3 = tabletHorizontalPanelPositionHelper.rightMost;
            float f4 = tabletHorizontalPanelPositionHelper.leftMost;
            float x = motionEvent.getX();
            if (f4 < x) {
                f4 = x;
            }
            if (f3 > f4) {
                f3 = f4;
            }
            f = f3 - tabletHorizontalPanelPositionHelper.panelCenter;
        }
        tabletHorizontalPanelPositionHelper.posResult = f;
        if (tabletHorizontalPanelPositionHelper.barStateIntSupplier.getAsInt() == 1 || (tabletHorizontalPanelPositionHelper.barStateIntSupplier.getAsInt() == 0 && tabletHorizontalPanelPositionHelper.isFullyCollapsedSupplier.getAsBoolean())) {
            tabletHorizontalPanelPositionHelper.setHorizontalPanelTranslation(tabletHorizontalPanelPositionHelper.posResult, false);
        }
    }

    public final void updateScrollViewLocationDelta() {
        int i;
        NotificationPanelView notificationPanelView = ((NotificationPanelViewController) this.panelViewControllerLazy.get()).mView;
        BatteryChipAnimationUtils.Companion companion = BatteryChipAnimationUtils.Companion;
        notificationPanelView.getClass();
        companion.getClass();
        int[] iArr = new int[2];
        notificationPanelView.getLocationOnScreen(iArr);
        int i2 = 0;
        int i3 = iArr[0];
        NonInterceptingScrollView nonInterceptingScrollView = this.qsScrollView;
        if (nonInterceptingScrollView != null) {
            companion.getClass();
            int[] iArr2 = new int[2];
            nonInterceptingScrollView.getLocationOnScreen(iArr2);
            i = iArr2[0];
        } else {
            i = 0;
        }
        this.deltaX = i3 - i;
        companion.getClass();
        int[] iArr3 = new int[2];
        notificationPanelView.getLocationOnScreen(iArr3);
        int i4 = iArr3[1];
        NonInterceptingScrollView nonInterceptingScrollView2 = this.qsScrollView;
        if (nonInterceptingScrollView2 != null) {
            companion.getClass();
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
