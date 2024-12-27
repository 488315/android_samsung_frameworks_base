package com.android.server.net;

import android.os.Process;
import android.os.RemoteException;
import android.sec.enterprise.auditlog.AuditLog;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.net.NetworkManagementService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NetworkManagementService.NetdUnsolicitedEventListener f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda1(NetworkManagementService.NetdUnsolicitedEventListener netdUnsolicitedEventListener, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = netdUnsolicitedEventListener;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NetworkManagementService.NetdUnsolicitedEventListener netdUnsolicitedEventListener = this.f$0;
                String str = this.f$1;
                NetworkManagementService networkManagementService = netdUnsolicitedEventListener.this$0;
                networkManagementService.mActiveAlerts.remove(str);
                networkManagementService.mActiveQuotas.remove(str);
                AuditLog.log(5, 4, true, Process.myPid(), "NetworkManagementService", XmlUtils$$ExternalSyntheticOutline0.m("Interface ", str, " is untethered"));
                int beginBroadcast = networkManagementService.mObservers.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        networkManagementService.mObservers.getBroadcastItem(i).interfaceRemoved(str);
                    } catch (RemoteException | RuntimeException unused) {
                    } catch (Throwable th) {
                        networkManagementService.mObservers.finishBroadcast();
                        throw th;
                    }
                }
                networkManagementService.mObservers.finishBroadcast();
                return;
            default:
                NetworkManagementService.NetdUnsolicitedEventListener netdUnsolicitedEventListener2 = this.f$0;
                String str2 = this.f$1;
                NetworkManagementService networkManagementService2 = netdUnsolicitedEventListener2.this$0;
                networkManagementService2.getClass();
                AuditLog.log(5, 4, true, Process.myPid(), "NetworkManagementService", XmlUtils$$ExternalSyntheticOutline0.m("Interface ", str2, " is tethered"));
                int beginBroadcast2 = networkManagementService2.mObservers.beginBroadcast();
                for (int i2 = 0; i2 < beginBroadcast2; i2++) {
                    try {
                        networkManagementService2.mObservers.getBroadcastItem(i2).interfaceAdded(str2);
                    } catch (RemoteException | RuntimeException unused2) {
                    } catch (Throwable th2) {
                        networkManagementService2.mObservers.finishBroadcast();
                        throw th2;
                    }
                }
                networkManagementService2.mObservers.finishBroadcast();
                return;
        }
    }
}
