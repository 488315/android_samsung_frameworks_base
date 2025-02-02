package com.samsung.context.sdk.samsunganalytics.internal.sender;

import android.content.Context;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DLC.DLCLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender;

/* loaded from: classes.dex */
public final class Sender {
    private static BaseLogSender logSender;

    public static LogSender get(Context context, int i, Configuration configuration) {
        if (logSender == null) {
            synchronized (Sender.class) {
                if (i == 0) {
                    logSender = new DLSLogSender(context, configuration);
                } else if (i == 1) {
                    logSender = new DLCLogSender(context, configuration);
                } else if (i == 2 || i == 3) {
                    logSender = new DMALogSender(context, configuration);
                }
            }
        }
        return logSender;
    }
}
