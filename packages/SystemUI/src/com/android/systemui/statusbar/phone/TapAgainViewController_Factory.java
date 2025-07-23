package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TapAgainViewController_Factory implements Provider {
    public final Provider configurationControllerProvider;
    public final Provider delayableExecutorProvider;
    public final Provider doubleTapTimeMsProvider;
    public final Provider viewProvider;

    public TapAgainViewController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.viewProvider = provider;
        this.delayableExecutorProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.doubleTapTimeMsProvider = provider4;
    }

    public static TapAgainViewController newInstance(TapAgainView tapAgainView, DelayableExecutor delayableExecutor, ConfigurationController configurationController) {
        return new TapAgainViewController(tapAgainView, delayableExecutor, configurationController, 1200L);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TapAgainViewController((TapAgainView) this.viewProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), ((Long) this.doubleTapTimeMsProvider.get()).longValue());
    }
}
