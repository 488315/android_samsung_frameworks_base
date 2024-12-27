package com.android.server;

import android.content.Context;
import android.util.Log;

import com.android.internal.util.ConcurrentUtils;
import com.android.server.location.contexthub.ContextHubService;

import java.util.concurrent.Future;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ContextHubSystemService extends SystemService {
    public ContextHubService mContextHubService;
    public Future mInit;

    public ContextHubSystemService(final Context context) {
        super(context);
        this.mInit =
                SystemServerInitThreadPool.submit(
                        "Init ContextHubSystemService",
                        new Runnable() { // from class:
                            // com.android.server.ContextHubSystemService$$ExternalSyntheticLambda0
                            /* JADX WARN: Removed duplicated region for block: B:11:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                            /* JADX WARN: Removed duplicated region for block: B:14:0x0068  */
                            /* JADX WARN: Removed duplicated region for block: B:21:0x003a A[EXC_TOP_SPLITTER, SYNTHETIC] */
                            /* JADX WARN: Removed duplicated region for block: B:24:0x004a  */
                            /* JADX WARN: Removed duplicated region for block: B:25:0x004c  */
                            /* JADX WARN: Removed duplicated region for block: B:34:0x0030  */
                            /* JADX WARN: Removed duplicated region for block: B:35:0x0032  */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void run() {
                                /*
                                    r10 = this;
                                    com.android.server.ContextHubSystemService r0 = com.android.server.ContextHubSystemService.this
                                    android.content.Context r10 = r2
                                    r0.getClass()
                                    com.android.server.location.contexthub.ContextHubService r1 = new com.android.server.location.contexthub.ContextHubService
                                    android.hardware.contexthub.IContextHub r2 = com.android.server.location.contexthub.IContextHubWrapper.maybeConnectToAidlGetProxy()
                                    r3 = 0
                                    if (r2 != 0) goto L12
                                    r4 = r3
                                    goto L17
                                L12:
                                    com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperAidl r4 = new com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperAidl
                                    r4.<init>(r2)
                                L17:
                                    java.lang.String r2 = "RemoteException while attaching to Context Hub HAL proxy"
                                    java.lang.String r5 = "Context Hub HAL service not found"
                                    r6 = 1
                                    java.lang.String r7 = "IContextHubWrapper"
                                    if (r4 != 0) goto L38
                                    android.hardware.contexthub.V1_2.IContexthub r4 = android.hardware.contexthub.V1_2.IContexthub.getService(r6)     // Catch: java.util.NoSuchElementException -> L25 android.os.RemoteException -> L29
                                    goto L2e
                                L25:
                                    android.util.Log.i(r7, r5)
                                    goto L2d
                                L29:
                                    r4 = move-exception
                                    android.util.Log.e(r7, r2, r4)
                                L2d:
                                    r4 = r3
                                L2e:
                                    if (r4 != 0) goto L32
                                    r4 = r3
                                    goto L38
                                L32:
                                    com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperV1_2 r8 = new com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperV1_2
                                    r8.<init>(r4)
                                    r4 = r8
                                L38:
                                    if (r4 != 0) goto L55
                                    android.hardware.contexthub.V1_1.IContexthub r4 = android.hardware.contexthub.V1_1.IContexthub.getService(r6)     // Catch: java.util.NoSuchElementException -> L3f android.os.RemoteException -> L43
                                    goto L48
                                L3f:
                                    android.util.Log.i(r7, r5)
                                    goto L47
                                L43:
                                    r4 = move-exception
                                    android.util.Log.e(r7, r2, r4)
                                L47:
                                    r4 = r3
                                L48:
                                    if (r4 != 0) goto L4c
                                    r4 = r3
                                    goto L55
                                L4c:
                                    com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperV1_0 r8 = new com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperV1_0
                                    r9 = 1
                                    r8.<init>(r4, r9)
                                    r8.mHub = r4
                                    r4 = r8
                                L55:
                                    if (r4 != 0) goto L71
                                    android.hardware.contexthub.V1_0.IContexthub r2 = android.hardware.contexthub.V1_0.IContexthub.getService(r6)     // Catch: java.util.NoSuchElementException -> L5c android.os.RemoteException -> L60
                                    goto L65
                                L5c:
                                    android.util.Log.i(r7, r5)
                                    goto L64
                                L60:
                                    r4 = move-exception
                                    android.util.Log.e(r7, r2, r4)
                                L64:
                                    r2 = r3
                                L65:
                                    if (r2 != 0) goto L68
                                    goto L70
                                L68:
                                    com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperV1_0 r3 = new com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperV1_0
                                    r4 = 0
                                    r3.<init>(r2, r4)
                                    r3.mHub = r2
                                L70:
                                    r4 = r3
                                L71:
                                    r1.<init>(r10, r4)
                                    r0.mContextHubService = r1
                                    return
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.server.ContextHubSystemService$$ExternalSyntheticLambda0.run():void");
                            }
                        });
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            Log.d("ContextHubSystemService", "onBootPhase: PHASE_SYSTEM_SERVICES_READY");
            ConcurrentUtils.waitForFutureNoInterrupt(
                    this.mInit, "Wait for ContextHubSystemService init");
            this.mInit = null;
            publishBinderService("contexthub", this.mContextHubService);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {}

    @Override // com.android.server.SystemService
    public final void onUserSwitching(
            SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        ContextHubService contextHubService = this.mContextHubService;
        StringBuilder sb = new StringBuilder("User changed to id: ");
        contextHubService.getClass();
        sb.append(ContextHubService.getCurrentUserId());
        Log.d("ContextHubService", sb.toString());
        contextHubService.sendLocationSettingUpdate();
        contextHubService.sendMicrophoneDisableSettingUpdateForCurrentUser();
    }
}
