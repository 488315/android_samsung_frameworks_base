package com.android.systemui.inputdevice.tutorial.ui;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor;
import com.android.systemui.settings.UserTracker;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class TutorialNotificationCoordinator {
    public final CoroutineScope backgroundScope;
    public final Context context;
    public final NotificationManager notificationManager;
    public final TutorialSchedulerInteractor tutorialSchedulerInteractor;
    public final UserTracker userTracker;

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

    public TutorialNotificationCoordinator(CoroutineScope coroutineScope, Context context, TutorialSchedulerInteractor tutorialSchedulerInteractor, NotificationManager notificationManager, UserTracker userTracker) {
        this.backgroundScope = coroutineScope;
        this.context = context;
        this.tutorialSchedulerInteractor = tutorialSchedulerInteractor;
        this.notificationManager = notificationManager;
        this.userTracker = userTracker;
    }
}
