package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.os.RemoteException;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paResult;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class C2paClientGetManifestRunnable extends TaskRunnable {
    private static final String TAG = "C2paClientGetManifestRunnable";
    private C2paManifestsCallback mCallback = new C2paManifestsCallback() { // from class: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientGetManifestRunnable.1
        @Override // com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestsCallback, com.samsung.android.visual.ai.sdkcommon.IC2paManifestsCallback
        public void onError(String str) throws RemoteException {
            ((TaskRunnable) C2paClientGetManifestRunnable.this).mSource.setResult(new C2paResult.Builder().setSuccess(false).setError(str).build());
        }

        @Override // com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestsCallback, com.samsung.android.visual.ai.sdkcommon.IC2paManifestsCallback
        public void onResult(String str, boolean z, boolean z2) throws RemoteException {
            ((TaskRunnable) C2paClientGetManifestRunnable.this).mSource.setResult(new C2paResult.Builder().setSuccess(true).setTrusted(z).setCompleted(z2).setManifestResult(str).build());
        }
    };
    private String mFilePath;
    private final C2paServiceExecutor mServiceExecutor;

    public C2paClientGetManifestRunnable(C2paServiceExecutor c2paServiceExecutor) {
        this.mServiceExecutor = c2paServiceExecutor;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x004d, code lost:
    
        if (r1.getFileDescriptor().valid() == false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004f, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0053, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0054, code lost:
    
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
            java.lang.String r0 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientGetManifestRunnable.TAG
            java.lang.String r1 = "execute getManifestsAsString()"
            com.samsung.android.sdk.scs.base.utils.Log.d(r0, r1)
            java.lang.String r0 = r3.mFilePath     // Catch: java.lang.Exception -> L41
            java.lang.String r0 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paUtils.getFileExtension(r0)     // Catch: java.lang.Exception -> L41
            java.lang.String r1 = r3.mFilePath     // Catch: java.lang.Exception -> L41
            android.os.ParcelFileDescriptor r1 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paUtils.getParcelFileDescriptor(r1)     // Catch: java.lang.Exception -> L41
            if (r1 == 0) goto L43
            if (r0 == 0) goto L43
            java.lang.String r2 = r3.mFilePath     // Catch: java.lang.Exception -> L41
            if (r2 != 0) goto L1c
            goto L43
        L1c:
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$ExtractParamBuilder r2 = new com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$ExtractParamBuilder     // Catch: java.lang.Exception -> L41
            r2.<init>()     // Catch: java.lang.Exception -> L41
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$ExtractParamBuilder r1 = r2.setPfd(r1)     // Catch: java.lang.Exception -> L41
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$ExtractParamBuilder r0 = r1.setExtensionType(r0)     // Catch: java.lang.Exception -> L41
            java.lang.String r1 = r3.mFilePath     // Catch: java.lang.Exception -> L41
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$ExtractParamBuilder r0 = r0.setFilePath(r1)     // Catch: java.lang.Exception -> L41
            android.os.Bundle r0 = r0.build()     // Catch: java.lang.Exception -> L41
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paServiceExecutor r1 = r3.mServiceExecutor     // Catch: java.lang.Exception -> L41
            com.samsung.android.visual.ai.sdkcommon.IDpsC2pa r1 = r1.getC2PAService()     // Catch: java.lang.Exception -> L41
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestsCallback r2 = r3.mCallback     // Catch: java.lang.Exception -> L41
            com.samsung.android.visual.ai.sdkcommon.IDpsC2pa$Stub$Proxy r1 = (com.samsung.android.visual.ai.sdkcommon.IDpsC2pa.Stub.Proxy) r1     // Catch: java.lang.Exception -> L41
            r1.getManifestsAsStringWithPfd(r0, r2)     // Catch: java.lang.Exception -> L41
            return
        L41:
            r0 = move-exception
            goto L5f
        L43:
            if (r1 == 0) goto L57
            java.io.FileDescriptor r0 = r1.getFileDescriptor()     // Catch: java.lang.Exception -> L53
            boolean r0 = r0.valid()     // Catch: java.lang.Exception -> L53
            if (r0 == 0) goto L57
            r1.close()     // Catch: java.lang.Exception -> L53
            goto L57
        L53:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Exception -> L41
        L57:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException     // Catch: java.lang.Exception -> L41
            java.lang.String r1 = "Target PFD/Extension is NULL"
            r0.<init>(r1)     // Catch: java.lang.Exception -> L41
            throw r0     // Catch: java.lang.Exception -> L41
        L5f:
            r0.printStackTrace()
            com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource r3 = r3.mSource
            r3.setException(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientGetManifestRunnable.execute():void");
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public String getFeatureName() {
        return "FEATURE_C2PA";
    }

    public void setFilePath(String str) {
        this.mFilePath = str;
    }
}
