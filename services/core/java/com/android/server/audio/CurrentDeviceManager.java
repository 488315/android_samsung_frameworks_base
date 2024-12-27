package com.android.server.audio;

import com.samsung.android.server.audio.SensorHandleThread$$ExternalSyntheticLambda0;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CurrentDeviceManager {
    public static final Object lock = new Object();
    public final Set callbacks = new HashSet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallbackRecord {
        public final SensorHandleThread$$ExternalSyntheticLambda0 callback;
        public final Executor executor;

        public CallbackRecord(
                SensorHandleThread$$ExternalSyntheticLambda0
                        sensorHandleThread$$ExternalSyntheticLambda0) {
            this.callback = sensorHandleThread$$ExternalSyntheticLambda0;
        }

        public CallbackRecord(
                SensorHandleThread$$ExternalSyntheticLambda0
                        sensorHandleThread$$ExternalSyntheticLambda0,
                Executor executor) {
            this.callback = sensorHandleThread$$ExternalSyntheticLambda0;
            this.executor = executor;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof CallbackRecord) {
                return this.callback == ((CallbackRecord) obj).callback;
            }
            return false;
        }

        public final int hashCode() {
            return this.callback.hashCode();
        }
    }
}
