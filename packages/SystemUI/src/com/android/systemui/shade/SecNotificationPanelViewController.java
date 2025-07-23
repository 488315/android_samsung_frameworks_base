package com.android.systemui.shade;

import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.domain.interactor.SecHideNotificationShadeInMirrorInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.domain.interactor.SecStatusBarWindowViewTouchedInteractor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecNotificationPanelViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Supplier expandFractionSupplier;
    public final Consumer expandedHeightInternalConsumer;
    public final SecHideNotificationShadeInMirrorInteractor hideNotificationShadeInMirrorInteractor;
    public final BooleanSupplier isTrackingSupplier;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public final IntSupplier maxPanelHeightSupplier;
    public final NotificationsQSContainerController notificationsQSContainerController;
    public final Runnable notifyExpandingFinishedRunnable;
    public final Consumer onTrackingStoppedConsumer;
    public final SecPanelSAStatusLogInteractor panelSAStatusLogInteractor;
    public final SecPanelSplitHelper panelSplitHelper;
    public final QuickSettingsControllerImpl quickSettingsController;
    public final Lazy resourcePicker$delegate;
    public final SecQuickSettingsControllerImpl secQuickSettingsControllerImpl;
    public final ShadeHeaderController shadeHeaderController;
    public final ShadeRepository shadeRepository;
    public final Lazy statusBarWindowViewTouchedInteractor$delegate;

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

    public SecNotificationPanelViewController(LockscreenShadeTransitionController lockscreenShadeTransitionController, NotificationsQSContainerController notificationsQSContainerController, QuickSettingsControllerImpl quickSettingsControllerImpl, ShadeHeaderController shadeHeaderController, ShadeRepository shadeRepository, Supplier<NotificationPanelView> supplier, BooleanSupplier booleanSupplier, Supplier<Float> supplier2, IntSupplier intSupplier, Consumer<Float> consumer, Consumer<Boolean> consumer2, Runnable runnable, SecHideNotificationShadeInMirrorInteractor secHideNotificationShadeInMirrorInteractor) {
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.notificationsQSContainerController = notificationsQSContainerController;
        this.quickSettingsController = quickSettingsControllerImpl;
        this.shadeHeaderController = shadeHeaderController;
        this.shadeRepository = shadeRepository;
        this.isTrackingSupplier = booleanSupplier;
        this.expandFractionSupplier = supplier2;
        this.maxPanelHeightSupplier = intSupplier;
        this.expandedHeightInternalConsumer = consumer;
        this.onTrackingStoppedConsumer = consumer2;
        this.notifyExpandingFinishedRunnable = runnable;
        this.hideNotificationShadeInMirrorInteractor = secHideNotificationShadeInMirrorInteractor;
        SecPanelSplitHelper secPanelSplitHelper = (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
        NotificationPanelView notificationPanelView = supplier.get();
        secPanelSplitHelper.panelRootView = notificationPanelView;
        secPanelSplitHelper.shadeRootView = notificationPanelView != null ? notificationPanelView.findViewById(R.id.notification_stack_scroller) : null;
        View view = secPanelSplitHelper.panelRootView;
        secPanelSplitHelper.qsFrame = view != null ? view.findViewById(R.id.qs_frame) : null;
        SecNotificationPanelViewController$panelSplitHelper$1$1 secNotificationPanelViewController$panelSplitHelper$1$1 = new SecNotificationPanelViewController$panelSplitHelper$1$1(this);
        secPanelSplitHelper.interceptCallback = secNotificationPanelViewController$panelSplitHelper$1$1;
        secPanelSplitHelper.panelSlideEventHandler.interceptCallback = secNotificationPanelViewController$panelSplitHelper$1$1;
        this.panelSplitHelper = secPanelSplitHelper;
        this.panelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
        final int i = 0;
        this.resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecNotificationPanelViewController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = SecNotificationPanelViewController.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    default:
                        int i3 = SecNotificationPanelViewController.$r8$clinit;
                        return (SecStatusBarWindowViewTouchedInteractor) Dependency.sDependency.getDependencyInner(SecStatusBarWindowViewTouchedInteractor.class);
                }
            }
        });
        this.secQuickSettingsControllerImpl = quickSettingsControllerImpl.mSecQuickSettingsControllerImpl;
        final int i2 = 1;
        this.statusBarWindowViewTouchedInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecNotificationPanelViewController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = SecNotificationPanelViewController.$r8$clinit;
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    default:
                        int i3 = SecNotificationPanelViewController.$r8$clinit;
                        return (SecStatusBarWindowViewTouchedInteractor) Dependency.sDependency.getDependencyInner(SecStatusBarWindowViewTouchedInteractor.class);
                }
            }
        });
    }

    public final boolean isStatusBarWindowViewTouched() {
        SecStatusBarWindowViewTouchedInteractor secStatusBarWindowViewTouchedInteractor = (SecStatusBarWindowViewTouchedInteractor) this.statusBarWindowViewTouchedInteractor$delegate.getValue();
        return secStatusBarWindowViewTouchedInteractor != null && secStatusBarWindowViewTouchedInteractor.isTouched();
    }

    public final void onPanelSplitIntercepted() {
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this.secQuickSettingsControllerImpl;
        if (secQuickSettingsControllerImpl != null) {
            secQuickSettingsControllerImpl.closeQSTooltip();
        }
        if (((Number) this.expandFractionSupplier.get()).floatValue() != 1.0f) {
            this.expandedHeightInternalConsumer.accept(Float.valueOf(this.maxPanelHeightSupplier.getAsInt()));
        } else if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
            this.notifyExpandingFinishedRunnable.run();
        }
        if (this.isTrackingSupplier.getAsBoolean()) {
            this.onTrackingStoppedConsumer.accept(Boolean.TRUE);
        }
    }
}
