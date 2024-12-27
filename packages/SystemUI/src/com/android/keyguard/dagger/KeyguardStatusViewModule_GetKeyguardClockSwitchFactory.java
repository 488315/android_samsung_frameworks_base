package com.android.keyguard.dagger;

import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.KeyguardStatusView;
import com.android.systemui.R;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class KeyguardStatusViewModule_GetKeyguardClockSwitchFactory implements Provider {
    public final javax.inject.Provider keyguardPresentationProvider;

    public KeyguardStatusViewModule_GetKeyguardClockSwitchFactory(javax.inject.Provider provider) {
        this.keyguardPresentationProvider = provider;
    }

    public static KeyguardClockSwitch getKeyguardClockSwitch(KeyguardStatusView keyguardStatusView) {
        KeyguardClockSwitch keyguardClockSwitch = (KeyguardClockSwitch) keyguardStatusView.findViewById(R.id.keyguard_clock_container);
        Preconditions.checkNotNullFromProvides(keyguardClockSwitch);
        return keyguardClockSwitch;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return getKeyguardClockSwitch((KeyguardStatusView) this.keyguardPresentationProvider.get());
    }
}
