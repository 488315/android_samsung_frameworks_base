package com.android.systemui.dagger;

import android.content.Context;
import android.os.Vibrator;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideOptionalVibratorFactory implements Provider {
    public final Provider contextProvider;

    public FrameworkServicesModule_ProvideOptionalVibratorFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static Optional provideOptionalVibrator(Context context) {
        Optional ofNullable = Optional.ofNullable((Vibrator) context.getSystemService(Vibrator.class));
        Preconditions.checkNotNullFromProvides(ofNullable);
        return ofNullable;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideOptionalVibrator((Context) this.contextProvider.get());
    }
}
