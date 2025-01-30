package com.samsung.context.sdk.samsunganalytics.internal.sender;

import android.content.Context;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Sender {
    public static BaseLogSender logSender;

    public static BaseLogSender get(Context context, int i, Configuration configuration) {
        if (logSender == null) {
            synchronized (Sender.class) {
                if (i == 0) {
                    logSender = new DLSLogSender(context, configuration);
                } else if (i == 2 || i == 3) {
                    logSender = new DMALogSender(context, configuration);
                }
            }
        }
        return logSender;
    }
}
