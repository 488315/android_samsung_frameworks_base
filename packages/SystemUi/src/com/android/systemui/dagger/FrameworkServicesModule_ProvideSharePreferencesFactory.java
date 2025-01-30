package com.android.systemui.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideSharePreferencesFactory implements Provider {
    public final Provider contextProvider;
    public final FrameworkServicesModule module;

    public FrameworkServicesModule_ProvideSharePreferencesFactory(FrameworkServicesModule frameworkServicesModule, Provider provider) {
        this.module = frameworkServicesModule;
        this.contextProvider = provider;
    }

    public static SharedPreferences provideSharePreferences(FrameworkServicesModule frameworkServicesModule, Context context) {
        frameworkServicesModule.getClass();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        Preconditions.checkNotNullFromProvides(sharedPreferences);
        return sharedPreferences;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSharePreferences(this.module, (Context) this.contextProvider.get());
    }
}
