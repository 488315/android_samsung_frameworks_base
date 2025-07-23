package com.android.systemui.recordissue;

import android.net.Uri;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IssueRecordingServiceSession$share$1 implements Runnable {
    public final /* synthetic */ int $notificationId;
    public final /* synthetic */ Uri $screenRecording;
    public final /* synthetic */ IssueRecordingServiceSession this$0;

    public IssueRecordingServiceSession$share$1(IssueRecordingServiceSession issueRecordingServiceSession, int i, Uri uri) {
        this.this$0 = issueRecordingServiceSession;
        this.$notificationId = i;
        this.$screenRecording = uri;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0094  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r8 = this;
            r0 = 1
            com.android.systemui.recordissue.IssueRecordingServiceSession r1 = r8.this$0
            android.app.NotificationManager r1 = r1.notificationManager
            int r2 = r8.$notificationId
            android.os.UserHandle r3 = new android.os.UserHandle
            com.android.systemui.recordissue.IssueRecordingServiceSession r4 = r8.this$0
            com.android.systemui.settings.UserContextProvider r4 = r4.userContextProvider
            com.android.systemui.settings.UserTrackerImpl r4 = (com.android.systemui.settings.UserTrackerImpl) r4
            android.content.Context r4 = r4.getUserContext()
            int r4 = r4.getUserId()
            r3.<init>(r4)
            r4 = 0
            r1.cancelAsUser(r4, r2, r3)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            android.net.Uri r2 = r8.$screenRecording
            com.android.systemui.recordissue.IssueRecordingServiceSession r3 = r8.this$0
            if (r2 == 0) goto L2c
            r1.add(r2)
        L2c:
            com.android.traceur.TraceConfig r2 = r3.traceConfig
            boolean r2 = r2.winscope
            if (r2 == 0) goto L97
            boolean r2 = r3.screenRecord
            if (r2 == 0) goto L97
            com.android.systemui.settings.UserContextProvider r2 = r3.userContextProvider
            com.android.systemui.settings.UserTrackerImpl r2 = (com.android.systemui.settings.UserTrackerImpl) r2
            android.content.Context r2 = r2.getUserContext()
            com.android.systemui.recordissue.ScreenRecordingStartTimeStore r3 = r3.startTimeStore
            r3.getClass()
            java.io.File r5 = r2.getExternalCacheDir()
            if (r5 == 0) goto L88
            r5.mkdirs()
            java.io.File r6 = new java.io.File     // Catch: java.lang.Exception -> L7f
            java.lang.String r7 = "screen_recording_metadata.json"
            r6.<init>(r5, r7)     // Catch: java.lang.Exception -> L7f
            android.util.SparseArray r5 = r3.userIdToScreenRecordingStartTime     // Catch: java.lang.Exception -> L7f
            com.android.systemui.settings.UserTracker r3 = r3.userTracker     // Catch: java.lang.Exception -> L7f
            com.android.systemui.settings.UserTrackerImpl r3 = (com.android.systemui.settings.UserTrackerImpl) r3     // Catch: java.lang.Exception -> L7f
            int r3 = r3.getUserId()     // Catch: java.lang.Exception -> L7f
            java.lang.Object r3 = r5.get(r3)     // Catch: java.lang.Exception -> L7f
            org.json.JSONObject r3 = (org.json.JSONObject) r3     // Catch: java.lang.Exception -> L7f
            if (r3 == 0) goto L88
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Exception -> L7f
            java.nio.charset.Charset r5 = kotlin.text.Charsets.UTF_8     // Catch: java.lang.Exception -> L7f
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch: java.lang.Exception -> L7f
            r7.<init>(r6)     // Catch: java.lang.Exception -> L7f
            kotlin.io.FilesKt__FileReadWriteKt.writeTextImpl(r7, r3, r5)     // Catch: java.lang.Throwable -> L81
            kotlin.Unit r3 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L81
            r7.close()     // Catch: java.lang.Exception -> L7f
            java.lang.String r3 = "com.android.systemui.fileprovider"
            android.net.Uri r2 = androidx.core.content.FileProvider.getUriForFile(r2, r3, r6)     // Catch: java.lang.Exception -> L7f
            goto L92
        L7f:
            r2 = move-exception
            goto L8a
        L81:
            r2 = move-exception
            throw r2     // Catch: java.lang.Throwable -> L83
        L83:
            r3 = move-exception
            kotlin.io.CloseableKt.closeFinally(r7, r2)     // Catch: java.lang.Exception -> L7f
            throw r3     // Catch: java.lang.Exception -> L7f
        L88:
            r2 = r4
            goto L92
        L8a:
            java.lang.String r3 = "ScreenRecordingStartTimeStore"
            java.lang.String r5 = "failed to get screen recording start time metadata via file uri"
            android.util.Log.e(r3, r5, r2)
            goto L88
        L92:
            if (r2 == 0) goto L97
            r1.add(r2)
        L97:
            com.android.systemui.recordissue.IssueRecordingServiceSession r2 = r8.this$0
            boolean r3 = r2.takeBugReport
            if (r3 == 0) goto Lc1
            int r3 = r1.size()
            r4 = 0
        La2:
            if (r4 >= r3) goto Lb9
            java.lang.Object r5 = r1.get(r4)
            int r4 = r4 + r0
            android.net.Uri r5 = (android.net.Uri) r5
            com.android.systemui.settings.UserContextProvider r6 = r2.userContextProvider
            com.android.systemui.settings.UserTrackerImpl r6 = (com.android.systemui.settings.UserTrackerImpl) r6
            android.content.Context r6 = r6.getUserContext()
            java.lang.String r7 = "com.android.shell"
            r6.grantUriPermission(r7, r5, r0)
            goto La2
        Lb9:
            com.android.systemui.recordissue.IssueRecordingServiceSession r8 = r8.this$0
            android.app.IActivityManager r8 = r8.iActivityManager
            r8.requestBugReportWithExtraAttachments(r1)
            goto Ld8
        Lc1:
            com.android.systemui.recordissue.TraceurConnection r8 = r2.traceurConnection
            r8.getClass()
            android.os.Messenger r0 = new android.os.Messenger
            com.android.systemui.recordissue.ShareFilesHandler r2 = new com.android.systemui.recordissue.ShareFilesHandler
            com.android.systemui.settings.UserContextProvider r3 = r8.userContextProvider
            android.os.Looper r5 = r8.bgLooper
            r2.<init>(r1, r3, r5)
            r0.<init>(r2)
            r1 = 2
            com.android.systemui.recordissue.TraceurConnection.sendMessage$default(r8, r1, r4, r0, r1)
        Ld8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.recordissue.IssueRecordingServiceSession$share$1.run():void");
    }
}
