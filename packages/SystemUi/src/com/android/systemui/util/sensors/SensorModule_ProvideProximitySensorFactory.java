package com.android.systemui.util.sensors;

import android.content.res.Resources;
import android.text.TextUtils;
import com.android.systemui.R;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SensorModule_ProvideProximitySensorFactory implements Provider {
    public final Provider postureDependentProximitySensorProvider;
    public final Provider proximitySensorProvider;
    public final Provider resourcesProvider;

    public SensorModule_ProvideProximitySensorFactory(Provider provider, Provider provider2, Provider provider3) {
        this.resourcesProvider = provider;
        this.postureDependentProximitySensorProvider = provider2;
        this.proximitySensorProvider = provider3;
    }

    public static ProximitySensor provideProximitySensor(Resources resources, Lazy lazy, Lazy lazy2) {
        String[] stringArray = resources.getStringArray(R.array.proximity_sensor_posture_mapping);
        boolean z = false;
        if (stringArray != null && stringArray.length != 0) {
            int length = stringArray.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (!TextUtils.isEmpty(stringArray[i])) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        ProximitySensor proximitySensor = z ? (ProximitySensor) lazy.get() : (ProximitySensor) lazy2.get();
        Preconditions.checkNotNullFromProvides(proximitySensor);
        return proximitySensor;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideProximitySensor((Resources) this.resourcesProvider.get(), DoubleCheck.lazy(this.postureDependentProximitySensorProvider), DoubleCheck.lazy(this.proximitySensorProvider));
    }
}
