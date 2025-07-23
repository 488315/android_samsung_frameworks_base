package com.samsung.android.sdk.scs.ai.visual.c2pa;

import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class C2paClientIsC2paExistRunnable extends TaskRunnable {
    private static final String TAG = "C2paClientIsC2paExistRunnable";
    String mFilePath;
    private final C2paServiceExecutor mServiceExecutor;

    public C2paClientIsC2paExistRunnable(C2paServiceExecutor c2paServiceExecutor) {
        this.mServiceExecutor = c2paServiceExecutor;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x004b, code lost:
    
        if (r1.getFileDescriptor().valid() == false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x004d, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0051, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0052, code lost:
    
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
            java.lang.String r0 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientIsC2paExistRunnable.TAG
            java.lang.String r1 = "execute isC2paExist()"
            com.samsung.android.sdk.scs.base.utils.Log.d(r0, r1)
            java.lang.String r0 = r3.mFilePath     // Catch: java.lang.Exception -> L3f
            java.lang.String r0 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paUtils.getFileExtension(r0)     // Catch: java.lang.Exception -> L3f
            java.lang.String r1 = r3.mFilePath     // Catch: java.lang.Exception -> L3f
            android.os.ParcelFileDescriptor r1 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paUtils.getParcelFileDescriptor(r1)     // Catch: java.lang.Exception -> L3f
            if (r1 == 0) goto L41
            if (r0 != 0) goto L18
            goto L41
        L18:
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$C2paExistParamBuilder r2 = new com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$C2paExistParamBuilder     // Catch: java.lang.Exception -> L3f
            r2.<init>()     // Catch: java.lang.Exception -> L3f
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$C2paExistParamBuilder r1 = r2.setPfd(r1)     // Catch: java.lang.Exception -> L3f
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$C2paExistParamBuilder r0 = r1.setExtensionType(r0)     // Catch: java.lang.Exception -> L3f
            android.os.Bundle r0 = r0.build()     // Catch: java.lang.Exception -> L3f
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paServiceExecutor r1 = r3.mServiceExecutor     // Catch: java.lang.Exception -> L3f
            com.samsung.android.visual.ai.sdkcommon.IDpsC2pa r1 = r1.getC2PAService()     // Catch: java.lang.Exception -> L3f
            com.samsung.android.visual.ai.sdkcommon.IDpsC2pa$Stub$Proxy r1 = (com.samsung.android.visual.ai.sdkcommon.IDpsC2pa.Stub.Proxy) r1     // Catch: java.lang.Exception -> L3f
            boolean r0 = r1.isC2paInfoExistWithPfd(r0)     // Catch: java.lang.Exception -> L3f
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch: java.lang.Exception -> L3f
            com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource r1 = r3.mSource     // Catch: java.lang.Exception -> L3f
            r1.setResult(r0)     // Catch: java.lang.Exception -> L3f
            return
        L3f:
            r0 = move-exception
            goto L5d
        L41:
            if (r1 == 0) goto L55
            java.io.FileDescriptor r0 = r1.getFileDescriptor()     // Catch: java.lang.Exception -> L51
            boolean r0 = r0.valid()     // Catch: java.lang.Exception -> L51
            if (r0 == 0) goto L55
            r1.close()     // Catch: java.lang.Exception -> L51
            goto L55
        L51:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Exception -> L3f
        L55:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException     // Catch: java.lang.Exception -> L3f
            java.lang.String r1 = "Target PFD/Extension is NULL"
            r0.<init>(r1)     // Catch: java.lang.Exception -> L3f
            throw r0     // Catch: java.lang.Exception -> L3f
        L5d:
            r0.printStackTrace()
            com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource r3 = r3.mSource
            r3.setException(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientIsC2paExistRunnable.execute():void");
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public String getFeatureName() {
        return "FEATURE_C2PA";
    }

    public void setParentPath(String str) {
        this.mFilePath = str;
    }
}
