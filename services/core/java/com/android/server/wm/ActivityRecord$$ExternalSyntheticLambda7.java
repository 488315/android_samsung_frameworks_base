package com.android.server.wm;

import android.app.assist.ActivityId;
import android.app.assist.AssistContent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;

import com.android.server.LocalServices;
import com.android.server.contentcapture.ContentCaptureManagerService;
import com.android.server.contentcapture.ContentCapturePerUserService;

public final /* synthetic */ class ActivityRecord$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityRecord f$0;

    public /* synthetic */ ActivityRecord$$ExternalSyntheticLambda7(
            int i, ActivityRecord activityRecord) {
        this.$r8$classId = i;
        this.f$0 = activityRecord;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ActivityRecord activityRecord = this.f$0;
        switch (i) {
            case 0:
                WindowManagerGlobalLock windowManagerGlobalLock =
                        activityRecord.mAtmService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (activityRecord.hasProcess() && activityRecord.app.mRepProcState > 6) {
                            WindowProcessController windowProcessController = activityRecord.app;
                            WindowManagerService.resetPriorityAfterLockedSection();
                            activityRecord.mAtmService.mAmInternal.killProcess(
                                    windowProcessController.mName,
                                    windowProcessController.mUid,
                                    "resetConfig");
                            return;
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            default:
                activityRecord.getClass();
                ContentCaptureManagerService.LocalService localService =
                        (ContentCaptureManagerService.LocalService)
                                LocalServices.getService(
                                        ContentCaptureManagerService.LocalService.class);
                if (localService != null) {
                    int i2 = activityRecord.mUserId;
                    ComponentName componentName = activityRecord.mActivityComponent;
                    Task task = activityRecord.task;
                    localService.notifyActivityEvent(
                            i2,
                            componentName,
                            10000,
                            new ActivityId(
                                    task != null ? task.mTaskId : -1,
                                    activityRecord.shareableActivityToken));
                    int i3 = activityRecord.mUserId;
                    Binder binder = activityRecord.shareableActivityToken;
                    Intent intent = activityRecord.intent;
                    synchronized (ContentCaptureManagerService.this.mLock) {
                        try {
                            if (ContentCaptureManagerService.this.activityStartAssistDataEnabled) {
                                Intent intent2 = new Intent(intent);
                                intent2.setFlags(intent2.getFlags() & (-67));
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("activity_start_assist_content", true);
                                AssistContent assistContent = new AssistContent(bundle);
                                assistContent.setDefaultIntent(intent2);
                                Bundle bundle2 = new Bundle();
                                bundle2.putParcelable("content", assistContent);
                                ContentCapturePerUserService contentCapturePerUserService =
                                        (ContentCapturePerUserService)
                                                ContentCaptureManagerService.this
                                                        .peekServiceForUserLocked(i3);
                                if (contentCapturePerUserService != null) {
                                    contentCapturePerUserService.sendActivityAssistDataLocked(
                                            binder, bundle2);
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                }
                return;
        }
    }
}
