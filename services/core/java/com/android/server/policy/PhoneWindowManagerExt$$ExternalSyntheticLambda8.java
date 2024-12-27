package com.android.server.policy;

import android.app.ActivityManagerInternal;
import android.app.ActivityTaskManager;
import android.net.util.NetdService$$ExternalSyntheticOutline0;
import android.os.RemoteException;
import android.util.Log;
import com.android.server.LocalServices;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PhoneWindowManagerExt$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Log.d("PhoneWindowManagerExt", "Stop Lock Task Mode");
                try {
                    ActivityTaskManager.getService().stopSystemLockTaskMode();
                    break;
                } catch (RemoteException e) {
                    NetdService$$ExternalSyntheticOutline0.m("Unable to reach activity manager, ", e, "PhoneWindowManagerExt");
                    return;
                }
            default:
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).requestCustomFullBugreport();
                break;
        }
    }
}
