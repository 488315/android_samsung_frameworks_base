package com.android.server.devicestate;

import android.hardware.devicestate.DeviceStateInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DeviceStateManagerService$ProcessRecord$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DeviceStateManagerService.ProcessRecord f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DeviceStateManagerService$ProcessRecord$$ExternalSyntheticLambda0(
            DeviceStateManagerService.ProcessRecord processRecord, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = processRecord;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DeviceStateManagerService.ProcessRecord processRecord = this.f$0;
                IBinder iBinder = (IBinder) this.f$1;
                processRecord.getClass();
                try {
                    processRecord.mCallback.onRequestActive(iBinder);
                    return;
                } catch (RemoteException e) {
                    Slog.w(
                            "DeviceStateManagerService",
                            "Failed to notify process "
                                    + processRecord.mPid
                                    + " that request state changed.",
                            e);
                    return;
                }
            case 1:
                DeviceStateManagerService.ProcessRecord processRecord2 = this.f$0;
                IBinder iBinder2 = (IBinder) this.f$1;
                processRecord2.getClass();
                try {
                    processRecord2.mCallback.onRequestCanceled(iBinder2);
                    return;
                } catch (RemoteException e2) {
                    Slog.w(
                            "DeviceStateManagerService",
                            "Failed to notify process "
                                    + processRecord2.mPid
                                    + " that request state changed.",
                            e2);
                    return;
                }
            default:
                DeviceStateManagerService.ProcessRecord processRecord3 = this.f$0;
                DeviceStateInfo deviceStateInfo = (DeviceStateInfo) this.f$1;
                processRecord3.getClass();
                boolean isTagEnabled = Trace.isTagEnabled(524288L);
                if (isTagEnabled) {
                    Trace.traceBegin(
                            524288L, "notifyDeviceStateInfoAsync(pid=" + processRecord3.mPid + ")");
                }
                try {
                    try {
                        processRecord3.mCallback.onDeviceStateInfoChanged(deviceStateInfo);
                        if (!isTagEnabled) {
                            return;
                        }
                    } catch (RemoteException e3) {
                        Slog.w(
                                "DeviceStateManagerService",
                                "Failed to notify process "
                                        + processRecord3.mPid
                                        + " that device state changed.",
                                e3);
                        if (!isTagEnabled) {
                            return;
                        }
                    }
                    Trace.traceEnd(524288L);
                    return;
                } catch (Throwable th) {
                    if (isTagEnabled) {
                        Trace.traceEnd(524288L);
                    }
                    throw th;
                }
        }
    }
}
