package com.android.systemui.mediaprojection.appselector.data;

import android.content.pm.UserInfo;
import android.os.UserManager;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.settings.UserTracker;
import com.android.wm.shell.recents.RecentTasks;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public final UserManager userManager;
    public final UserTracker userTracker;

    public ShellRecentTaskListProvider(CoroutineDispatcher coroutineDispatcher, Executor executor, Optional<RecentTasks> optional, UserTracker userTracker, UserManager userManager) {
        this.coroutineDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.recentTasks = optional;
        this.userTracker = userTracker;
        this.userManager = userManager;
    }

    public static final RecentTask.UserType access$toUserType(ShellRecentTaskListProvider shellRecentTaskListProvider, UserInfo userInfo) {
        shellRecentTaskListProvider.getClass();
        return userInfo.isCloneProfile() ? RecentTask.UserType.CLONED : userInfo.isManagedProfile() ? RecentTask.UserType.WORK : userInfo.isPrivateProfile() ? RecentTask.UserType.PRIVATE : RecentTask.UserType.STANDARD;
    }

    public final Object loadRecentTasks(Continuation continuation) {
        return BuildersKt.withContext(this.coroutineDispatcher, new ShellRecentTaskListProvider$loadRecentTasks$2(this, null), continuation);
    }
}
