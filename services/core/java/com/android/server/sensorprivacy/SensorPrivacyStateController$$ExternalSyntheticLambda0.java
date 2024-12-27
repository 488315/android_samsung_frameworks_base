package com.android.server.sensorprivacy;

import java.util.function.BiConsumer;

public final /* synthetic */ class SensorPrivacyStateController$$ExternalSyntheticLambda0
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        ((SensorPrivacyStateController$SetStateResultCallback) obj)
                .callback(((Boolean) obj2).booleanValue());
    }
}
