package com.android.systemui.doze.dagger;

import android.os.SystemProperties;
import com.android.systemui.R;
import com.android.systemui.doze.DozeBrightnessHostForwarder;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeScreenStatePreventingAdapter;
import com.android.systemui.doze.DozeSuspendScreenStatePreventingAdapter;
import com.android.systemui.statusbar.phone.DozeParameters;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeModule_ProvidesWrappedServiceFactory implements Provider {
    public final Provider bgExecutorProvider;
    public final Provider dozeHostProvider;
    public final Provider dozeMachineServiceProvider;
    public final Provider dozeParametersProvider;

    public DozeModule_ProvidesWrappedServiceFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.dozeMachineServiceProvider = provider;
        this.dozeHostProvider = provider2;
        this.dozeParametersProvider = provider3;
        this.bgExecutorProvider = provider4;
    }

    public static DozeMachine.Service.Delegate providesWrappedService(DozeMachine.Service service, DozeHost dozeHost, DozeParameters dozeParameters, Executor executor) {
        DozeMachine.Service.Delegate dozeBrightnessHostForwarder = new DozeBrightnessHostForwarder(service, dozeHost, executor);
        if (!SystemProperties.getBoolean("doze.display.supported", dozeParameters.mResources.getBoolean(R.bool.doze_display_state_supported))) {
            dozeBrightnessHostForwarder = new DozeScreenStatePreventingAdapter(dozeBrightnessHostForwarder, executor);
        }
        return dozeParameters.mResources.getBoolean(R.bool.doze_suspend_display_state_supported) ^ true ? new DozeSuspendScreenStatePreventingAdapter(dozeBrightnessHostForwarder, executor) : dozeBrightnessHostForwarder;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesWrappedService((DozeMachine.Service) this.dozeMachineServiceProvider.get(), (DozeHost) this.dozeHostProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (Executor) this.bgExecutorProvider.get());
    }
}
