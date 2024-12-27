package com.android.server.biometrics.log;

import com.android.internal.logging.InstanceId;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BiometricContextSessionInfo {
    public final InstanceId mId;
    public final AtomicInteger mOrder = new AtomicInteger(0);

    public BiometricContextSessionInfo(InstanceId instanceId) {
        this.mId = instanceId;
    }

    public final String toString() {
        return "[sid: " + this.mId.getId() + "]";
    }
}
