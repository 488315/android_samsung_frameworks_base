package com.android.server.display;

import java.util.ArrayDeque;

public final class HighBrightnessModeMetadata {
    public final ArrayDeque mEvents = new ArrayDeque();
    public long mRunningStartTimeMillis = -1;
}
