package com.google.android.msdl.logging;

import java.util.ArrayDeque;
import java.util.Deque;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MSDLHistoryLoggerImpl implements MSDLHistoryLogger {
    public final Deque history;
    public final int maxHistorySize;

    public MSDLHistoryLoggerImpl(int i) {
        this.maxHistorySize = i;
        this.history = new ArrayDeque(i);
    }
}
