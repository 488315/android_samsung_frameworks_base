package com.android.server.telecom;

import android.os.Binder;
import android.os.Process;
import com.android.internal.telecom.IDeviceIdleControllerAdapter;
import com.android.internal.telecom.IInternalServiceRetriever;
import com.android.server.DeviceIdleInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class InternalServiceRepository extends IInternalServiceRetriever.Stub {
    public final DeviceIdleInternal mDeviceIdleController;
    public final AnonymousClass1 mDeviceIdleControllerAdapter = new IDeviceIdleControllerAdapter.Stub() { // from class: com.android.server.telecom.InternalServiceRepository.1
        public final void exemptAppTemporarilyForEvent(String str, long j, int i, String str2) {
            InternalServiceRepository.this.mDeviceIdleController.addPowerSaveTempWhitelistApp(Process.myUid(), str, j, i, true, 0, str2);
        }
    };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.telecom.InternalServiceRepository$1] */
    public InternalServiceRepository(DeviceIdleInternal deviceIdleInternal) {
        this.mDeviceIdleController = deviceIdleInternal;
    }

    public final IDeviceIdleControllerAdapter getDeviceIdleController() {
        if (Binder.getCallingUid() == 1000) {
            return this.mDeviceIdleControllerAdapter;
        }
        throw new SecurityException("SYSTEM ONLY API.");
    }
}
