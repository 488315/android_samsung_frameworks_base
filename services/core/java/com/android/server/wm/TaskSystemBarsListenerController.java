package com.android.server.wm;

import com.android.internal.os.BackgroundThread;

import java.util.HashSet;
import java.util.concurrent.Executor;

public final class TaskSystemBarsListenerController {
    public final HashSet mListeners = new HashSet();
    public final Executor mBackgroundExecutor = BackgroundThread.getExecutor();
}
