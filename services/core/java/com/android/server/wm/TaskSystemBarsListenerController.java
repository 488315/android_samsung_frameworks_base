package com.android.server.wm;

import com.android.internal.os.BackgroundThread;

import java.util.HashSet;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TaskSystemBarsListenerController {
    public final HashSet mListeners = new HashSet();
    public final Executor mBackgroundExecutor = BackgroundThread.getExecutor();
}
