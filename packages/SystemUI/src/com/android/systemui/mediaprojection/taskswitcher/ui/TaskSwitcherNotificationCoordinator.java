package com.android.systemui.mediaprojection.taskswitcher.ui;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TaskSwitcherNotificationCoordinator {
    public final BroadcastDispatcher broadcastDispatcher;
    public final Context context;
    public final NotificationManager notificationManager;
    public final TaskSwitcherNotificationViewModel viewModel;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public TaskSwitcherNotificationCoordinator(Context context, NotificationManager notificationManager, CoroutineScope coroutineScope, TaskSwitcherNotificationViewModel taskSwitcherNotificationViewModel, BroadcastDispatcher broadcastDispatcher) {
    }
}
