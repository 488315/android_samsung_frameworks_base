package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TapAgainViewController extends ViewController {
    public final ConfigurationController mConfigurationController;
    final ConfigurationController.ConfigurationListener mConfigurationListener;
    public final DelayableExecutor mDelayableExecutor;
    public final long mDoubleTapTimeMs;
    public Runnable mHideCanceler;

    /* renamed from: $r8$lambda$RI85HBul7dnFWQwIiM-PGhllBiQ, reason: not valid java name */
    public static void m2235$r8$lambda$RI85HBul7dnFWQwIiMPGhllBiQ(TapAgainViewController tapAgainViewController) {
        tapAgainViewController.mHideCanceler = null;
        ((TapAgainView) tapAgainViewController.mView).animateOut();
    }

    public TapAgainViewController(TapAgainView tapAgainView, DelayableExecutor delayableExecutor, ConfigurationController configurationController, long j) {
        super(tapAgainView);
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.TapAgainViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                ((TapAgainView) ((ViewController) TapAgainViewController.this).mView).updateColor();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                ((TapAgainView) ((ViewController) TapAgainViewController.this).mView).updateColor();
            }
        };
        this.mDelayableExecutor = delayableExecutor;
        this.mConfigurationController = configurationController;
        this.mDoubleTapTimeMs = j;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    public final void show() {
        Runnable runnable = this.mHideCanceler;
        if (runnable != null) {
            runnable.run();
        }
        ((TapAgainView) this.mView).animateIn();
        this.mHideCanceler = this.mDelayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.TapAgainViewController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TapAgainViewController.m2235$r8$lambda$RI85HBul7dnFWQwIiMPGhllBiQ(TapAgainViewController.this);
            }
        }, this.mDoubleTapTimeMs);
    }
}
