package com.android.server.accessibility.magnification;

import android.os.Binder;
import android.provider.DeviceConfig;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class MagnificationFeatureFlagBase {
    public abstract boolean getDefaultValue();

    public abstract String getFeatureName();

    public abstract String getNamespace();

    public boolean setFeatureFlagEnabled(final boolean z) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(getDefaultValue());
        try {
            Binder.withCleanCallingIdentity(new MagnificationFeatureFlagBase$$ExternalSyntheticLambda3(new Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationFeatureFlagBase$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MagnificationFeatureFlagBase magnificationFeatureFlagBase = MagnificationFeatureFlagBase.this;
                    atomicBoolean.set(DeviceConfig.setProperty(magnificationFeatureFlagBase.getNamespace(), magnificationFeatureFlagBase.getFeatureName(), Boolean.toString(z), false));
                }
            }));
        } catch (Throwable unused) {
            atomicBoolean.set(getDefaultValue());
        }
        return atomicBoolean.get();
    }
}
