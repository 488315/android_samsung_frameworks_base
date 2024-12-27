package com.android.systemui.mediaprojection.taskswitcher.ui;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

public final class TaskSwitcherNotificationCoordinator {
    public final BroadcastDispatcher broadcastDispatcher;
    public final Context context;
    public final NotificationManager notificationManager;
    public final TaskSwitcherNotificationViewModel viewModel;

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
