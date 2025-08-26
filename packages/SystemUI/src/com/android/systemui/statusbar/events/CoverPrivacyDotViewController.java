package com.android.systemui.statusbar.events;

import android.content.Context;
import android.graphics.Rect;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$$ExternalSyntheticOutline0;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class CoverPrivacyDotViewController extends PrivacyDotViewControllerImpl {
    public final Executor mainExecutor;

    public CoverPrivacyDotViewController(Executor executor, CoroutineScope coroutineScope, StatusBarStateController statusBarStateController, ConfigurationController configurationController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, SystemStatusAnimationScheduler systemStatusAnimationScheduler, ShadeInteractor shadeInteractor, DelayableExecutor delayableExecutor, IndicatorScaleGardener indicatorScaleGardener, PrivacyLogger privacyLogger, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor, WakefulnessLifecycle wakefulnessLifecycle) {
        super(executor, coroutineScope, statusBarStateController, configurationController, statusBarContentInsetsProvider, systemStatusAnimationScheduler, shadeInteractor, delayableExecutor, indicatorScaleGardener, privacyLogger, secPanelExpansionStateInteractor, wakefulnessLifecycle);
        this.mainExecutor = executor;
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl, com.android.systemui.statusbar.events.PrivacyDotViewController
    public final void initialize(View view, View view2, View view3, View view4) {
        super.initialize(view, view2, view3, view4);
        updateRotations(0, 0);
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl
    public final boolean needsLayout(ViewState viewState, ViewState viewState2) {
        return (viewState.rotation == viewState2.rotation && viewState.viewInitialized == viewState2.viewInitialized) ? false : true;
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl
    public final void setCornerSizes(ViewState viewState) {
        Rect rect;
        Rect rect2;
        Rect rect3;
        Rect rect4;
        View view = viewState.designatedCorner;
        if (view != null) {
            int i = viewState.rotation;
            if (i == 0 || i == 2) {
                view.getLayoutParams().width = QSLayoutEditViewController$$ExternalSyntheticOutline0.m(view, R.dimen.cover_privacy_dot_container_width);
                view.getLayoutParams().height = QSLayoutEditViewController$$ExternalSyntheticOutline0.m(view, R.dimen.samsung_status_bar_battery_icon_height);
            } else {
                view.getLayoutParams().width = QSLayoutEditViewController$$ExternalSyntheticOutline0.m(view, R.dimen.samsung_status_bar_battery_icon_height);
                view.getLayoutParams().height = QSLayoutEditViewController$$ExternalSyntheticOutline0.m(view, R.dimen.cover_privacy_dot_container_width);
            }
            Context context = view.getContext();
            int dimensionPixelSize = i != 0 ? i != 2 ? 0 : context.getResources().getDimensionPixelSize(R.dimen.cover_battery_icon_opposite_margin) : context.getResources().getDimensionPixelSize(R.dimen.cover_battery_icon_default_margin);
            DisplayCutout cutout = view.getContext().getDisplay().getCutout();
            if (cutout == null || (rect = cutout.getBoundingRectLeft()) == null) {
                rect = new Rect();
            }
            int iWidth = rect.width();
            DisplayCutout cutout2 = view.getContext().getDisplay().getCutout();
            if (cutout2 == null || (rect2 = cutout2.getBoundingRectTop()) == null) {
                rect2 = new Rect();
            }
            int iHeight = (rect2.height() + dimensionPixelSize) - 1;
            DisplayCutout cutout3 = view.getContext().getDisplay().getCutout();
            if (cutout3 == null || (rect3 = cutout3.getBoundingRectRight()) == null) {
                rect3 = new Rect();
            }
            int iWidth2 = rect3.width();
            DisplayCutout cutout4 = view.getContext().getDisplay().getCutout();
            if (cutout4 == null || (rect4 = cutout4.getBoundingRectBottom()) == null) {
                rect4 = new Rect();
            }
            ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(iWidth, iHeight, iWidth2, rect4.height());
        }
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl
    public final boolean shouldShowDot(ViewState viewState) {
        return viewState.systemPrivacyEventIsActive && !viewState.isDotBlocked;
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl
    public final void updateRotations(int i, int i2) {
        int i3;
        for (View view : getViews()) {
            view.setPadding(0, 0, 0, 0);
            PrivacyDotCorner privacyDotCornerRotatedCorner = PrivacyDotCornerKt.rotatedCorner(cornerForView(view), i);
            ((FrameLayout.LayoutParams) view.getLayoutParams()).gravity = privacyDotCornerRotatedCorner.getGravity();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.requireViewById(R.id.privacy_dot).getLayoutParams();
            int index = privacyDotCornerRotatedCorner.getIndex();
            if (index == 0) {
                i3 = 85;
            } else if (index == 1) {
                i3 = 83;
            } else if (index == 2) {
                i3 = 51;
            } else {
                if (index != 3) {
                    throw new IllegalArgumentException("Not a corner");
                }
                i3 = 53;
            }
            layoutParams.gravity = i3;
        }
    }
}
