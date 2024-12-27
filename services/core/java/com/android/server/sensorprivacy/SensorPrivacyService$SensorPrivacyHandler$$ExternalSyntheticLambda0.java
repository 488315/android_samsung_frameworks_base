package com.android.server.sensorprivacy;

import android.os.IBinder;
import android.util.Pair;

import com.android.internal.util.function.TriConsumer;

public final /* synthetic */
class SensorPrivacyService$SensorPrivacyHandler$$ExternalSyntheticLambda0 implements TriConsumer {
    public final void accept(Object obj, Object obj2, Object obj3) {
        int i = SensorPrivacyService.SensorPrivacyServiceImpl.$r8$clinit;
        ((SensorPrivacyService.SensorPrivacyServiceImpl) obj)
                .removeSuppressPackageReminderToken((Pair) obj2, (IBinder) obj3);
    }
}
