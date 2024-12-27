package com.android.systemui.screenshot.scroll;

import android.content.Context;
import android.view.IWindowManager;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class ScrollCaptureClient {
    static final int MATCH_ANY_TASK = -1;
    public final Executor mBgExecutor;

    public interface Session {
    }

    public ScrollCaptureClient(IWindowManager iWindowManager, Executor executor, Context context) {
        Objects.requireNonNull(context.getDisplay(), "context must be associated with a Display!");
        this.mBgExecutor = executor;
    }
}
