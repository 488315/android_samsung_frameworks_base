package com.android.systemui.screenshot;

import android.content.Context;
import android.view.IWindowManager;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScrollCaptureClient {
    static final int MATCH_ANY_TASK = -1;
    public final Executor mBgExecutor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Session {
    }

    public ScrollCaptureClient(IWindowManager iWindowManager, Executor executor, Context context) {
        Objects.requireNonNull(context.getDisplay(), "context must be associated with a Display!");
        this.mBgExecutor = executor;
    }
}
