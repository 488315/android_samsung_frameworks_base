package com.android.systemui.log;

import com.android.systemui.log.LogMessageImpl;

/* loaded from: classes2.dex */
public abstract class LogBufferKt {
    public static final LogMessageImpl FROZEN_MESSAGE;

    static {
        LogMessageImpl.Factory.getClass();
        FROZEN_MESSAGE = LogMessageImpl.Factory.create();
    }
}
