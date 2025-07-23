package com.samsung.android.smartthingsmediasdk.mediasdk.manager;

import com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AbstractMediaSdkManager {
    public final MediaSdkSupportServiceClient mediaSdkSupportServiceClient;

    public AbstractMediaSdkManager(MediaSdkSupportServiceClient mediaSdkSupportServiceClient) {
        this.mediaSdkSupportServiceClient = mediaSdkSupportServiceClient;
    }

    public abstract String getTag();

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000c, code lost:
    
        if (r6 == null) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object useSafeMediaSdkSupportService(java.lang.Object r5, kotlin.jvm.functions.Function1 r6) {
        /*
            r4 = this;
            int r0 = kotlin.Result.$r8$clinit     // Catch: java.lang.Throwable -> Lf
            com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient r0 = r4.mediaSdkSupportServiceClient     // Catch: java.lang.Throwable -> Lf
            com.samsung.android.oneconnect.mediaoutput.IMediaOutputService r0 = r0.mediaSdkSupportService     // Catch: java.lang.Throwable -> Lf
            if (r0 == 0) goto L11
            java.lang.Object r6 = r6.mo779invoke(r0)     // Catch: java.lang.Throwable -> Lf
            if (r6 != 0) goto L1b
            goto L11
        Lf:
            r6 = move-exception
            goto L13
        L11:
            r6 = r5
            goto L1b
        L13:
            int r0 = kotlin.Result.$r8$clinit
            kotlin.Result$Failure r0 = new kotlin.Result$Failure
            r0.<init>(r6)
            r6 = r0
        L1b:
            java.lang.Throwable r0 = kotlin.Result.m3422exceptionOrNullimpl(r6)
            if (r0 == 0) goto L3d
            com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog$Companion r1 = com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog.Companion
            java.lang.String r4 = r4.getTag()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "throwable: "
            r2.<init>(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.getClass()
            java.lang.String r1 = "useSafeMediaSdkSupportService.onFailure"
            com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog.Companion.i(r4, r1, r0)
        L3d:
            boolean r4 = r6 instanceof kotlin.Result.Failure
            if (r4 == 0) goto L42
            goto L43
        L42:
            r5 = r6
        L43:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager.useSafeMediaSdkSupportService(java.lang.Object, kotlin.jvm.functions.Function1):java.lang.Object");
    }
}
