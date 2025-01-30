package com.android.settingslib.volume;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaMetadata;
import android.media.MediaRoute2Info;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.Slog;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.C3599D;
import com.android.systemui.volume.VolumeDialogControllerImpl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaSessions {
    public static final String TAG;
    public final Callbacks mCallbacks;
    public final Context mContext;
    public final HandlerC0937H mHandler;
    public final HandlerExecutor mHandlerExecutor;
    public boolean mInit;
    public final MediaSessionManager mMgr;
    public final Map mRecords = new HashMap();
    public final C09351 mSessionsListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.settingslib.volume.MediaSessions.1
        @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
        public final void onActiveSessionsChanged(List list) {
            MediaSessions.this.onActiveSessionsUpdatedH(list);
        }
    };
    public final C09362 mRemoteSessionCallback = new MediaSessionManager.RemoteSessionCallback() { // from class: com.android.settingslib.volume.MediaSessions.2
        public final void onDefaultRemoteSessionChanged(MediaSession.Token token) {
            MediaSessions.this.mHandler.obtainMessage(3, token).sendToTarget();
        }

        public final void onVolumeChanged(MediaSession.Token token, int i) {
            MediaSessions.this.mHandler.obtainMessage(2, i, 0, token).sendToTarget();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callbacks {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.settingslib.volume.MediaSessions$H */
    public final class HandlerC0937H extends Handler {
        public /* synthetic */ HandlerC0937H(MediaSessions mediaSessions, Looper looper, int i) {
            this(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int intValue;
            int i = message.what;
            if (i == 1) {
                MediaSessions mediaSessions = MediaSessions.this;
                mediaSessions.onActiveSessionsUpdatedH(mediaSessions.mMgr.getActiveSessions(null));
                return;
            }
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                MediaSessions mediaSessions2 = MediaSessions.this;
                MediaSession.Token token = (MediaSession.Token) message.obj;
                String str = MediaSessions.TAG;
                mediaSessions2.getClass();
                MediaController mediaController = token != null ? new MediaController(mediaSessions2.mContext, token) : null;
                String packageName = mediaController != null ? mediaController.getPackageName() : null;
                if (C0934D.BUG) {
                    Log.d(MediaSessions.TAG, KeyAttributes$$ExternalSyntheticOutline0.m21m("onUpdateRemoteSessionListH ", packageName));
                }
                if (mediaSessions2.mInit) {
                    mediaSessions2.mHandler.sendEmptyMessage(1);
                    return;
                }
                return;
            }
            MediaSessions mediaSessions3 = MediaSessions.this;
            MediaSession.Token token2 = (MediaSession.Token) message.obj;
            int i2 = message.arg1;
            String str2 = MediaSessions.TAG;
            mediaSessions3.getClass();
            MediaController mediaController2 = new MediaController(mediaSessions3.mContext, token2);
            if (C0934D.BUG) {
                String str3 = MediaSessions.TAG;
                StringBuilder sb = new StringBuilder("remoteVolumeChangedH ");
                sb.append(mediaController2.getPackageName());
                sb.append(" ");
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb, Util.bitFieldToString(i2, Util.AUDIO_MANAGER_FLAG_NAMES, Util.AUDIO_MANAGER_FLAGS), str3);
            }
            MediaSession.Token sessionToken = mediaController2.getSessionToken();
            VolumeDialogControllerImpl.MediaSessionsCallbacks mediaSessionsCallbacks = (VolumeDialogControllerImpl.MediaSessionsCallbacks) mediaSessions3.mCallbacks;
            if (mediaSessionsCallbacks.showForSession(sessionToken)) {
                mediaSessionsCallbacks.addStream(sessionToken, "onRemoteVolumeChanged");
                synchronized (mediaSessionsCallbacks.mRemoteStreams) {
                    intValue = ((Integer) mediaSessionsCallbacks.mRemoteStreams.get(sessionToken)).intValue();
                }
                VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                String str4 = VolumeDialogControllerImpl.TAG;
                boolean shouldShowUI = volumeDialogControllerImpl.shouldShowUI(i2);
                String str5 = VolumeDialogControllerImpl.TAG;
                Slog.d(str5, "onRemoteVolumeChanged: stream: " + intValue + " showui? " + shouldShowUI);
                if (sessionToken != null && sessionToken.getBinder() != null) {
                    VolumeDialogControllerImpl.m2728$$Nest$mupdateRemoteFixedVolumeSession(VolumeDialogControllerImpl.this, intValue, new MediaController(VolumeDialogControllerImpl.this.mContext, sessionToken).getPlaybackInfo());
                }
                boolean updateActiveStreamW = VolumeDialogControllerImpl.this.updateActiveStreamW(intValue);
                if (shouldShowUI) {
                    updateActiveStreamW |= VolumeDialogControllerImpl.this.checkRoutedToBluetoothW(3);
                }
                if (updateActiveStreamW) {
                    Slog.d(str5, "onRemoteChanged: updatingState");
                    VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                    volumeDialogControllerImpl2.mCallbacks.onStateChanged(volumeDialogControllerImpl2.mState);
                }
                if (shouldShowUI) {
                    VolumeDialogControllerImpl volumeDialogControllerImpl3 = VolumeDialogControllerImpl.this;
                    volumeDialogControllerImpl3.mCallbacks.onShowRequested(2, volumeDialogControllerImpl3.mKeyguardManager.isKeyguardLocked(), volumeDialogControllerImpl3.mActivityManager.getLockTaskModeState());
                }
            }
        }

        private HandlerC0937H(Looper looper) {
            super(looper);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaControllerRecord extends MediaController.Callback {
        public final MediaController controller;
        public String name;
        public boolean sentRemote;

        public /* synthetic */ MediaControllerRecord(MediaSessions mediaSessions, MediaController mediaController, int i) {
            this(mediaController);
        }

        /* renamed from: cb */
        public final String m91cb(String str) {
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, " ");
            m2m.append(this.controller.getPackageName());
            m2m.append(" ");
            return m2m.toString();
        }

        @Override // android.media.session.MediaController.Callback
        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            if (C0934D.BUG) {
                String str = MediaSessions.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append(m91cb("onAudioInfoChanged"));
                sb.append(Util.playbackInfoToString(playbackInfo));
                sb.append(" sentRemote=");
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.sentRemote, str);
            }
            String str2 = MediaSessions.TAG;
            boolean z = playbackInfo != null && playbackInfo.getPlaybackType() == 2;
            if (!z && this.sentRemote) {
                ((VolumeDialogControllerImpl.MediaSessionsCallbacks) MediaSessions.this.mCallbacks).onRemoteRemoved(this.controller.getSessionToken());
                this.sentRemote = false;
            } else if (z) {
                MediaSessions.this.updateRemoteH(this.controller.getSessionToken(), this.name, playbackInfo);
                this.sentRemote = true;
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onExtrasChanged(Bundle bundle) {
            if (C0934D.BUG) {
                Log.d(MediaSessions.TAG, m91cb("onExtrasChanged") + bundle);
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            if (C0934D.BUG) {
                String str = MediaSessions.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append(m91cb("onMetadataChanged"));
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb, mediaMetadata == null ? null : mediaMetadata.getDescription().toString(), str);
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            if (C0934D.BUG) {
                Log.d(MediaSessions.TAG, m91cb("onPlaybackStateChanged") + Util.playbackStateToString(playbackState));
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onQueueChanged(List list) {
            if (C0934D.BUG) {
                Log.d(MediaSessions.TAG, m91cb("onQueueChanged") + list);
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onQueueTitleChanged(CharSequence charSequence) {
            if (C0934D.BUG) {
                Log.d(MediaSessions.TAG, m91cb("onQueueTitleChanged") + ((Object) charSequence));
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            if (C0934D.BUG) {
                Log.d(MediaSessions.TAG, m91cb("onSessionDestroyed"));
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionEvent(String str, Bundle bundle) {
            if (C0934D.BUG) {
                Log.d(MediaSessions.TAG, m91cb("onSessionEvent") + "event=" + str + " extras=" + bundle);
            }
        }

        private MediaControllerRecord(MediaController mediaController) {
            this.controller = mediaController;
        }
    }

    static {
        String concat = "vol.".concat("MediaSessions");
        if (concat.length() >= 23) {
            concat = concat.substring(0, 23);
        }
        TAG = concat;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settingslib.volume.MediaSessions$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settingslib.volume.MediaSessions$2] */
    public MediaSessions(Context context, Looper looper, Callbacks callbacks) {
        this.mContext = context;
        HandlerC0937H handlerC0937H = new HandlerC0937H(this, looper, 0);
        this.mHandler = handlerC0937H;
        this.mHandlerExecutor = new HandlerExecutor(handlerC0937H);
        this.mMgr = (MediaSessionManager) context.getSystemService("media_session");
        this.mCallbacks = callbacks;
    }

    public final void onActiveSessionsUpdatedH(List list) {
        boolean z = C0934D.BUG;
        String str = TAG;
        if (z) {
            Log.d(str, "onActiveSessionsUpdatedH n=" + list.size());
        }
        HashMap hashMap = (HashMap) this.mRecords;
        HashSet hashSet = new HashSet(hashMap.keySet());
        Iterator it = list.iterator();
        while (true) {
            int i = 0;
            if (!it.hasNext()) {
                break;
            }
            MediaController mediaController = (MediaController) it.next();
            MediaSession.Token sessionToken = mediaController.getSessionToken();
            MediaController.PlaybackInfo playbackInfo = mediaController.getPlaybackInfo();
            hashSet.remove(sessionToken);
            if (!hashMap.containsKey(sessionToken)) {
                MediaControllerRecord mediaControllerRecord = new MediaControllerRecord(this, mediaController, i);
                PackageManager packageManager = this.mContext.getPackageManager();
                String packageName = mediaController.getPackageName();
                try {
                    String trim = Objects.toString(packageManager.getApplicationInfo(packageName, 0).loadLabel(packageManager), "").trim();
                    if (trim.length() > 0) {
                        packageName = trim;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
                mediaControllerRecord.name = packageName;
                hashMap.put(sessionToken, mediaControllerRecord);
                mediaController.registerCallback(mediaControllerRecord, this.mHandler);
            }
            MediaControllerRecord mediaControllerRecord2 = (MediaControllerRecord) hashMap.get(sessionToken);
            if (playbackInfo != null && playbackInfo.getPlaybackType() == 2) {
                i = 1;
            }
            if (i != 0) {
                updateRemoteH(sessionToken, mediaControllerRecord2.name, playbackInfo);
                mediaControllerRecord2.sentRemote = true;
            }
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            MediaSession.Token token = (MediaSession.Token) it2.next();
            MediaControllerRecord mediaControllerRecord3 = (MediaControllerRecord) hashMap.get(token);
            mediaControllerRecord3.controller.unregisterCallback(mediaControllerRecord3);
            hashMap.remove(token);
            if (C0934D.BUG) {
                StringBuilder sb = new StringBuilder("Removing ");
                sb.append(mediaControllerRecord3.name);
                sb.append(" sentRemote=");
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, mediaControllerRecord3.sentRemote, str);
            }
            if (mediaControllerRecord3.sentRemote) {
                ((VolumeDialogControllerImpl.MediaSessionsCallbacks) this.mCallbacks).onRemoteRemoved(token);
                mediaControllerRecord3.sentRemote = false;
            }
        }
    }

    public final void updateRemoteH(MediaSession.Token token, String str, MediaController.PlaybackInfo playbackInfo) {
        int intValue;
        MediaRoute2Info mediaRoute2Info;
        Callbacks callbacks = this.mCallbacks;
        if (callbacks != null) {
            VolumeDialogControllerImpl.MediaSessionsCallbacks mediaSessionsCallbacks = (VolumeDialogControllerImpl.MediaSessionsCallbacks) callbacks;
            if (mediaSessionsCallbacks.showForSession(token)) {
                mediaSessionsCallbacks.addStream(token, "onRemoteUpdate");
                synchronized (mediaSessionsCallbacks.mRemoteStreams) {
                    intValue = ((Integer) mediaSessionsCallbacks.mRemoteStreams.get(token)).intValue();
                }
                String str2 = VolumeDialogControllerImpl.TAG;
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("onRemoteUpdate: stream: ", intValue, " volume: ");
                m1m.append(playbackInfo.getCurrentVolume());
                Slog.d(str2, m1m.toString());
                VolumeDialogControllerImpl.m2728$$Nest$mupdateRemoteFixedVolumeSession(VolumeDialogControllerImpl.this, intValue, playbackInfo);
                boolean z = true;
                boolean z2 = VolumeDialogControllerImpl.this.mState.states.indexOfKey(intValue) < 0;
                VolumeDialogController.StreamState streamStateW = VolumeDialogControllerImpl.this.streamStateW(intValue);
                Iterator it = VolumeDialogControllerImpl.this.mRouter2Manager.getAllRoutes().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        mediaRoute2Info = null;
                        break;
                    }
                    mediaRoute2Info = (MediaRoute2Info) it.next();
                    if (mediaRoute2Info.getConnectionState() == 2 && !mediaRoute2Info.getFeatures().contains("android.media.route.feature.LOCAL_PLAYBACK")) {
                        break;
                    }
                }
                if (mediaRoute2Info != null) {
                    List<String> features = mediaRoute2Info.getFeatures();
                    if (features.contains("android.media.route.feature.REMOTE_PLAYBACK") || features.contains("android.media.route.feature.REMOTE_AUDIO_PLAYBACK")) {
                        VolumeDialogController.StreamState streamStateW2 = VolumeDialogControllerImpl.this.streamStateW(intValue);
                        if (!streamStateW2.remoteSpeaker) {
                            streamStateW2.remoteSpeaker = true;
                            if (C3599D.BUG) {
                                Log.d(VolumeDialogControllerImpl.TAG, LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("updateStreamRoutedToRemoteSpeaker stream=", intValue, " remoteSpeaker=true"));
                            }
                        }
                    }
                }
                streamStateW.dynamic = true;
                streamStateW.levelMin = 0;
                streamStateW.levelMax = playbackInfo.getMaxVolume();
                if (streamStateW.level != playbackInfo.getCurrentVolume()) {
                    streamStateW.level = playbackInfo.getCurrentVolume();
                    z2 = true;
                }
                if (Objects.equals(streamStateW.remoteLabel, str)) {
                    z = z2;
                } else {
                    streamStateW.name = -1;
                    streamStateW.remoteLabel = str;
                }
                if (z) {
                    String str3 = VolumeDialogControllerImpl.TAG;
                    StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("onRemoteUpdate: ", str, ": ");
                    m4m.append(streamStateW.level);
                    m4m.append(" of ");
                    RecyclerView$$ExternalSyntheticOutline0.m46m(m4m, streamStateW.levelMax, str3);
                    VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                    volumeDialogControllerImpl.mCallbacks.onStateChanged(volumeDialogControllerImpl.mState);
                }
            }
        }
    }
}
