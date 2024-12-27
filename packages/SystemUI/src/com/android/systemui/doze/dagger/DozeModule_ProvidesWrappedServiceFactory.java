package com.android.systemui.doze.dagger;

import android.os.SystemProperties;
import com.android.systemui.R;
import com.android.systemui.doze.DozeBrightnessHostForwarder;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeScreenStatePreventingAdapter;
import com.android.systemui.doze.DozeSuspendScreenStatePreventingAdapter;
import com.android.systemui.statusbar.phone.DozeParameters;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

public final class DozeModule_ProvidesWrappedServiceFactory implements Provider {
    public final javax.inject.Provider bgExecutorProvider;
    public final javax.inject.Provider dozeHostProvider;
    public final javax.inject.Provider dozeMachineServiceProvider;
    public final javax.inject.Provider dozeParametersProvider;

    public DozeModule_ProvidesWrappedServiceFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
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
