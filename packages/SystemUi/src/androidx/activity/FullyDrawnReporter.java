package androidx.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FullyDrawnReporter {
    public final Executor executor;
    public final Object lock = new Object();
    public final List onReportCallbacks = new ArrayList();
    public boolean reportedFullyDrawn;

    public FullyDrawnReporter(Executor executor, Function0 function0) {
        this.executor = executor;
    }
}
