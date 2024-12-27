package com.android.server.audio;

import com.samsung.android.server.audio.SensorHandleThread$$ExternalSyntheticLambda0;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

public final class CurrentDeviceManager {
    public static final Object lock = new Object();
    public final Set callbacks = new HashSet();

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
