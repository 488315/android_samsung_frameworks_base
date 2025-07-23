package com.android.systemui.recordissue;

import android.app.IActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
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
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IssueRecordingService extends RecordingService {
    public static final Companion Companion = new Companion(null);
    public final Executor bgExecutor;
    public final IssueRecordingServiceSession session;
    public final TraceurConnection traceurConnection;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0027, code lost:
    
        if (r0.equals("com.android.systemui.screenrecord.STOP_FROM_NOTIF") == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0035, code lost:
    
        r0 = r5.session;
        r0.bgExecutor.execute(new com.android.systemui.recordissue.IssueRecordingServiceSession$stop$1(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0031, code lost:
    
        if (r0.equals("com.android.systemui.screenrecord.STOP") == false) goto L35;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.android.systemui.screenrecord.RecordingService, android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onStartCommand(android.content.Intent r6, int r7, int r8) {
        /*
            r5 = this;
            r0 = 0
            if (r6 == 0) goto L8
            java.lang.String r1 = r6.getAction()
            goto L9
        L8:
            r1 = r0
        L9:
            java.lang.String r2 = "handling action: "
            java.lang.String r3 = "IssueRecordingService"
            android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r2, r1, r3)
            if (r6 == 0) goto L16
            java.lang.String r0 = r6.getAction()
        L16:
            if (r0 == 0) goto Lbe
            int r1 = r0.hashCode()
            switch(r1) {
                case -1688140755: goto L89;
                case -1687783248: goto L43;
                case -470086188: goto L2b;
                case -288359034: goto L21;
                default: goto L1f;
            }
        L1f:
            goto Lbe
        L21:
            java.lang.String r1 = "com.android.systemui.screenrecord.STOP_FROM_NOTIF"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L35
            goto Lbe
        L2b:
            java.lang.String r1 = "com.android.systemui.screenrecord.STOP"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L35
            goto Lbe
        L35:
            com.android.systemui.recordissue.IssueRecordingServiceSession r0 = r5.session
            java.util.concurrent.Executor r1 = r0.bgExecutor
            com.android.systemui.recordissue.IssueRecordingServiceSession$stop$1 r2 = new com.android.systemui.recordissue.IssueRecordingServiceSession$stop$1
            r2.<init>()
            r1.execute(r2)
            goto Lbe
        L43:
            java.lang.String r1 = "com.android.systemui.screenrecord.START"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L4c
            goto Lbe
        L4c:
            java.lang.String r0 = "extra_screenRecord"
            r1 = 0
            boolean r0 = r6.getBooleanExtra(r0, r1)
            com.android.systemui.recordissue.IssueRecordingServiceSession r2 = r5.session
            java.lang.String r3 = "com.android.traceur.trace_type"
            java.lang.Class<com.android.traceur.TraceConfig> r4 = com.android.traceur.TraceConfig.class
            java.lang.Object r3 = r6.getParcelableExtra(r3, r4)
            com.android.traceur.TraceConfig r3 = (com.android.traceur.TraceConfig) r3
            if (r3 != 0) goto L65
            com.android.traceur.TraceConfig r3 = com.android.traceur.PresetTraceConfigs.getDefaultConfig()
        L65:
            r2.traceConfig = r3
            java.lang.String r3 = "extra_bugReport"
            boolean r1 = r6.getBooleanExtra(r3, r1)
            r2.takeBugReport = r1
            r2.screenRecord = r0
            java.util.concurrent.Executor r1 = r2.bgExecutor
            com.android.systemui.recordissue.IssueRecordingServiceSession$start$1 r3 = new com.android.systemui.recordissue.IssueRecordingServiceSession$start$1
            r3.<init>()
            r1.execute(r3)
            if (r0 != 0) goto Lbe
            android.content.Intent r6 = new android.content.Intent
            java.lang.String r0 = "com.android.systemui.screenrecord.START_NOTIF"
            r6.<init>(r0)
            int r5 = super.onStartCommand(r6, r7, r8)
            return r5
        L89:
            java.lang.String r1 = "com.android.systemui.screenrecord.SHARE"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L92
            goto Lbe
        L92:
            com.android.systemui.recordissue.IssueRecordingServiceSession r7 = r5.session
            java.lang.String r8 = "notification_id"
            int r5 = r5.mNotificationId
            int r5 = r6.getIntExtra(r8, r5)
            java.lang.String r8 = "extra_path"
            java.lang.Class<android.net.Uri> r0 = android.net.Uri.class
            java.lang.Object r6 = r6.getParcelableExtra(r8, r0)
            android.net.Uri r6 = (android.net.Uri) r6
            java.util.concurrent.Executor r8 = r7.bgExecutor
            com.android.systemui.recordissue.IssueRecordingServiceSession$share$1 r0 = new com.android.systemui.recordissue.IssueRecordingServiceSession$share$1
            r0.<init>(r7, r5, r6)
            r8.execute(r0)
            com.android.systemui.animation.DialogTransitionAnimator r5 = r7.dialogTransitionAnimator
            r5.disableAllCurrentDialogsExitAnimations()
            com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor r5 = r7.panelInteractor
            com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl r5 = (com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl) r5
            r5.collapsePanels()
            r5 = 1
            return r5
        Lbe:
            int r5 = super.onStartCommand(r6, r7, r8)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.recordissue.IssueRecordingService.onStartCommand(android.content.Intent, int, int):int");
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
