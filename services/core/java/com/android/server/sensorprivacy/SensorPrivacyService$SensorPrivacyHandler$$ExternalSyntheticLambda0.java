package com.android.server.sensorprivacy;

import android.os.IBinder;
import android.util.Pair;

import com.android.internal.util.function.TriConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class SensorPrivacyService$SensorPrivacyHandler$$ExternalSyntheticLambda0 implements TriConsumer {
    public final void accept(Object obj, Object obj2, Object obj3) {
        int i = SensorPrivacyService.SensorPrivacyServiceImpl.$r8$clinit;
        ((SensorPrivacyService.SensorPrivacyServiceImpl) obj)
                .removeSuppressPackageReminderToken((Pair) obj2, (IBinder) obj3);
    }
}
