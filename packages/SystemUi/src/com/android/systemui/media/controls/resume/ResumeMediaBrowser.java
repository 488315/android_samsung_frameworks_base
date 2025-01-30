package com.android.systemui.media.controls.resume;

import android.content.ComponentName;
import android.content.Context;
import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ResumeMediaBrowser {
    public final MediaBrowserFactory mBrowserFactory;
    public final Callback mCallback;
    public final ComponentName mComponentName;
    public final Context mContext;
    public final ResumeMediaBrowserLogger mLogger;
    public MediaBrowser mMediaBrowser;
    public MediaController mMediaController;
    public final int mUserId;
    public final SessionDestroyCallback mMediaControllerCallback = new SessionDestroyCallback(this, 0);
    public final C18021 mSubscriptionCallback = new MediaBrowser.SubscriptionCallback() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowser.1
        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        public final void onChildrenLoaded(String str, List list) {
            ResumeMediaBrowser resumeMediaBrowser;
            MediaBrowser mediaBrowser;
            if (list.size() == 0) {
                Log.d("ResumeMediaBrowser", "No children found for " + ResumeMediaBrowser.this.mComponentName);
                Callback callback = ResumeMediaBrowser.this.mCallback;
                if (callback != null) {
                    callback.onError();
                }
            } else {
                MediaBrowser.MediaItem mediaItem = (MediaBrowser.MediaItem) list.get(0);
                MediaDescription description = mediaItem.getDescription();
                if (!mediaItem.isPlayable() || (mediaBrowser = (resumeMediaBrowser = ResumeMediaBrowser.this).mMediaBrowser) == null) {
                    Log.d("ResumeMediaBrowser", "Child found but not playable for " + ResumeMediaBrowser.this.mComponentName);
                    Callback callback2 = ResumeMediaBrowser.this.mCallback;
                    if (callback2 != null) {
                        callback2.onError();
                    }
                } else {
                    Callback callback3 = resumeMediaBrowser.mCallback;
                    if (callback3 != null) {
                        callback3.addTrack(description, mediaBrowser.getServiceComponent(), ResumeMediaBrowser.this);
                    }
                }
            }
            ResumeMediaBrowser.this.disconnect();
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        public final void onError(String str) {
            Log.d("ResumeMediaBrowser", "Subscribe error for " + ResumeMediaBrowser.this.mComponentName + ": " + str);
            Callback callback = ResumeMediaBrowser.this.mCallback;
            if (callback != null) {
                callback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        public final void onError(String str, Bundle bundle) {
            Log.d("ResumeMediaBrowser", "Subscribe error for " + ResumeMediaBrowser.this.mComponentName + ": " + str + ", options: " + bundle);
            Callback callback = ResumeMediaBrowser.this.mCallback;
            if (callback != null) {
                callback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }
    };
    public final C18032 mConnectionCallback = new MediaBrowser.ConnectionCallback() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowser.2
        @Override // android.media.browse.MediaBrowser.ConnectionCallback
        public final void onConnected() {
            Log.d("ResumeMediaBrowser", "Service connected for " + ResumeMediaBrowser.this.mComponentName);
            ResumeMediaBrowser.this.updateMediaController();
            if (ResumeMediaBrowser.this.isBrowserConnected()) {
                String root = ResumeMediaBrowser.this.mMediaBrowser.getRoot();
                if (!TextUtils.isEmpty(root)) {
                    Callback callback = ResumeMediaBrowser.this.mCallback;
                    if (callback != null) {
                        callback.onConnected();
                    }
                    ResumeMediaBrowser resumeMediaBrowser = ResumeMediaBrowser.this;
                    MediaBrowser mediaBrowser = resumeMediaBrowser.mMediaBrowser;
                    if (mediaBrowser != null) {
                        mediaBrowser.subscribe(root, resumeMediaBrowser.mSubscriptionCallback);
                        return;
                    }
                    return;
                }
            }
            Callback callback2 = ResumeMediaBrowser.this.mCallback;
            if (callback2 != null) {
                callback2.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }

        @Override // android.media.browse.MediaBrowser.ConnectionCallback
        public final void onConnectionFailed() {
            Log.d("ResumeMediaBrowser", "Connection failed for " + ResumeMediaBrowser.this.mComponentName);
            Callback callback = ResumeMediaBrowser.this.mCallback;
            if (callback != null) {
                callback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }

        @Override // android.media.browse.MediaBrowser.ConnectionCallback
        public final void onConnectionSuspended() {
            Log.d("ResumeMediaBrowser", "Connection suspended for " + ResumeMediaBrowser.this.mComponentName);
            Callback callback = ResumeMediaBrowser.this.mCallback;
            if (callback != null) {
                callback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SessionDestroyCallback extends MediaController.Callback {
        public /* synthetic */ SessionDestroyCallback(ResumeMediaBrowser resumeMediaBrowser, int i) {
            this();
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            ResumeMediaBrowser resumeMediaBrowser = ResumeMediaBrowser.this;
            ResumeMediaBrowserLogger resumeMediaBrowserLogger = resumeMediaBrowser.mLogger;
            boolean isBrowserConnected = resumeMediaBrowser.isBrowserConnected();
            ComponentName componentName = ResumeMediaBrowser.this.mComponentName;
            resumeMediaBrowserLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            ResumeMediaBrowserLogger$logSessionDestroyed$2 resumeMediaBrowserLogger$logSessionDestroyed$2 = new Function1() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowserLogger$logSessionDestroyed$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return "Session destroyed. Active browser = " + logMessage.getBool1() + ". Browser component = " + logMessage.getStr1() + ".";
                }
            };
            LogBuffer logBuffer = resumeMediaBrowserLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaBrowser", logLevel, resumeMediaBrowserLogger$logSessionDestroyed$2, null);
            obtain.setBool1(isBrowserConnected);
            obtain.setStr1(componentName.toShortString());
            logBuffer.commit(obtain);
            ResumeMediaBrowser.this.disconnect();
        }

        private SessionDestroyCallback() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.media.controls.resume.ResumeMediaBrowser$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.media.controls.resume.ResumeMediaBrowser$2] */
    public ResumeMediaBrowser(Context context, Callback callback, ComponentName componentName, MediaBrowserFactory mediaBrowserFactory, ResumeMediaBrowserLogger resumeMediaBrowserLogger, int i) {
        this.mContext = context;
        this.mCallback = callback;
        this.mComponentName = componentName;
        this.mBrowserFactory = mediaBrowserFactory;
        this.mLogger = resumeMediaBrowserLogger;
        this.mUserId = i;
    }

    public final void connectBrowser(MediaBrowser mediaBrowser, String str) {
        ResumeMediaBrowserLogger resumeMediaBrowserLogger = this.mLogger;
        resumeMediaBrowserLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ResumeMediaBrowserLogger$logConnection$2 resumeMediaBrowserLogger$logConnection$2 = new Function1() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowserLogger$logConnection$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m32m("Connecting browser for component ", logMessage.getStr1(), " due to ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = resumeMediaBrowserLogger.buffer;
        LogMessage obtain = logBuffer.obtain("MediaBrowser", logLevel, resumeMediaBrowserLogger$logConnection$2, null);
        obtain.setStr1(this.mComponentName.toShortString());
        obtain.setStr2(str);
        logBuffer.commit(obtain);
        disconnect();
        this.mMediaBrowser = mediaBrowser;
        mediaBrowser.connect();
        updateMediaController();
    }

    public MediaController createMediaController(MediaSession.Token token) {
        return new MediaController(this.mContext, token);
    }

    public final void disconnect() {
        if (this.mMediaBrowser != null) {
            ResumeMediaBrowserLogger resumeMediaBrowserLogger = this.mLogger;
            resumeMediaBrowserLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            ResumeMediaBrowserLogger$logDisconnect$2 resumeMediaBrowserLogger$logDisconnect$2 = new Function1() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowserLogger$logDisconnect$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("Disconnecting browser for component ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = resumeMediaBrowserLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaBrowser", logLevel, resumeMediaBrowserLogger$logDisconnect$2, null);
            obtain.setStr1(this.mComponentName.toShortString());
            logBuffer.commit(obtain);
            this.mMediaBrowser.disconnect();
        }
        this.mMediaBrowser = null;
        updateMediaController();
    }

    public final boolean isBrowserConnected() {
        MediaBrowser mediaBrowser = this.mMediaBrowser;
        return mediaBrowser != null && mediaBrowser.isConnected();
    }

    public final void updateMediaController() {
        MediaController mediaController = this.mMediaController;
        MediaSession.Token sessionToken = mediaController != null ? mediaController.getSessionToken() : null;
        MediaSession.Token sessionToken2 = !isBrowserConnected() ? null : this.mMediaBrowser.getSessionToken();
        if ((sessionToken == null && sessionToken2 == null) || (sessionToken != null && sessionToken.equals(sessionToken2))) {
            return;
        }
        MediaController mediaController2 = this.mMediaController;
        SessionDestroyCallback sessionDestroyCallback = this.mMediaControllerCallback;
        if (mediaController2 != null) {
            mediaController2.unregisterCallback(sessionDestroyCallback);
        }
        if (sessionToken2 == null) {
            this.mMediaController = null;
            return;
        }
        MediaController createMediaController = createMediaController(sessionToken2);
        this.mMediaController = createMediaController;
        createMediaController.registerCallback(sessionDestroyCallback);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Callback {
        public void onConnected() {
        }

        public void onError() {
        }

        public void addTrack(MediaDescription mediaDescription, ComponentName componentName, ResumeMediaBrowser resumeMediaBrowser) {
        }
    }
}
