package com.samsung.context.sdk.samsunganalytics.internal.sender;

import android.content.Context;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;

/* loaded from: classes4.dex */
public class Sender {
    public static Configuration configuration;
    public static BaseLogSender logSender;

    /* JADX WARN: Removed duplicated region for block: B:8:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static BaseLogSender get(Context context, int i, Configuration configuration2) {
        if (logSender != null) {
            Configuration configuration3 = configuration;
            if (!Utils.isSendingAppCommonSupported(context) && configuration3 == null) {
                synchronized (Sender.class) {
                    try {
                        if (!Utils.isSendingAppCommonSupported(context) && configuration == null) {
                            logSender = configuration2 == null ? null : SenderHolder.diagnosticInstance;
                            configuration = configuration2;
                        }
                        if (logSender == null) {
                            if (i == 0) {
                                logSender = new DLSLogSender(context, configuration2);
                            } else if (i == 2 || i == 3) {
                                logSender = new DMALogSender(context, configuration2);
                            } else {
                                Debug.logwingE("Sender type is invalid : " + i);
                            }
                            BaseLogSender baseLogSender = logSender;
                            if (configuration2 != null) {
                                SenderHolder.diagnosticInstance = baseLogSender;
                            }
                            configuration = configuration2;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
        return logSender;
    }
}
