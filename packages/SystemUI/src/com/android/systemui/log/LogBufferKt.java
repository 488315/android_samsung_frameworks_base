package com.android.systemui.log;

import com.android.systemui.log.LogMessageImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class LogBufferKt {
    public static final LogMessageImpl FROZEN_MESSAGE;

    static {
        LogMessageImpl.Factory.getClass();
        FROZEN_MESSAGE = LogMessageImpl.Factory.create();
    }
}
