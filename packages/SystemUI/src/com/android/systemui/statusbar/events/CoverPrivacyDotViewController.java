package com.android.systemui.statusbar.events;

import android.content.Context;
import android.graphics.Rect;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Iterator;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineScope;

public final class CoverPrivacyDotViewController extends PrivacyDotViewController {
    public int cutoutHeight;
    public int dotContainerHeight;
    public int dotContainerWidth;
    public final Executor mainExecutor;

    public CoverPrivacyDotViewController(Executor executor, CoroutineScope coroutineScope, StatusBarStateController statusBarStateController, ConfigurationController configurationController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, SystemStatusAnimationScheduler systemStatusAnimationScheduler, ShadeInteractor shadeInteractor, IndicatorScaleGardener indicatorScaleGardener, PrivacyLogger privacyLogger, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor) {
        super(executor, coroutineScope, statusBarStateController, configurationController, statusBarContentInsetsProvider, systemStatusAnimationScheduler, shadeInteractor, indicatorScaleGardener, privacyLogger, secPanelExpansionStateInteractor);
        this.mainExecutor = executor;
    }

    public static void setMargin(View view, int i, int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (i == 0) {
            marginLayoutParams.bottomMargin = i2;
            return;
        }
        if (i == 1) {
            marginLayoutParams.leftMargin = i2;
        } else if (i == 2) {
            marginLayoutParams.topMargin = i2;
        } else {
            if (i != 3) {
                return;
            }
            marginLayoutParams.rightMargin = i2;
        }
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewController
    public final void initialize(View view, View view2, View view3, View view4) {
        Rect rect;
        DisplayCutout cutout;
        Context context = view.getContext();
        this.dotContainerWidth = context.getResources().getDimensionPixelSize(R.dimen.cover_privacy_dot_container_width);
        this.dotContainerHeight = context.getResources().getDimensionPixelSize(R.dimen.cover_privacy_dot_container_height);
        Display display = context.getDisplay();
        if (display == null || (cutout = display.getCutout()) == null || (rect = cutout.getBoundingRectBottom()) == null) {
            rect = new Rect();
        }
        this.cutoutHeight = rect.height();
        super.initialize(view, view2, view3, view4);
        super.updateRotations(0, 0);
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewController
    public final int innerGravity(int i) {
        if (i == 0) {
            return 85;
        }
        if (i == 1) {
            return 83;
        }
        if (i == 2) {
            return 51;
        }
        if (i == 3) {
            return 53;
        }
        throw new IllegalArgumentException("Not a corner");
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewController
    public final boolean needsLayout(ViewState viewState, ViewState viewState2) {
        return (viewState.rotation == viewState2.rotation && viewState.viewInitialized == viewState2.viewInitialized) ? false : true;
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewController
    public final void setCornerSizes(ViewState viewState) {
        int i = viewState.rotation;
        if (i == 0 || i == 2) {
            Iterator it = getViews().iterator();
            while (it.hasNext()) {
                ((View) it.next()).getLayoutParams().width = this.dotContainerWidth;
            }
            Iterator it2 = getViews().iterator();
            while (it2.hasNext()) {
                ((View) it2.next()).getLayoutParams().height = this.dotContainerHeight;
            }
        } else {
            Iterator it3 = getViews().iterator();
            while (it3.hasNext()) {
                ((View) it3.next()).getLayoutParams().width = this.dotContainerHeight;
            }
            Iterator it4 = getViews().iterator();
            while (it4.hasNext()) {
                ((View) it4.next()).getLayoutParams().height = this.dotContainerWidth;
            }
        }
        View view = this.bl;
        if (view == null) {
            view = null;
        }
        setMargin(view, i, this.cutoutHeight);
        View view2 = this.br;
        setMargin(view2 != null ? view2 : null, i, this.cutoutHeight);
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewController
    public final boolean shouldShowDot(ViewState viewState) {
        return viewState.systemPrivacyEventIsActive && !viewState.isDotBlocked;
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewController
    public final void updateRotations(int i, int i2) {
        super.updateRotations(i, 0);
    }
}
