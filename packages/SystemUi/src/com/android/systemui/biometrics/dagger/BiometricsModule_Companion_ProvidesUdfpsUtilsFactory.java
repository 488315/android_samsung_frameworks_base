package com.android.systemui.biometrics.dagger;

import com.android.settingslib.udfps.UdfpsUtils;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BiometricsModule_Companion_ProvidesUdfpsUtilsFactory implements Provider {
    public static UdfpsUtils providesUdfpsUtils() {
        BiometricsModule.Companion.getClass();
        return new UdfpsUtils();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesUdfpsUtils();
    }
}
