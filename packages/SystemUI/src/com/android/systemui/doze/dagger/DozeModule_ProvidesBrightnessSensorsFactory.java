package com.android.systemui.doze.dagger;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.sensors.AsyncSensorManager;
import dagger.internal.Provider;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DozeModule_ProvidesBrightnessSensorsFactory implements Provider {
    public final javax.inject.Provider contextProvider;
    public final javax.inject.Provider dozeParametersProvider;
    public final javax.inject.Provider sensorManagerProvider;

    public DozeModule_ProvidesBrightnessSensorsFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.sensorManagerProvider = provider;
        this.contextProvider = provider2;
        this.dozeParametersProvider = provider3;
    }

    public static Optional[] providesBrightnessSensors(AsyncSensorManager asyncSensorManager, Context context, DozeParameters dozeParameters) {
        String[] stringArray = dozeParameters.mResources.getStringArray(R.array.doze_brightness_sensor_name_posture_mapping);
        if (stringArray.length == 0) {
            return new Optional[]{Optional.ofNullable(DozeSensors.findSensor(asyncSensorManager, context.getString(R.string.doze_brightness_sensor_type), null))};
        }
        Optional[] optionalArr = new Optional[5];
        Arrays.fill(optionalArr, Optional.empty());
        HashMap hashMap = new HashMap();
        for (int i = 0; i < stringArray.length; i++) {
            String str = stringArray[i];
            if (!hashMap.containsKey(str)) {
                hashMap.put(str, Optional.ofNullable(DozeSensors.findSensor(asyncSensorManager, context.getString(R.string.doze_brightness_sensor_type), stringArray[i])));
            }
            optionalArr[i] = (Optional) hashMap.get(str);
        }
        return optionalArr;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesBrightnessSensors((AsyncSensorManager) this.sensorManagerProvider.get(), (Context) this.contextProvider.get(), (DozeParameters) this.dozeParametersProvider.get());
    }
}
