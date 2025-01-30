package com.android.systemui.mediaprojection.appselector.data;

import com.android.wm.shell.recents.RecentTasks;
import com.android.systemui.settings.UserTracker;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ShellRecentTaskListProvider implements RecentTaskListProvider {
    public final Executor backgroundExecutor;
    public final CoroutineDispatcher coroutineDispatcher;
    public final Optional recentTasks;
    public final Lazy recents$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$recents$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (RecentTasks) ShellRecentTaskListProvider.this.recentTasks.orElse(null);
        }
    });
    public final UserTracker userTracker;

    public ShellRecentTaskListProvider(CoroutineDispatcher coroutineDispatcher, Executor executor, Optional<RecentTasks> optional, UserTracker userTracker) {
        this.coroutineDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.recentTasks = optional;
        this.userTracker = userTracker;
    }

    public final Object loadRecentTasks(Continuation continuation) {
        return BuildersKt.withContext(this.coroutineDispatcher, new ShellRecentTaskListProvider$loadRecentTasks$2(this, null), continuation);
    }
}
