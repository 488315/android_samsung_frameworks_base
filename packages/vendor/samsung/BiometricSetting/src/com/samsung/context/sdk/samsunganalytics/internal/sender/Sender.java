package com.samsung.context.sdk.samsunganalytics.internal.sender;

import android.content.Context;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DLC.DLCLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public abstract class Sender {
    public static BaseLogSender logSender;

    public static BaseLogSender get(Context context, int i, Configuration configuration) {
        if (logSender == null) {
            synchronized (Sender.class) {
                try {
                    if (i == 0) {
                        logSender = new DLSLogSender(context, configuration);
                    } else if (i == 1) {
                        logSender = new DLCLogSender(context, configuration);
                    } else if (i == 2 || i == 3) {
                        logSender = new DMALogSender(context, configuration);
                    }
                } finally {
                }
            }
        }
        return logSender;
    }
}
