package com.android.server.contentcapture;

import android.app.assist.ActivityId;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.contentcapture.ContentCaptureContext;

import com.android.internal.os.IResultReceiver;
import com.android.internal.util.Preconditions;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ContentCaptureServerSession {
    public final ComponentName appComponentName;
    public final IBinder mActivityToken;
    public final ContentCaptureContext mContentCaptureContext;
    public final int mId;
    public final Object mLock;
    public final ContentCapturePerUserService mService;
    public final IResultReceiver mSessionStateReceiver;
    public final int mUid;

    public ContentCaptureServerSession(
            Object obj,
            IBinder iBinder,
            ActivityId activityId,
            ContentCapturePerUserService contentCapturePerUserService,
            ComponentName componentName,
            IResultReceiver iResultReceiver,
            int i,
            int i2,
            int i3,
            int i4) {
        Preconditions.checkArgument(i2 != 0);
        this.mLock = obj;
        this.mActivityToken = iBinder;
        this.appComponentName = componentName;
        this.mService = contentCapturePerUserService;
        this.mId = i2;
        this.mUid = i3;
        this.mContentCaptureContext =
                new ContentCaptureContext(null, activityId, componentName, i, iBinder, i4);
        this.mSessionStateReceiver = iResultReceiver;
        try {
            iResultReceiver
                    .asBinder()
                    .linkToDeath(
                            new IBinder
                                    .DeathRecipient() { // from class:
                                                        // com.android.server.contentcapture.ContentCaptureServerSession$$ExternalSyntheticLambda0
                                @Override // android.os.IBinder.DeathRecipient
                                public final void binderDied() {
                                    ContentCaptureServerSession contentCaptureServerSession =
                                            ContentCaptureServerSession.this;
                                    if (contentCaptureServerSession.mService.mMaster.verbose) {
                                        StringBuilder sb = new StringBuilder("onClientDeath(");
                                        sb.append(contentCaptureServerSession.mActivityToken);
                                        sb.append("): removing session ");
                                        GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                                sb,
                                                contentCaptureServerSession.mId,
                                                "ContentCaptureServerSession");
                                    }
                                    synchronized (contentCaptureServerSession.mLock) {
                                        try {
                                            int i5 = contentCaptureServerSession.mId;
                                            ContentCapturePerUserService
                                                    contentCapturePerUserService2 =
                                                            contentCaptureServerSession.mService;
                                            try {
                                                contentCaptureServerSession.destroyLocked();
                                            } finally {
                                                contentCapturePerUserService2.mSessions.remove(i5);
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            },
                            0);
        } catch (Exception unused) {
            Slog.w(
                    "ContentCaptureServerSession",
                    "could not register DeathRecipient for " + iBinder);
        }
    }

    public final void destroyLocked() {
        ContentCapturePerUserService contentCapturePerUserService = this.mService;
        if (contentCapturePerUserService.mMaster.verbose) {
            Slog.v("ContentCaptureServerSession", "destroy(notifyRemoteService=true)");
        }
        RemoteContentCaptureService remoteContentCaptureService =
                contentCapturePerUserService.mRemoteService;
        if (remoteContentCaptureService == null) {
            Slog.w("ContentCaptureServerSession", "destroyLocked(): no remote service");
        } else {
            remoteContentCaptureService.onSessionFinished(this.mId);
        }
    }

    public final void setContentCaptureEnabledLocked(boolean z) {
        try {
            Bundle bundle = new Bundle();
            int i = 1;
            bundle.putBoolean("enabled", true);
            IResultReceiver iResultReceiver = this.mSessionStateReceiver;
            if (!z) {
                i = 2;
            }
            iResultReceiver.send(i, bundle);
        } catch (RemoteException e) {
            AccountManagerService$$ExternalSyntheticOutline0.m(
                    "Error async reporting result to client: ", e, "ContentCaptureServerSession");
        }
    }

    public final String toString() {
        return "ContentCaptureSession[id=" + this.mId + ", act=" + this.mActivityToken + "]";
    }
}
