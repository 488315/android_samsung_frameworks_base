package com.android.systemui.recordissue;

import android.content.Context;
import android.net.Uri;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import androidx.core.content.FileProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.leak.LeakReporter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.io.FilesKt__FileReadWriteKt;
import kotlin.text.Charsets;
import org.json.JSONObject;

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

    /* JADX WARN: Removed duplicated region for block: B:25:0x0089  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() throws IOException, RemoteException {
        Uri uriForFile;
        File file;
        JSONObject jSONObject;
        this.this$0.notificationManager.cancelAsUser(null, this.$notificationId, new UserHandle(((UserTrackerImpl) this.this$0.userContextProvider).getUserContext().getUserId()));
        ArrayList arrayList = new ArrayList();
        Uri uri = this.$screenRecording;
        IssueRecordingServiceSession issueRecordingServiceSession = this.this$0;
        if (uri != null) {
            arrayList.add(uri);
        }
        if (issueRecordingServiceSession.traceConfig.winscope && issueRecordingServiceSession.screenRecord) {
            Context userContext = ((UserTrackerImpl) issueRecordingServiceSession.userContextProvider).getUserContext();
            ScreenRecordingStartTimeStore screenRecordingStartTimeStore = issueRecordingServiceSession.startTimeStore;
            screenRecordingStartTimeStore.getClass();
            File externalCacheDir = userContext.getExternalCacheDir();
            if (externalCacheDir != null) {
                externalCacheDir.mkdirs();
                try {
                    file = new File(externalCacheDir, "screen_recording_metadata.json");
                    jSONObject = (JSONObject) screenRecordingStartTimeStore.userIdToScreenRecordingStartTime.get(((UserTrackerImpl) screenRecordingStartTimeStore.userTracker).getUserId());
                } catch (Exception e) {
                    Log.e("ScreenRecordingStartTimeStore", "failed to get screen recording start time metadata via file uri", e);
                }
                if (jSONObject != null) {
                    String string = jSONObject.toString();
                    Charset charset = Charsets.UTF_8;
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    try {
                        FilesKt__FileReadWriteKt.writeTextImpl(fileOutputStream, string, charset);
                        Unit unit = Unit.INSTANCE;
                        fileOutputStream.close();
                        uriForFile = FileProvider.getUriForFile(userContext, LeakReporter.FILEPROVIDER_AUTHORITY, file);
                    } finally {
                    }
                } else {
                    uriForFile = null;
                }
                if (uriForFile != null) {
                    arrayList.add(uriForFile);
                }
            }
        }
        IssueRecordingServiceSession issueRecordingServiceSession2 = this.this$0;
        if (!issueRecordingServiceSession2.takeBugReport) {
            TraceurConnection traceurConnection = issueRecordingServiceSession2.traceurConnection;
            traceurConnection.getClass();
            TraceurConnection.sendMessage$default(traceurConnection, 2, null, new Messenger(new ShareFilesHandler(arrayList, traceurConnection.userContextProvider, traceurConnection.bgLooper)), 2);
            return;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((UserTrackerImpl) issueRecordingServiceSession2.userContextProvider).getUserContext().grantUriPermission("com.android.shell", (Uri) obj, 1);
        }
        this.this$0.iActivityManager.requestBugReportWithExtraAttachments(arrayList);
    }
}
