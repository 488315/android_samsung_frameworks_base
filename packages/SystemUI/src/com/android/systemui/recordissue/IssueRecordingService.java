package com.android.systemui.recordissue;

import android.app.IActivityManager;
import android.app.NotificationManager;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.RecordingService;
import com.android.systemui.screenrecord.RecordingServiceStrings;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class IssueRecordingService extends RecordingService {
    public static final Companion Companion = new Companion(null);
    public final Executor bgExecutor;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final IActivityManager iActivityManager;
    public final IssueRecordingState issueRecordingState;
    public final PanelInteractor panelInteractor;
    public final TraceurMessageSender traceurMessageSender;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public IssueRecordingService(RecordingController recordingController, Executor executor, Handler handler, UiEventLogger uiEventLogger, NotificationManager notificationManager, UserContextProvider userContextProvider, KeyguardDismissUtil keyguardDismissUtil, DialogTransitionAnimator dialogTransitionAnimator, PanelInteractor panelInteractor, TraceurMessageSender traceurMessageSender, IssueRecordingState issueRecordingState, IActivityManager iActivityManager) {
        super(recordingController, executor, handler, uiEventLogger, notificationManager, userContextProvider, keyguardDismissUtil);
        this.bgExecutor = executor;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.panelInteractor = panelInteractor;
        this.traceurMessageSender = traceurMessageSender;
        this.issueRecordingState = issueRecordingState;
        this.iActivityManager = iActivityManager;
    }

    @Override // com.android.systemui.screenrecord.RecordingService
    public final String getChannelId() {
        return "issue_record";
    }

    @Override // com.android.systemui.screenrecord.RecordingService
    public final String getTag() {
        return "IssueRecordingService";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0041, code lost:
    
        if (r0.hasNext() == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0043, code lost:
    
        ((java.lang.Runnable) r0.next()).run();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0025, code lost:
    
        if (r0.equals("com.android.systemui.screenrecord.STOP") == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x001b, code lost:
    
        if (r0.equals("com.android.systemui.screenrecord.STOP_FROM_NOTIF") == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0029, code lost:
    
        r4.bgExecutor.execute(new com.android.systemui.recordissue.IssueRecordingService$onStartCommand$2(r4));
        r0 = r4.issueRecordingState;
        r0.isRecording = false;
        r0 = r0.listeners.iterator();
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.android.systemui.screenrecord.RecordingService, android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onStartCommand(final android.content.Intent r5, int r6, int r7) {
        /*
            r4 = this;
            if (r5 == 0) goto L7
            java.lang.String r0 = r5.getAction()
            goto L8
        L7:
            r0 = 0
        L8:
            if (r0 == 0) goto Lb0
            int r1 = r0.hashCode()
            r2 = 0
            r3 = 1
            switch(r1) {
                case -1688140755: goto L92;
                case -1687783248: goto L4d;
                case -470086188: goto L1f;
                case -288359034: goto L15;
                default: goto L13;
            }
        L13:
            goto Lb0
        L15:
            java.lang.String r1 = "com.android.systemui.screenrecord.STOP_FROM_NOTIF"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L29
            goto Lb0
        L1f:
            java.lang.String r1 = "com.android.systemui.screenrecord.STOP"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L29
            goto Lb0
        L29:
            java.util.concurrent.Executor r0 = r4.bgExecutor
            com.android.systemui.recordissue.IssueRecordingService$onStartCommand$2 r1 = new com.android.systemui.recordissue.IssueRecordingService$onStartCommand$2
            r1.<init>()
            r0.execute(r1)
            com.android.systemui.recordissue.IssueRecordingState r0 = r4.issueRecordingState
            r0.isRecording = r2
            java.util.concurrent.CopyOnWriteArrayList r0 = r0.listeners
            java.util.Iterator r0 = r0.iterator()
        L3d:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto Lb0
            java.lang.Object r1 = r0.next()
            java.lang.Runnable r1 = (java.lang.Runnable) r1
            r1.run()
            goto L3d
        L4d:
            java.lang.String r1 = "com.android.systemui.screenrecord.START"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L56
            goto Lb0
        L56:
            java.util.concurrent.Executor r0 = r4.bgExecutor
            com.android.systemui.recordissue.IssueRecordingService$onStartCommand$1 r1 = new com.android.systemui.recordissue.IssueRecordingService$onStartCommand$1
            r1.<init>()
            r0.execute(r1)
            com.android.systemui.recordissue.IssueRecordingState r0 = r4.issueRecordingState
            r0.isRecording = r3
            java.util.concurrent.CopyOnWriteArrayList r0 = r0.listeners
            java.util.Iterator r0 = r0.iterator()
        L6a:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L7a
            java.lang.Object r1 = r0.next()
            java.lang.Runnable r1 = (java.lang.Runnable) r1
            r1.run()
            goto L6a
        L7a:
            com.android.systemui.recordissue.IssueRecordingState r0 = r4.issueRecordingState
            android.content.SharedPreferences r0 = r0.prefs
            java.lang.String r1 = "key_recordScreen"
            boolean r0 = r0.getBoolean(r1, r2)
            if (r0 != 0) goto Lb0
            android.content.Intent r5 = new android.content.Intent
            java.lang.String r0 = "com.android.systemui.screenrecord.START_NOTIF"
            r5.<init>(r0)
            int r4 = super.onStartCommand(r5, r6, r7)
            return r4
        L92:
            java.lang.String r1 = "com.android.systemui.screenrecord.SHARE"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L9b
            goto Lb0
        L9b:
            java.util.concurrent.Executor r6 = r4.bgExecutor
            com.android.systemui.recordissue.IssueRecordingService$onStartCommand$3 r7 = new com.android.systemui.recordissue.IssueRecordingService$onStartCommand$3
            r7.<init>()
            r6.execute(r7)
            com.android.systemui.animation.DialogTransitionAnimator r5 = r4.dialogTransitionAnimator
            r5.disableAllCurrentDialogsExitAnimations()
            com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor r4 = r4.panelInteractor
            r4.collapsePanels()
            return r3
        Lb0:
            int r4 = super.onStartCommand(r5, r6, r7)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.recordissue.IssueRecordingService.onStartCommand(android.content.Intent, int, int):int");
    }

    @Override // com.android.systemui.screenrecord.RecordingService
    public final RecordingServiceStrings provideRecordingServiceStrings() {
        return new IrsStrings(getResources());
    }
}
