package com.android.systemui.log;

import com.android.systemui.log.LogMessageImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class LogBufferKt {
    public static final LogMessageImpl FROZEN_MESSAGE;

    static {
        LogMessageImpl.Factory.getClass();
        FROZEN_MESSAGE = LogMessageImpl.Factory.create();
    }
}
