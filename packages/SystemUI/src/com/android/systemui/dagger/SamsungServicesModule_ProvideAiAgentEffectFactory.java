package com.android.systemui.dagger;

import android.app.IActivityManager;
import android.content.Context;
import android.os.PowerManager;
import com.android.systemui.aiagent.AiAgentEffect;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.privacy.PrivacyItemController;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SamsungServicesModule_ProvideAiAgentEffectFactory implements Provider {
    public final Provider contextProvider;
    public final Provider iActivityManagerProvider;
    public final Provider powerInteractorProvider;
    public final Provider powerManagerProvider;
    public final Provider privacyItemControllerProvider;
    public final Provider statusBarStateControllerProvider;

    public SamsungServicesModule_ProvideAiAgentEffectFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.privacyItemControllerProvider = provider2;
        this.powerManagerProvider = provider3;
        this.statusBarStateControllerProvider = provider4;
        this.powerInteractorProvider = provider5;
        this.iActivityManagerProvider = provider6;
    }

    public static AiAgentEffect provideAiAgentEffect(Context context, PrivacyItemController privacyItemController, PowerManager powerManager, StatusBarStateController statusBarStateController, PowerInteractor powerInteractor, IActivityManager iActivityManager) {
        return new AiAgentEffect(context, privacyItemController, powerManager, statusBarStateController, powerInteractor, iActivityManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AiAgentEffect((Context) this.contextProvider.get(), (PrivacyItemController) this.privacyItemControllerProvider.get(), (PowerManager) this.powerManagerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (PowerInteractor) this.powerInteractorProvider.get(), (IActivityManager) this.iActivityManagerProvider.get());
    }
}
