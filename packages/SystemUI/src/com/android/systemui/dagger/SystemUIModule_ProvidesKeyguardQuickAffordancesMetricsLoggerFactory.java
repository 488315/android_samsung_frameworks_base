package com.android.systemui.dagger;

import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancesMetricsLoggerImpl;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class SystemUIModule_ProvidesKeyguardQuickAffordancesMetricsLoggerFactory implements Provider {
    public static KeyguardQuickAffordancesMetricsLoggerImpl providesKeyguardQuickAffordancesMetricsLogger() {
        return new KeyguardQuickAffordancesMetricsLoggerImpl();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new KeyguardQuickAffordancesMetricsLoggerImpl();
    }
}
