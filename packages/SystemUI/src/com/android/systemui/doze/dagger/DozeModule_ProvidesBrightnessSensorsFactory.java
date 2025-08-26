package com.android.systemui.doze.dagger;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.sensors.AsyncSensorManager;
import dagger.internal.Provider;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

/* loaded from: classes2.dex */
public final class DozeModule_ProvidesBrightnessSensorsFactory implements Provider {
    public final Provider contextProvider;
    public final Provider dozeParametersProvider;
    public final Provider sensorManagerProvider;

    public DozeModule_ProvidesBrightnessSensorsFactory(Provider provider, Provider provider2, Provider provider3) {
        this.sensorManagerProvider = provider;
        this.contextProvider = provider2;
        this.dozeParametersProvider = provider3;
    }

    public static Optional[] providesBrightnessSensors(AsyncSensorManager asyncSensorManager, Context context, DozeParameters dozeParameters) throws Resources.NotFoundException {
        String[] stringArray = dozeParameters.mResources.getStringArray(R.array.doze_brightness_sensor_name_posture_mapping);
        if (stringArray.length == 0) {
            return new Optional[]{Optional.ofNullable(DozeSensors.findSensor(asyncSensorManager, context.getString(R.string.doze_brightness_sensor_type), null))};
        }
        Optional[] optionalArr = new Optional[5];
        Arrays.fill(optionalArr, Optional.empty());
        HashMap map = new HashMap();
        for (int i = 0; i < stringArray.length; i++) {
            String str = stringArray[i];
            if (!map.containsKey(str)) {
                map.put(str, Optional.ofNullable(DozeSensors.findSensor(asyncSensorManager, context.getString(R.string.doze_brightness_sensor_type), stringArray[i])));
            }
            optionalArr[i] = (Optional) map.get(str);
        }
        return optionalArr;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesBrightnessSensors((AsyncSensorManager) this.sensorManagerProvider.get(), (Context) this.contextProvider.get(), (DozeParameters) this.dozeParametersProvider.get());
    }
}
