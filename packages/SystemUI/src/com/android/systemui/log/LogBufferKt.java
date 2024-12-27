package com.android.systemui.log;

import com.android.systemui.log.LogMessageImpl;

public abstract class LogBufferKt {
    public static final LogMessageImpl FROZEN_MESSAGE;

    static {
        LogMessageImpl.Factory.getClass();
        FROZEN_MESSAGE = LogMessageImpl.Factory.create();
    }
}
