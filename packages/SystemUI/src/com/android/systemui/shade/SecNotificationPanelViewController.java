package com.android.systemui.shade;

import android.animation.ValueAnimator;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.domain.interactor.SecHideNotificationShadeInMirrorInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SecNotificationPanelViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Supplier expandFractionSupplier;
    public final Consumer expandedHeightInternalConsumer;
    public final Supplier heightAnimatorSupplier;
    public final SecHideNotificationShadeInMirrorInteractor hideNotificationShadeInMirrorInteractor = (SecHideNotificationShadeInMirrorInteractor) Dependency.sDependency.getDependencyInner(SecHideNotificationShadeInMirrorInteractor.class);
    public final BooleanSupplier isTrackingSupplier;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public final IntSupplier maxPanelHeightSupplier;
    public final NotificationsQSContainerController notificationsQSContainerController;
    public final Consumer onTrackingStoppedConsumer;
    public final SecPanelSAStatusLogInteractor panelSAStatusLogInteractor;
    public final SecPanelSplitHelper panelSplitHelper;
    public final QuickSettingsControllerImpl quickSettingsController;
    public final Lazy resourcePicker$delegate;
    public final SecQuickSettingsControllerImpl secQuickSettingsControllerImpl;
    public final ShadeHeaderController shadeHeaderController;
    public final ShadeRepository shadeRepository;

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

    public SecNotificationPanelViewController(LockscreenShadeTransitionController lockscreenShadeTransitionController, NotificationsQSContainerController notificationsQSContainerController, QuickSettingsControllerImpl quickSettingsControllerImpl, ShadeHeaderController shadeHeaderController, ShadeRepository shadeRepository, Supplier<NotificationPanelView> supplier, Supplier<ValueAnimator> supplier2, BooleanSupplier booleanSupplier, Supplier<Float> supplier3, IntSupplier intSupplier, Consumer<Float> consumer, Consumer<Boolean> consumer2) {
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.notificationsQSContainerController = notificationsQSContainerController;
        this.quickSettingsController = quickSettingsControllerImpl;
        this.shadeHeaderController = shadeHeaderController;
        this.shadeRepository = shadeRepository;
        this.heightAnimatorSupplier = supplier2;
        this.isTrackingSupplier = booleanSupplier;
        this.expandFractionSupplier = supplier3;
        this.maxPanelHeightSupplier = intSupplier;
        this.expandedHeightInternalConsumer = consumer;
        this.onTrackingStoppedConsumer = consumer2;
        SecPanelSplitHelper secPanelSplitHelper = (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
        NotificationPanelView notificationPanelView = supplier.get();
        secPanelSplitHelper.panelRootView = notificationPanelView;
        secPanelSplitHelper.shadeRootView = notificationPanelView != null ? notificationPanelView.findViewById(R.id.notification_stack_scroller) : null;
        secPanelSplitHelper.panelSlideEventHandler.interceptCallback = new Runnable() { // from class: com.android.systemui.shade.SecNotificationPanelViewController$panelSplitHelper$1$1
            @Override // java.lang.Runnable
            public final void run() {
                SecNotificationPanelViewController secNotificationPanelViewController = SecNotificationPanelViewController.this;
                int i = SecNotificationPanelViewController.$r8$clinit;
                secNotificationPanelViewController.onPanelSplitIntercepted();
            }
        };
        this.panelSplitHelper = secPanelSplitHelper;
        this.panelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
        this.resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecNotificationPanelViewController$resourcePicker$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
            }
        });
        this.secQuickSettingsControllerImpl = quickSettingsControllerImpl.mSecQuickSettingsControllerImpl;
    }

    public final void onPanelSplitIntercepted() {
        SecQuickSettingsControllerImpl secQuickSettingsControllerImpl = this.secQuickSettingsControllerImpl;
        if (secQuickSettingsControllerImpl != null) {
            secQuickSettingsControllerImpl.closeQSTooltip();
        }
        if (((Number) this.expandFractionSupplier.get()).floatValue() != 1.0f) {
            this.expandedHeightInternalConsumer.accept(Float.valueOf(this.maxPanelHeightSupplier.getAsInt()));
        }
        if (this.isTrackingSupplier.getAsBoolean()) {
            this.onTrackingStoppedConsumer.accept(Boolean.TRUE);
        }
    }
}
