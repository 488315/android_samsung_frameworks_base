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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DozeModule_ProvidesWrappedServiceFactory implements Provider {
    public final Provider dozeHostProvider;
    public final Provider dozeMachineServiceProvider;
    public final Provider dozeParametersProvider;

    public DozeModule_ProvidesWrappedServiceFactory(Provider provider, Provider provider2, Provider provider3) {
        this.dozeMachineServiceProvider = provider;
        this.dozeHostProvider = provider2;
        this.dozeParametersProvider = provider3;
    }

    public static DozeMachine.Service.Delegate providesWrappedService(DozeMachine.Service service, DozeHost dozeHost, DozeParameters dozeParameters) {
        DozeMachine.Service.Delegate dozeBrightnessHostForwarder = new DozeBrightnessHostForwarder(service, dozeHost);
        if (!SystemProperties.getBoolean("doze.display.supported", dozeParameters.mResources.getBoolean(R.bool.doze_display_state_supported))) {
            dozeBrightnessHostForwarder = new DozeScreenStatePreventingAdapter(dozeBrightnessHostForwarder);
        }
        return !dozeParameters.mResources.getBoolean(R.bool.doze_suspend_display_state_supported) ? new DozeSuspendScreenStatePreventingAdapter(dozeBrightnessHostForwarder) : dozeBrightnessHostForwarder;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesWrappedService((DozeMachine.Service) this.dozeMachineServiceProvider.get(), (DozeHost) this.dozeHostProvider.get(), (DozeParameters) this.dozeParametersProvider.get());
    }
}
