package com.android.systemui.mediaprojection.taskswitcher.ui;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.mediaprojection.taskswitcher.ui.viewmodel.TaskSwitcherNotificationViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TaskSwitcherNotificationCoordinator {
    public final BroadcastDispatcher broadcastDispatcher;
    public final Context context;
    public final NotificationManager notificationManager;
    public final TaskSwitcherNotificationViewModel viewModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TaskSwitcherNotificationCoordinator(Context context, NotificationManager notificationManager, CoroutineScope coroutineScope, TaskSwitcherNotificationViewModel taskSwitcherNotificationViewModel, BroadcastDispatcher broadcastDispatcher) {
        this.context = context;
        this.notificationManager = notificationManager;
        this.viewModel = taskSwitcherNotificationViewModel;
        this.broadcastDispatcher = broadcastDispatcher;
    }
}
