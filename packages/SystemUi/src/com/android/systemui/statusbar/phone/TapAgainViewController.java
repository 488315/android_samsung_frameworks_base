package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TapAgainViewController extends ViewController {
    public final ConfigurationController mConfigurationController;
    final ConfigurationController.ConfigurationListener mConfigurationListener;
    public final DelayableExecutor mDelayableExecutor;
    public final long mDoubleTapTimeMs;
    public ExecutorImpl.ExecutionToken mHideCanceler;

    public TapAgainViewController(TapAgainView tapAgainView, DelayableExecutor delayableExecutor, ConfigurationController configurationController, long j) {
        super(tapAgainView);
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.TapAgainViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                ((TapAgainView) TapAgainViewController.this.mView).updateColor();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                ((TapAgainView) TapAgainViewController.this.mView).updateColor();
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
}
