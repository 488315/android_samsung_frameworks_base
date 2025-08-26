package com.android.systemui.recordissue;

import android.app.IActivityManager;
import android.app.NotificationManager;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.settings.UserContextProvider;
import com.android.traceur.PresetTraceConfigs;
import com.android.traceur.TraceConfig;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class IssueRecordingServiceSession {
    public final Executor bgExecutor;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final IActivityManager iActivityManager;
    public final IssueRecordingState issueRecordingState;
    public final NotificationManager notificationManager;
    public final PanelInteractor panelInteractor;
    public boolean screenRecord;
    public final ScreenRecordingStartTimeStore startTimeStore;
    public boolean takeBugReport;
    public TraceConfig traceConfig = PresetTraceConfigs.getDefaultConfig();
    public final TraceurConnection traceurConnection;
    public final UserContextProvider userContextProvider;

    public IssueRecordingServiceSession(Executor executor, DialogTransitionAnimator dialogTransitionAnimator, PanelInteractor panelInteractor, TraceurConnection traceurConnection, IssueRecordingState issueRecordingState, IActivityManager iActivityManager, NotificationManager notificationManager, UserContextProvider userContextProvider, ScreenRecordingStartTimeStore screenRecordingStartTimeStore) {
        this.bgExecutor = executor;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.panelInteractor = panelInteractor;
        this.traceurConnection = traceurConnection;
        this.issueRecordingState = issueRecordingState;
        this.iActivityManager = iActivityManager;
        this.notificationManager = notificationManager;
        this.userContextProvider = userContextProvider;
        this.startTimeStore = screenRecordingStartTimeStore;
    }
}
