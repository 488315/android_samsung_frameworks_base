package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.AlwaysOnDisplayNotificationIconViewStore;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.StatusBarIconViewBindingFailureTracker;
import com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerAlwaysOnDisplayViewModel;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.statusbar.ui.SystemBarUtilsState;
import com.android.systemui.util.ui.AnimatedValue;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AodNotificationIconsSection extends KeyguardSection {
    public final Context context;
    public NotificationIconContainer nic;
    public final int nicId = R.id.aod_notification_icon_container;
    public final NotificationIconAreaController notificationIconAreaController;
    public final KeyguardRootViewModel rootViewModel;
    public final ShadeModeInteractor shadeModeInteractor;

    public AodNotificationIconsSection(Context context, ConfigurationState configurationState, StatusBarIconViewBindingFailureTracker statusBarIconViewBindingFailureTracker, NotificationIconContainerAlwaysOnDisplayViewModel notificationIconContainerAlwaysOnDisplayViewModel, AlwaysOnDisplayNotificationIconViewStore alwaysOnDisplayNotificationIconViewStore, NotificationIconAreaController notificationIconAreaController, SystemBarUtilsState systemBarUtilsState, KeyguardRootViewModel keyguardRootViewModel, ShadeModeInteractor shadeModeInteractor) {
        this.context = context;
        this.notificationIconAreaController = notificationIconAreaController;
        this.rootViewModel = keyguardRootViewModel;
        this.shadeModeInteractor = shadeModeInteractor;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        NotificationIconContainer notificationIconContainer = new NotificationIconContainer(this.context, null);
        notificationIconContainer.setId(this.nicId);
        notificationIconContainer.setPaddingRelative(notificationIconContainer.getResources().getDimensionPixelSize(R.dimen.below_clock_padding_start_icons), 0, 0, 0);
        notificationIconContainer.setVisibility(4);
        this.nic = notificationIconContainer;
        constraintLayout.addView(notificationIconContainer);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        Object value;
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.keyguard_status_view_bottom_margin);
        int dimensionPixelSize2 = this.context.getResources().getDimensionPixelSize(R$dimen.status_view_margin_horizontal);
        int dimensionPixelSize3 = this.context.getResources().getDimensionPixelSize(R.dimen.notification_shelf_height);
        AnimatedValue animatedValue = (AnimatedValue) this.rootViewModel.isNotifIconContainerVisible.getValue();
        ((Boolean) ((ShadeModeInteractorImpl) this.shadeModeInteractor).isShadeLayoutWide.$$delegate_0.getValue()).getClass();
        constraintSet.connect(this.nicId, 3, R.id.smart_space_barrier_bottom, 4, dimensionPixelSize);
        int i = this.nicId;
        constraintSet.setGoneMargin(i, 4, dimensionPixelSize);
        if (animatedValue instanceof AnimatedValue.Animating) {
            value = ((AnimatedValue.Animating) animatedValue).getValue();
        } else {
            if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                throw new NoWhenBranchMatchedException();
            }
            value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
        }
        constraintSet.setVisibility(i, ((Boolean) value).booleanValue() ? 0 : 8);
        constraintSet.connect(this.nicId, 6, 0, 6, dimensionPixelSize2);
        constraintSet.connect(this.nicId, 7, 0, 7, dimensionPixelSize2);
        constraintSet.constrainHeight(i, dimensionPixelSize3);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        this.notificationIconAreaController.setupAodIcons();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, this.nicId);
    }
}
