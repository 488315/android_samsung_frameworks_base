package com.android.systemui.qs;

import android.content.res.Configuration;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;

public final class SecQSGradationDrawableController extends ViewController implements ShadeExpansionListener, StatusBarStateController.StateListener {
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public int statusBarState;
    public final StatusBarStateController statusBarStateController;
    public final SecQSGradationDrawableView view;

    public SecQSGradationDrawableController(SecQSGradationDrawableView secQSGradationDrawableView, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager) {
        super(secQSGradationDrawableView);
        this.view = secQSGradationDrawableView;
        this.statusBarStateController = statusBarStateController;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.statusBarState = 1;
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        int i = this.statusBarState;
        SecQSGradationDrawableView secQSGradationDrawableView = this.view;
        if (i == 1) {
            secQSGradationDrawableView.setAlpha(0.0f);
        } else {
            secQSGradationDrawableView.setAlpha(shadeExpansionChangeEvent.fraction);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        if (this.statusBarState == i) {
            return;
        }
        this.statusBarState = i;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.shadeExpansionStateManager.addExpansionListener(this);
        this.statusBarStateController.addCallback(this);
        ConfigurationController.ConfigurationListener configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.qs.SecQSGradationDrawableController$onViewAttached$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                SecQSGradationDrawableView secQSGradationDrawableView = SecQSGradationDrawableController.this.view;
                secQSGradationDrawableView.getLayoutParams().height = secQSGradationDrawableView.getContext().getResources().getDimensionPixelSize(R.dimen.qs_gradation_height);
            }
        };
        SecQSGradationDrawableView secQSGradationDrawableView = this.view;
        secQSGradationDrawableView.configChangedCallback = configurationListener;
        secQSGradationDrawableView.getLayoutParams().height = secQSGradationDrawableView.getContext().getResources().getDimensionPixelSize(R.dimen.qs_gradation_height);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.shadeExpansionStateManager.removeExpansionListener(this);
        this.statusBarStateController.removeCallback(this);
    }
}
