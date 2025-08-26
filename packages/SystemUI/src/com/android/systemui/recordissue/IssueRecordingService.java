package com.android.systemui.recordissue;

import android.app.IActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.recordissue.TraceurConnection;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.RecordingService;
import com.android.systemui.screenrecord.RecordingServiceStrings;
import com.android.systemui.screenrecord.ScreenMediaRecorder;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.traceur.PresetTraceConfigs;
import com.android.traceur.TraceConfig;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class IssueRecordingService extends RecordingService {
    public static final Companion Companion = new Companion(null);
    public final Executor bgExecutor;
    public final IssueRecordingServiceSession session;
    public final TraceurConnection traceurConnection;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static Intent getStopIntent(Context context) {
            return new Intent(context, (Class<?>) IssueRecordingService.class).setAction("com.android.systemui.screenrecord.STOP").putExtra("android.intent.extra.user_handle", context.getUserId());
        }

        private Companion() {
        }
    }

    public IssueRecordingService(RecordingController recordingController, Executor executor, Handler handler, UiEventLogger uiEventLogger, NotificationManager notificationManager, UserContextProvider userContextProvider, KeyguardDismissUtil keyguardDismissUtil, DialogTransitionAnimator dialogTransitionAnimator, PanelInteractor panelInteractor, IssueRecordingState issueRecordingState, TraceurConnection.Provider provider, IActivityManager iActivityManager, ScreenRecordingStartTimeStore screenRecordingStartTimeStore) {
        super(recordingController, executor, handler, uiEventLogger, notificationManager, userContextProvider, keyguardDismissUtil, screenRecordingStartTimeStore);
        this.bgExecutor = executor;
        provider.getClass();
        TraceurConnection traceurConnection = new TraceurConnection(provider.userContextProvider, provider.bgLooper, null);
        this.traceurConnection = traceurConnection;
        this.session = new IssueRecordingServiceSession(executor, dialogTransitionAnimator, panelInteractor, traceurConnection, issueRecordingState, iActivityManager, notificationManager, userContextProvider, screenRecordingStartTimeStore);
    }

    @Override // com.android.systemui.screenrecord.RecordingService
    public final String getChannelId() {
        return "issue_record";
    }

    @Override // com.android.systemui.screenrecord.RecordingService
    public final String getTag() {
        return "IssueRecordingService";
    }

    @Override // com.android.systemui.screenrecord.RecordingService, android.app.Service
    public final IBinder onBind(Intent intent) {
        this.traceurConnection.doBind();
        return null;
    }

    @Override // com.android.systemui.screenrecord.RecordingService
    public final void onRecordingSaved(ScreenMediaRecorder.SavedRecording savedRecording, UserHandle userHandle) {
        IssueRecordingServiceSession issueRecordingServiceSession = this.session;
        if (!issueRecordingServiceSession.takeBugReport) {
            super.onRecordingSaved(savedRecording, userHandle);
            return;
        }
        issueRecordingServiceSession.bgExecutor.execute(new IssueRecordingServiceSession$share$1(issueRecordingServiceSession, this.mNotificationId, savedRecording != null ? savedRecording.mUri : null));
        issueRecordingServiceSession.dialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
        ((PanelInteractorImpl) issueRecordingServiceSession.panelInteractor).collapsePanels();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0035  */
    @Override // com.android.systemui.screenrecord.RecordingService, android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int onStartCommand(Intent intent, int i, int i2) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("handling action: ", intent != null ? intent.getAction() : null, "IssueRecordingService");
        String action = intent != null ? intent.getAction() : null;
        if (action != null) {
            switch (action.hashCode()) {
                case -1688140755:
                    if (action.equals("com.android.systemui.screenrecord.SHARE")) {
                        IssueRecordingServiceSession issueRecordingServiceSession = this.session;
                        issueRecordingServiceSession.bgExecutor.execute(new IssueRecordingServiceSession$share$1(issueRecordingServiceSession, intent.getIntExtra("notification_id", this.mNotificationId), (Uri) intent.getParcelableExtra("extra_path", Uri.class)));
                        issueRecordingServiceSession.dialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                        ((PanelInteractorImpl) issueRecordingServiceSession.panelInteractor).collapsePanels();
                        return 1;
                    }
                    break;
                case -1687783248:
                    if (action.equals("com.android.systemui.screenrecord.START")) {
                        boolean booleanExtra = intent.getBooleanExtra("extra_screenRecord", false);
                        final IssueRecordingServiceSession issueRecordingServiceSession2 = this.session;
                        TraceConfig defaultConfig = (TraceConfig) intent.getParcelableExtra("com.android.traceur.trace_type", TraceConfig.class);
                        if (defaultConfig == null) {
                            defaultConfig = PresetTraceConfigs.getDefaultConfig();
                        }
                        issueRecordingServiceSession2.traceConfig = defaultConfig;
                        issueRecordingServiceSession2.takeBugReport = intent.getBooleanExtra("extra_bugReport", false);
                        issueRecordingServiceSession2.screenRecord = booleanExtra;
                        issueRecordingServiceSession2.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.recordissue.IssueRecordingServiceSession$start$1
                            @Override // java.lang.Runnable
                            public final void run() throws RemoteException {
                                IssueRecordingServiceSession issueRecordingServiceSession3 = issueRecordingServiceSession2;
                                TraceurConnection traceurConnection = issueRecordingServiceSession3.traceurConnection;
                                TraceConfig traceConfig = issueRecordingServiceSession3.traceConfig;
                                traceurConnection.getClass();
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("com.android.traceur.trace_type", traceConfig);
                                TraceurConnection.sendMessage$default(traceurConnection, 0, bundle, null, 4);
                                IssueRecordingState issueRecordingState = issueRecordingServiceSession2.issueRecordingState;
                                issueRecordingState.globalSettings.putInt("issueRecordingOngoing", 1);
                                issueRecordingState.isRecording = true;
                            }
                        });
                        if (!booleanExtra) {
                            return super.onStartCommand(new Intent("com.android.systemui.screenrecord.START_NOTIF"), i, i2);
                        }
                    }
                    break;
                case -470086188:
                    if (action.equals("com.android.systemui.screenrecord.STOP")) {
                        final IssueRecordingServiceSession issueRecordingServiceSession3 = this.session;
                        issueRecordingServiceSession3.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.recordissue.IssueRecordingServiceSession$stop$1
                            @Override // java.lang.Runnable
                            public final void run() throws RemoteException {
                                IssueRecordingServiceSession issueRecordingServiceSession4 = issueRecordingServiceSession3;
                                if (issueRecordingServiceSession4.traceConfig.longTrace) {
                                    Settings.Global.putInt(((UserTrackerImpl) issueRecordingServiceSession4.userContextProvider).getUserContext().getContentResolver(), "should_notify_trace_session_ended", 0);
                                }
                                TraceurConnection traceurConnection = issueRecordingServiceSession3.traceurConnection;
                                traceurConnection.getClass();
                                TraceurConnection.sendMessage$default(traceurConnection, 1, null, null, 6);
                                IssueRecordingState issueRecordingState = issueRecordingServiceSession3.issueRecordingState;
                                issueRecordingState.globalSettings.putInt("issueRecordingOngoing", 0);
                                issueRecordingState.isRecording = false;
                            }
                        });
                        break;
                    }
                    break;
                case -288359034:
                    if (action.equals("com.android.systemui.screenrecord.STOP_FROM_NOTIF")) {
                    }
                    break;
            }
        }
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        this.traceurConnection.doUnBind();
        return super.onUnbind(intent);
    }

    @Override // com.android.systemui.screenrecord.RecordingService
    public final RecordingServiceStrings provideRecordingServiceStrings() {
        return new IrsStrings(getResources());
    }
}
