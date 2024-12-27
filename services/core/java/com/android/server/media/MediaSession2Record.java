package com.android.server.media;

import android.app.ForegroundServiceDelegationOptions;
import android.app.Notification;
import android.media.MediaController2;
import android.media.Session2CommandGroup;
import android.media.Session2Token;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MediaSession2Record extends MediaSessionRecordImpl {
    public static final boolean DEBUG = Log.isLoggable("MediaSession2Record", 3);
    public final MediaController2 mController;
    public boolean mIsClosed;
    public boolean mIsConnected;
    public final Object mLock;
    public final int mPolicies;
    public final MediaSessionService mService;
    public final Session2Token mSessionToken;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Controller2Callback extends MediaController2.ControllerCallback {
        public Controller2Callback() {}

        @Override // android.media.MediaController2.ControllerCallback
        public final void onConnected(
                MediaController2 mediaController2, Session2CommandGroup session2CommandGroup) {
            MediaSession2Record mediaSession2Record;
            MediaSessionService mediaSessionService;
            if (MediaSession2Record.DEBUG) {
                Log.d(
                        "MediaSession2Record",
                        "connected to "
                                + MediaSession2Record.this.mSessionToken
                                + ", allowed="
                                + session2CommandGroup);
            }
            synchronized (MediaSession2Record.this.mLock) {
                mediaSession2Record = MediaSession2Record.this;
                mediaSession2Record.mIsConnected = true;
                mediaSessionService = mediaSession2Record.mService;
            }
            mediaSessionService.onSessionActiveStateChanged(mediaSession2Record, null);
        }

        @Override // android.media.MediaController2.ControllerCallback
        public final void onDisconnected(MediaController2 mediaController2) {
            MediaSession2Record mediaSession2Record;
            MediaSessionService mediaSessionService;
            if (MediaSession2Record.DEBUG) {
                Log.d(
                        "MediaSession2Record",
                        "disconnected from " + MediaSession2Record.this.mSessionToken);
            }
            synchronized (MediaSession2Record.this.mLock) {
                mediaSession2Record = MediaSession2Record.this;
                mediaSession2Record.mIsConnected = false;
                mediaSessionService = mediaSession2Record.mService;
            }
            synchronized (mediaSessionService.mLock) {
                mediaSessionService.destroySessionLocked(mediaSession2Record);
            }
        }

        @Override // android.media.MediaController2.ControllerCallback
        public final void onPlaybackActiveChanged(MediaController2 mediaController2, boolean z) {
            MediaSession2Record mediaSession2Record;
            MediaSessionService mediaSessionService;
            if (MediaSession2Record.DEBUG) {
                Log.d(
                        "MediaSession2Record",
                        "playback active changed, "
                                + MediaSession2Record.this.mSessionToken
                                + ", active="
                                + z);
            }
            synchronized (MediaSession2Record.this.mLock) {
                mediaSession2Record = MediaSession2Record.this;
                mediaSessionService = mediaSession2Record.mService;
            }
            mediaSessionService.onSessionPlaybackStateChanged(mediaSession2Record, z, null);
        }
    }

    public MediaSession2Record(
            Session2Token session2Token, MediaSessionService mediaSessionService, Looper looper) {
        Object obj = new Object();
        this.mLock = obj;
        synchronized (obj) {
            this.mSessionToken = session2Token;
            this.mService = mediaSessionService;
            this.mController =
                    new MediaController2.Builder(mediaSessionService.getContext(), session2Token)
                            .setControllerCallback(
                                    new HandlerExecutor(new Handler(looper)),
                                    new Controller2Callback())
                            .build();
            this.mPolicies = 0;
        }
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final boolean checkPlaybackActiveState(boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                z2 = (this.mIsConnected && this.mController.isPlaybackActive()) == z;
            } finally {
            }
        }
        return z2;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final void close() {
        synchronized (this.mLock) {
            this.mIsClosed = true;
            this.mController.close();
        }
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final void dump(PrintWriter printWriter, String str) {
        StringBuilder m =
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        Preconditions$$ExternalSyntheticOutline0.m(str, "uniqueId="),
                        this.mUniqueId,
                        printWriter,
                        str,
                        "token=");
        m.append(this.mSessionToken);
        printWriter.println(m.toString());
        printWriter.println(str + "controller=" + this.mController);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("  ");
        StringBuilder m2 =
                Preconditions$$ExternalSyntheticOutline0.m(sb.toString(), "playbackActive=");
        m2.append(this.mController.isPlaybackActive());
        printWriter.println(m2.toString());
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final void expireTempEngaged() {}

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final ForegroundServiceDelegationOptions getForegroundServiceDelegationOptions() {
        return null;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final String getPackageName() {
        return this.mSessionToken.getPackageName();
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final int getSessionPolicies() {
        int i;
        synchronized (this.mLock) {
            i = this.mPolicies;
        }
        return i;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final int getUid() {
        return this.mSessionToken.getUid();
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final int getUserId() {
        return UserHandle.getUserHandleForUid(this.mSessionToken.getUid()).getIdentifier();
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final boolean isActive() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsConnected;
        }
        return z;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final boolean isClosed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsClosed;
        }
        return z;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final boolean isLinkedToNotification(Notification notification) {
        return false;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public final boolean isSystemPriority() {
        return false;
    }

    public final String toString() {
        return this.mSessionToken.getPackageName()
                + "/"
                + this.mUniqueId
                + " (userId="
                + getUserId()
                + ")";
    }
}
