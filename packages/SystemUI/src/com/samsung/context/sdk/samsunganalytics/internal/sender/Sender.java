package com.samsung.context.sdk.samsunganalytics.internal.sender;

import com.samsung.context.sdk.samsunganalytics.Configuration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Sender {
    public static Configuration configuration;
    public static BaseLogSender logSender;

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000f, code lost:
    
        if (r1 != null) goto L39;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender get(android.content.Context r4, int r5, com.samsung.context.sdk.samsunganalytics.Configuration r6) {
        /*
            java.lang.String r0 = "Sender type is invalid : "
            com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender r1 = com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.logSender
            if (r1 == 0) goto L11
            com.samsung.context.sdk.samsunganalytics.Configuration r1 = com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.configuration
            boolean r2 = com.samsung.context.sdk.samsunganalytics.internal.util.Utils.isSendingAppCommonSupported(r4)
            if (r2 == 0) goto Lf
            goto L66
        Lf:
            if (r1 != 0) goto L66
        L11:
            java.lang.Class<com.samsung.context.sdk.samsunganalytics.internal.sender.Sender> r1 = com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.class
            monitor-enter(r1)
            com.samsung.context.sdk.samsunganalytics.Configuration r2 = com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.configuration     // Catch: java.lang.Throwable -> L2f
            boolean r3 = com.samsung.context.sdk.samsunganalytics.internal.util.Utils.isSendingAppCommonSupported(r4)     // Catch: java.lang.Throwable -> L2f
            if (r3 == 0) goto L1d
            goto L21
        L1d:
            if (r2 != 0) goto L21
            r2 = 1
            goto L22
        L21:
            r2 = 0
        L22:
            if (r2 == 0) goto L31
            if (r6 != 0) goto L28
            r2 = 0
            goto L2a
        L28:
            com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender r2 = com.samsung.context.sdk.samsunganalytics.internal.sender.SenderHolder.diagnosticInstance     // Catch: java.lang.Throwable -> L2f
        L2a:
            com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.logSender = r2     // Catch: java.lang.Throwable -> L2f
            com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.configuration = r6     // Catch: java.lang.Throwable -> L2f
            goto L31
        L2f:
            r4 = move-exception
            goto L69
        L31:
            com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender r2 = com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.logSender     // Catch: java.lang.Throwable -> L2f
            if (r2 != 0) goto L65
            if (r5 == 0) goto L55
            r2 = 2
            if (r5 == r2) goto L4d
            r2 = 3
            if (r5 == r2) goto L4d
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2f
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L2f
            r4.append(r5)     // Catch: java.lang.Throwable -> L2f
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L2f
            com.samsung.context.sdk.samsunganalytics.internal.util.Debug.logwingE(r4)     // Catch: java.lang.Throwable -> L2f
            goto L5c
        L4d:
            com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender r5 = new com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender     // Catch: java.lang.Throwable -> L2f
            r5.<init>(r4, r6)     // Catch: java.lang.Throwable -> L2f
            com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.logSender = r5     // Catch: java.lang.Throwable -> L2f
            goto L5c
        L55:
            com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender r5 = new com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender     // Catch: java.lang.Throwable -> L2f
            r5.<init>(r4, r6)     // Catch: java.lang.Throwable -> L2f
            com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.logSender = r5     // Catch: java.lang.Throwable -> L2f
        L5c:
            com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender r4 = com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.logSender     // Catch: java.lang.Throwable -> L2f
            if (r6 != 0) goto L61
            goto L63
        L61:
            com.samsung.context.sdk.samsunganalytics.internal.sender.SenderHolder.diagnosticInstance = r4     // Catch: java.lang.Throwable -> L2f
        L63:
            com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.configuration = r6     // Catch: java.lang.Throwable -> L2f
        L65:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L2f
        L66:
            com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender r4 = com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.logSender
            return r4
        L69:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L2f
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.context.sdk.samsunganalytics.internal.sender.Sender.get(android.content.Context, int, com.samsung.context.sdk.samsunganalytics.Configuration):com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender");
    }
}
