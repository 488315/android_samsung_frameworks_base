package com.google.android.msdl.logging;

import java.util.ArrayDeque;
import java.util.Deque;

/* loaded from: classes4.dex */
public final class MSDLHistoryLoggerImpl implements MSDLHistoryLogger {
    public final Deque history;
    public final int maxHistorySize;

    public MSDLHistoryLoggerImpl(int i) {
        this.maxHistorySize = i;
        this.history = new ArrayDeque(i);
    }
}
