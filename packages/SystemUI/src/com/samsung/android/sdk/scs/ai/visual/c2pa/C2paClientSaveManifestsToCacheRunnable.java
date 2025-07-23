package com.samsung.android.sdk.scs.ai.visual.c2pa;

import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class C2paClientSaveManifestsToCacheRunnable extends TaskRunnable {
    private static final String TAG = "C2paClientSaveManifestsToCacheRunnable";
    private final C2paServiceExecutor mServiceExecutor;
    String mfilePath;

    public C2paClientSaveManifestsToCacheRunnable(C2paServiceExecutor c2paServiceExecutor) {
        this.mServiceExecutor = c2paServiceExecutor;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0051, code lost:
    
        if (r1.getFileDescriptor().valid() == false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0053, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0057, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0058, code lost:
    
        r0.printStackTrace();
     */
    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void execute() {
        /*
            r3 = this;
            java.lang.String r0 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientSaveManifestsToCacheRunnable.TAG
            java.lang.String r1 = "execute saveManifestsToCache()"
            com.samsung.android.sdk.scs.base.utils.Log.d(r0, r1)
            java.lang.String r0 = r3.mfilePath     // Catch: java.lang.Exception -> L45
            java.lang.String r0 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paUtils.getFileExtension(r0)     // Catch: java.lang.Exception -> L45
            java.lang.String r1 = r3.mfilePath     // Catch: java.lang.Exception -> L45
            android.os.ParcelFileDescriptor r1 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paUtils.getParcelFileDescriptor(r1)     // Catch: java.lang.Exception -> L45
            if (r1 == 0) goto L47
            if (r0 == 0) goto L47
            java.lang.String r2 = r3.mfilePath     // Catch: java.lang.Exception -> L45
            if (r2 != 0) goto L1c
            goto L47
        L1c:
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$SaveToCacheParamBuilder r2 = new com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$SaveToCacheParamBuilder     // Catch: java.lang.Exception -> L45
            r2.<init>()     // Catch: java.lang.Exception -> L45
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$SaveToCacheParamBuilder r1 = r2.setPfd(r1)     // Catch: java.lang.Exception -> L45
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$SaveToCacheParamBuilder r0 = r1.setExtensionType(r0)     // Catch: java.lang.Exception -> L45
            java.lang.String r1 = r3.mfilePath     // Catch: java.lang.Exception -> L45
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$SaveToCacheParamBuilder r0 = r0.setFilePath(r1)     // Catch: java.lang.Exception -> L45
            android.os.Bundle r0 = r0.build()     // Catch: java.lang.Exception -> L45
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paServiceExecutor r1 = r3.mServiceExecutor     // Catch: java.lang.Exception -> L45
            com.samsung.android.visual.ai.sdkcommon.IDpsC2pa r1 = r1.getC2PAService()     // Catch: java.lang.Exception -> L45
            com.samsung.android.visual.ai.sdkcommon.IDpsC2pa$Stub$Proxy r1 = (com.samsung.android.visual.ai.sdkcommon.IDpsC2pa.Stub.Proxy) r1     // Catch: java.lang.Exception -> L45
            java.lang.String r0 = r1.saveManifestsToCacheWithPfd(r0)     // Catch: java.lang.Exception -> L45
            com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource r1 = r3.mSource     // Catch: java.lang.Exception -> L45
            r1.setResult(r0)     // Catch: java.lang.Exception -> L45
            return
        L45:
            r0 = move-exception
            goto L63
        L47:
            if (r1 == 0) goto L5b
            java.io.FileDescriptor r0 = r1.getFileDescriptor()     // Catch: java.lang.Exception -> L57
            boolean r0 = r0.valid()     // Catch: java.lang.Exception -> L57
            if (r0 == 0) goto L5b
            r1.close()     // Catch: java.lang.Exception -> L57
            goto L5b
        L57:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Exception -> L45
        L5b:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException     // Catch: java.lang.Exception -> L45
            java.lang.String r1 = "Target PFD/Extension is NULL"
            r0.<init>(r1)     // Catch: java.lang.Exception -> L45
            throw r0     // Catch: java.lang.Exception -> L45
        L63:
            r0.printStackTrace()
            com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource r3 = r3.mSource
            r3.setException(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientSaveManifestsToCacheRunnable.execute():void");
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public String getFeatureName() {
        return "FEATURE_C2PA";
    }

    public void setFilePath(String str) {
        this.mfilePath = str;
    }
}
