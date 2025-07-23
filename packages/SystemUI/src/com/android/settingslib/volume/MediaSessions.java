package com.android.settingslib.volume;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Slog;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.volume.VolumeDialogControllerImpl;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MediaSessions {
    public static final String TAG;
    public final Callbacks mCallbacks;
    public final Context mContext;
    public final H mHandler;
    public final HandlerExecutor mHandlerExecutor;
    public boolean mInit;
    public final MediaSessionManager mMgr;
    public final Map mRecords;
    public final MediaSessions$mRemoteSessionCallback$1 mRemoteSessionCallback;
    public final MediaSessions$mSessionsListener$1 mSessionsListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callbacks {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int intValue;
            int i = message.what;
            if (i == 1) {
                MediaSessions mediaSessions = MediaSessions.this;
                MediaSessions.access$onActiveSessionsUpdatedH(mediaSessions, mediaSessions.mMgr.getActiveSessions(null));
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
                if (D.BUG) {
                    Log.d(MediaSessions.TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onUpdateRemoteSessionListH ", token != null ? new MediaController(mediaSessions2.mContext, token).getPackageName() : null));
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
            MediaController mediaController = new MediaController(mediaSessions3.mContext, token2);
            if (D.BUG) {
                MediaSessions$H$$ExternalSyntheticOutline0.m("remoteVolumeChangedH ", mediaController.getPackageName(), " ", Util.bitFieldToString(i2, Util.AUDIO_MANAGER_FLAG_NAMES, Util.AUDIO_MANAGER_FLAGS), MediaSessions.TAG);
            }
            MediaSession.Token sessionToken = mediaController.getSessionToken();
            Callbacks callbacks = mediaSessions3.mCallbacks;
            SessionId.Companion.getClass();
            SessionId.Media media = new SessionId.Media(sessionToken);
            VolumeDialogControllerImpl.MediaSessionsCallbacks mediaSessionsCallbacks = (VolumeDialogControllerImpl.MediaSessionsCallbacks) callbacks;
            mediaSessionsCallbacks.addStream(media, "onRemoteVolumeChanged");
            synchronized (mediaSessionsCallbacks.mRemoteStreams) {
                intValue = ((Integer) mediaSessionsCallbacks.mRemoteStreams.get(media)).intValue();
            }
            VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
            String str3 = VolumeDialogControllerImpl.TAG;
            boolean shouldShowUI = volumeDialogControllerImpl.shouldShowUI(i2);
            String str4 = VolumeDialogControllerImpl.TAG;
            Slog.d(str4, "onRemoteVolumeChanged: stream: " + intValue + " showui? " + shouldShowUI);
            VolumeDialogControllerImpl.m3196$$Nest$mupdateRemoteFixedVolumeSession(VolumeDialogControllerImpl.this, intValue, VolumeDialogControllerImpl.m3195$$Nest$mgetMediaControllerFromSessionId(VolumeDialogControllerImpl.this, media).getPlaybackInfo());
            boolean updateActiveStreamW = VolumeDialogControllerImpl.this.updateActiveStreamW(intValue);
            if (shouldShowUI) {
                updateActiveStreamW |= VolumeDialogControllerImpl.this.checkRoutedToBluetoothW(3);
            }
            if (updateActiveStreamW) {
                Slog.d(str4, "onRemoteChanged: updatingState");
                VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                volumeDialogControllerImpl2.mCallbacks.onStateChanged(volumeDialogControllerImpl2.mState);
            }
            if (shouldShowUI) {
                VolumeDialogControllerImpl volumeDialogControllerImpl3 = VolumeDialogControllerImpl.this;
                volumeDialogControllerImpl3.mCallbacks.onShowRequested(2, volumeDialogControllerImpl3.mKeyguardManager.isKeyguardLocked(), volumeDialogControllerImpl3.mActivityManager.getLockTaskModeState());
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MediaControllerRecord extends MediaController.Callback {
        public final MediaController controller;
        public String name;
        public boolean sentRemote;

        public MediaControllerRecord(MediaController mediaController) {
            this.controller = mediaController;
        }

        public final String cb(String str) {
            return str + " " + this.controller.getPackageName() + " ";
        }

        @Override // android.media.session.MediaController.Callback
        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onAudioInfoChanged") + Util.playbackInfoToString(playbackInfo) + " sentRemote=" + this.sentRemote);
            }
            boolean z = playbackInfo != null && playbackInfo.getPlaybackType() == 2;
            if (!z && this.sentRemote) {
                Callbacks callbacks = MediaSessions.this.mCallbacks;
                SessionId.Companion companion = SessionId.Companion;
                MediaSession.Token sessionToken = this.controller.getSessionToken();
                companion.getClass();
                ((VolumeDialogControllerImpl.MediaSessionsCallbacks) callbacks).onRemoteRemoved(new SessionId.Media(sessionToken));
                this.sentRemote = false;
                return;
            }
            if (z) {
                MediaSessions mediaSessions = MediaSessions.this;
                MediaSession.Token sessionToken2 = this.controller.getSessionToken();
                String str = this.name;
                String str2 = MediaSessions.TAG;
                mediaSessions.updateRemoteH(sessionToken2, str, playbackInfo);
                this.sentRemote = true;
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onExtrasChanged(Bundle bundle) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onExtrasChanged") + bundle);
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onMetadataChanged") + (mediaMetadata == null ? null : mediaMetadata.getDescription().toString()));
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onPlaybackStateChanged") + Util.playbackStateToString(playbackState));
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onQueueChanged(List list) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onQueueChanged") + list);
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onQueueTitleChanged(CharSequence charSequence) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onQueueTitleChanged") + ((Object) charSequence));
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onSessionDestroyed"));
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionEvent(String str, Bundle bundle) {
            if (D.BUG) {
                Log.d(MediaSessions.TAG, cb("onSessionEvent") + "event=" + str + " extras=" + bundle);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SessionId {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Media implements SessionId {
            public final MediaSession.Token token;

            public Media(MediaSession.Token token) {
                this.token = token;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Media) && Intrinsics.areEqual(this.token, ((Media) obj).token);
            }

            public final int hashCode() {
                return this.token.hashCode();
            }

            public final String toString() {
                return "Media(token=" + this.token + ")";
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class VolumeInfo {
        public static final Companion Companion = new Companion(null);
        public final int currentVolume;
        public final int maxVolume;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public VolumeInfo(int i, int i2) {
            this.currentVolume = i;
            this.maxVolume = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof VolumeInfo)) {
                return false;
            }
            VolumeInfo volumeInfo = (VolumeInfo) obj;
            return this.currentVolume == volumeInfo.currentVolume && this.maxVolume == volumeInfo.maxVolume;
        }

        public final int hashCode() {
            return Integer.hashCode(this.maxVolume) + (Integer.hashCode(this.currentVolume) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("VolumeInfo(currentVolume=");
            sb.append(this.currentVolume);
            sb.append(", maxVolume=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.maxVolume, ")", sb);
        }
    }

    static {
        new Companion(null);
        String concat = "vol.".concat("MediaSessions");
        if (concat.length() >= 23) {
            concat = concat.substring(0, 23);
        }
        TAG = concat;
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.settingslib.volume.MediaSessions$mSessionsListener$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.settingslib.volume.MediaSessions$mRemoteSessionCallback$1] */
    public MediaSessions(Context context, Looper looper, Callbacks callbacks) {
        this.mContext = context;
        H h = new H(looper);
        this.mHandler = h;
        this.mHandlerExecutor = new HandlerExecutor(h);
        this.mMgr = (MediaSessionManager) context.getSystemService("media_session");
        this.mRecords = new HashMap();
        this.mCallbacks = callbacks;
        this.mSessionsListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.settingslib.volume.MediaSessions$mSessionsListener$1
            @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
            public final void onActiveSessionsChanged(List list) {
                MediaSessions mediaSessions = MediaSessions.this;
                list.getClass();
                MediaSessions.access$onActiveSessionsUpdatedH(mediaSessions, list);
            }
        };
        this.mRemoteSessionCallback = new MediaSessionManager.RemoteSessionCallback() { // from class: com.android.settingslib.volume.MediaSessions$mRemoteSessionCallback$1
            public final void onDefaultRemoteSessionChanged(MediaSession.Token token) {
                MediaSessions.this.mHandler.obtainMessage(3, token).sendToTarget();
            }

            public final void onVolumeChanged(MediaSession.Token token, int i) {
                MediaSessions.this.mHandler.obtainMessage(2, i, 0, token).sendToTarget();
            }
        };
    }

    public static final void access$onActiveSessionsUpdatedH(MediaSessions mediaSessions, List list) {
        String obj;
        mediaSessions.getClass();
        boolean z = D.BUG;
        String str = TAG;
        if (z) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(list.size(), "onActiveSessionsUpdatedH n=", str);
        }
        HashSet hashSet = new HashSet(((HashMap) mediaSessions.mRecords).keySet());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MediaController mediaController = (MediaController) it.next();
            MediaSession.Token sessionToken = mediaController.getSessionToken();
            MediaController.PlaybackInfo playbackInfo = mediaController.getPlaybackInfo();
            hashSet.remove(sessionToken);
            if (!((HashMap) mediaSessions.mRecords).containsKey(sessionToken)) {
                MediaControllerRecord mediaControllerRecord = mediaSessions.new MediaControllerRecord(mediaController);
                PackageManager packageManager = mediaSessions.mContext.getPackageManager();
                String packageName = mediaController.getPackageName();
                try {
                    String objects = Objects.toString(packageManager.getApplicationInfo(packageName, 0).loadLabel(packageManager), "");
                    int length = objects.length() - 1;
                    int i = 0;
                    boolean z2 = false;
                    while (i <= length) {
                        boolean z3 = Intrinsics.compare(objects.charAt(!z2 ? i : length), 32) <= 0;
                        if (z2) {
                            if (!z3) {
                                break;
                            } else {
                                length--;
                            }
                        } else if (z3) {
                            i++;
                        } else {
                            z2 = true;
                        }
                    }
                    obj = objects.subSequence(i, length + 1).toString();
                } catch (PackageManager.NameNotFoundException unused) {
                }
                if (obj.length() > 0) {
                    packageName = obj;
                    mediaControllerRecord.name = packageName;
                    ((HashMap) mediaSessions.mRecords).put(sessionToken, mediaControllerRecord);
                    mediaController.registerCallback(mediaControllerRecord, mediaSessions.mHandler);
                }
                packageName.getClass();
                mediaControllerRecord.name = packageName;
                ((HashMap) mediaSessions.mRecords).put(sessionToken, mediaControllerRecord);
                mediaController.registerCallback(mediaControllerRecord, mediaSessions.mHandler);
            }
            MediaControllerRecord mediaControllerRecord2 = (MediaControllerRecord) ((HashMap) mediaSessions.mRecords).get(sessionToken);
            if (playbackInfo != null && playbackInfo.getPlaybackType() == 2) {
                mediaControllerRecord2.getClass();
                mediaSessions.updateRemoteH(sessionToken, mediaControllerRecord2.name, playbackInfo);
                mediaControllerRecord2.sentRemote = true;
            }
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            MediaSession.Token token = (MediaSession.Token) it2.next();
            Object obj2 = ((HashMap) mediaSessions.mRecords).get(token);
            obj2.getClass();
            MediaControllerRecord mediaControllerRecord3 = (MediaControllerRecord) obj2;
            mediaControllerRecord3.controller.unregisterCallback(mediaControllerRecord3);
            ((HashMap) mediaSessions.mRecords).remove(token);
            if (D.BUG) {
                Log.d(str, "Removing " + mediaControllerRecord3.name + " sentRemote=" + mediaControllerRecord3.sentRemote);
            }
            if (mediaControllerRecord3.sentRemote) {
                SessionId.Companion.getClass();
                ((VolumeDialogControllerImpl.MediaSessionsCallbacks) mediaSessions.mCallbacks).onRemoteRemoved(new SessionId.Media(token));
                mediaControllerRecord3.sentRemote = false;
            }
        }
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("MediaSessions state:");
        printWriter.print("  mInit: ");
        printWriter.println(this.mInit);
        printWriter.print("  mRecords.size: ");
        printWriter.println(((HashMap) this.mRecords).size());
        Iterator it = ((HashMap) this.mRecords).values().iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
            MediaController mediaController = ((MediaControllerRecord) it.next()).controller;
            printWriter.println("  Controller " + i + ": " + mediaController.getPackageName());
            String playbackStateToString = Util.playbackStateToString(mediaController.getPlaybackState());
            StringBuilder sb = new StringBuilder("    PlaybackState: ");
            sb.append(playbackStateToString);
            printWriter.println(sb.toString());
            printWriter.println("    PlaybackInfo: " + Util.playbackInfoToString(mediaController.getPlaybackInfo()));
            MediaMetadata metadata = mediaController.getMetadata();
            if (metadata != null) {
                printWriter.println("  MediaMetadata.desc=" + metadata.getDescription());
            }
            printWriter.println("    RatingType: " + mediaController.getRatingType());
            printWriter.println("    Flags: " + mediaController.getFlags());
            printWriter.println("    Extras:");
            Bundle extras = mediaController.getExtras();
            if (extras == null) {
                printWriter.println("      <null>");
            } else {
                for (String str : extras.keySet()) {
                    printWriter.println("      " + str + "=" + extras.getString(str));
                }
            }
            printWriter.println("    QueueTitle: " + ((Object) mediaController.getQueueTitle()));
            List<MediaSession.QueueItem> queue = mediaController.getQueue();
            List<MediaSession.QueueItem> list = queue;
            if (list != null && !list.isEmpty()) {
                printWriter.println("    Queue:");
                Iterator<MediaSession.QueueItem> it2 = queue.iterator();
                while (it2.hasNext()) {
                    printWriter.println("      " + it2.next());
                }
            }
            printWriter.println("    sessionActivity: " + mediaController.getSessionActivity());
        }
    }

    public final void init() {
        if (D.BUG) {
            Log.d(TAG, "init");
        }
        MediaSessionManager mediaSessionManager = this.mMgr;
        MediaSessions$mSessionsListener$1 mediaSessions$mSessionsListener$1 = this.mSessionsListener;
        H h = this.mHandler;
        mediaSessionManager.addOnActiveSessionsChangedListener(mediaSessions$mSessionsListener$1, null, h);
        this.mInit = true;
        h.sendEmptyMessage(1);
        this.mMgr.registerRemoteSessionCallback(this.mHandlerExecutor, this.mRemoteSessionCallback);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateRemoteH(android.media.session.MediaSession.Token r11, java.lang.String r12, android.media.session.MediaController.PlaybackInfo r13) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.volume.MediaSessions.updateRemoteH(android.media.session.MediaSession$Token, java.lang.String, android.media.session.MediaController$PlaybackInfo):void");
    }
}
