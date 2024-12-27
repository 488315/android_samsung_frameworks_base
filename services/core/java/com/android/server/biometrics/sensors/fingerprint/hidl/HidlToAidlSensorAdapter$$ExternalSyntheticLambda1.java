package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.content.pm.UserInfo;
import android.os.UserManager;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class HidlToAidlSensorAdapter$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HidlToAidlSensorAdapter f$0;

    public /* synthetic */ HidlToAidlSensorAdapter$$ExternalSyntheticLambda1(HidlToAidlSensorAdapter hidlToAidlSensorAdapter, int i) {
        this.$r8$classId = i;
        this.f$0 = hidlToAidlSensorAdapter;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        HidlToAidlSensorAdapter hidlToAidlSensorAdapter = this.f$0;
        switch (i) {
            case 0:
                hidlToAidlSensorAdapter.getIBiometricsFingerprint();
                break;
            default:
                Iterator it = UserManager.get(hidlToAidlSensorAdapter.mContext).getAliveUsers().iterator();
                while (it.hasNext()) {
                    int i2 = ((UserInfo) it.next()).id;
                    if (!((HashMap) hidlToAidlSensorAdapter.mAuthenticatorIds).containsKey(Integer.valueOf(i2))) {
                        hidlToAidlSensorAdapter.mScheduler.scheduleClientMonitor(hidlToAidlSensorAdapter.getFingerprintUpdateActiveUserClient(i2, true), null);
                    }
                }
                break;
        }
    }
}
