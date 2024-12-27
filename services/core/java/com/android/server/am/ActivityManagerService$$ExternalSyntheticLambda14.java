package com.android.server.am;

import android.app.IApplicationThread;
import android.os.Binder;
import android.os.Build;
import android.os.PowerSaveState;
import android.os.RemoteException;
import android.util.Log;

import com.android.internal.os.ProcessCpuTracker;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ActivityManagerService$$ExternalSyntheticLambda14
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ActivityManagerService$$ExternalSyntheticLambda14(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ProcessRecord processRecord = (ProcessRecord) obj;
                ((ActivityManagerService) obj2).getClass();
                IApplicationThread iApplicationThread = processRecord.mThread;
                if (iApplicationThread != null) {
                    if (Binder.isSystemServerBinderTrackerEnabled
                            || Build.IS_DEBUGGABLE
                            || processRecord.isDebuggable()) {
                        try {
                            iApplicationThread.startBinderTracking();
                            break;
                        } catch (RemoteException unused) {
                            Log.v("ActivityManager", "Process disappared");
                            return;
                        }
                    }
                }
                break;
            case 1:
                ActivityManagerService activityManagerService = (ActivityManagerService) obj2;
                activityManagerService.getClass();
                activityManagerService.updateForceBackgroundCheck(
                        ((PowerSaveState) obj).batterySaverEnabled);
                break;
            default:
                ((ArrayList) obj2).add((ProcessCpuTracker.Stats) obj);
                break;
        }
    }
}
