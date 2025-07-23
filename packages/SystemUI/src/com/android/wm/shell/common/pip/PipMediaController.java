package com.android.wm.shell.common.pip;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.pip.PipTaskOrganizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PipMediaController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList mActionListeners;
    public final Context mContext;
    public final HandlerExecutor mHandlerExecutor;
    public Locale mLastLocale;
    public final Handler mMainHandler;
    public final PipMediaController$mMediaActionReceiver$1 mMediaActionReceiver;
    public MediaController mMediaController;
    public final MediaSessionManager mMediaSessionManager;
    public final ArrayList mMetadataListeners;
    public RemoteAction mNextAction;
    public RemoteAction mPauseAction;
    public RemoteAction mPlayAction;
    public final PipMediaController$mPlaybackChangedListener$1 mPlaybackChangedListener;
    public RemoteAction mPrevAction;
    public final PipMediaController$mSessionsChangedListener$1 mSessionsChangedListener;
    public final ArrayList mTokenListeners;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ActionListener {
        void onMediaActionsChanged(List list);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.common.pip.PipMediaController$mPlaybackChangedListener$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.common.pip.PipMediaController$mSessionsChangedListener$1] */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.BroadcastReceiver, com.android.wm.shell.common.pip.PipMediaController$mMediaActionReceiver$1] */
    public PipMediaController(Context context, Handler handler) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mHandlerExecutor = new HandlerExecutor(handler);
        ?? r2 = new BroadcastReceiver() { // from class: com.android.wm.shell.common.pip.PipMediaController$mMediaActionReceiver$1
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
            java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
             */
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action;
                if (PipMediaController.this.mMediaController == null || (action = intent.getAction()) == null) {
                    return;
                }
                switch (action.hashCode()) {
                    case 40376596:
                        if (action.equals("com.android.wm.shell.pip.NEXT")) {
                            MediaController mediaController = PipMediaController.this.mMediaController;
                            mediaController.getClass();
                            mediaController.getTransportControls().skipToNext();
                            break;
                        }
                        break;
                    case 40442197:
                        if (action.equals("com.android.wm.shell.pip.PLAY")) {
                            MediaController mediaController2 = PipMediaController.this.mMediaController;
                            mediaController2.getClass();
                            mediaController2.getTransportControls().play();
                            break;
                        }
                        break;
                    case 40448084:
                        if (action.equals("com.android.wm.shell.pip.PREV")) {
                            MediaController mediaController3 = PipMediaController.this.mMediaController;
                            mediaController3.getClass();
                            mediaController3.getTransportControls().skipToPrevious();
                            break;
                        }
                        break;
                    case 1253399509:
                        if (action.equals("com.android.wm.shell.pip.PAUSE")) {
                            MediaController mediaController4 = PipMediaController.this.mMediaController;
                            mediaController4.getClass();
                            mediaController4.getTransportControls().pause();
                            break;
                        }
                        break;
                }
            }
        };
        this.mMediaActionReceiver = r2;
        this.mPlaybackChangedListener = new MediaController.Callback() { // from class: com.android.wm.shell.common.pip.PipMediaController$mPlaybackChangedListener$1
            @Override // android.media.session.MediaController.Callback
            public final void onMetadataChanged(MediaMetadata mediaMetadata) {
                PipMediaController pipMediaController = PipMediaController.this;
                int i = PipMediaController.$r8$clinit;
                if (pipMediaController.mMetadataListeners.isEmpty()) {
                    return;
                }
                pipMediaController.mMetadataListeners.forEach(new PipMediaController$notifyMetadataChanged$1(mediaMetadata));
            }

            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                PipMediaController pipMediaController = PipMediaController.this;
                int i = PipMediaController.$r8$clinit;
                if (pipMediaController.mActionListeners.isEmpty()) {
                    return;
                }
                pipMediaController.mActionListeners.forEach(new PipMediaController$notifyActionsChanged$1(pipMediaController.getMediaActions()));
            }
        };
        this.mSessionsChangedListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.wm.shell.common.pip.PipMediaController$mSessionsChangedListener$1
            @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
            public final void onActiveSessionsChanged(List list) {
                PipMediaController pipMediaController = PipMediaController.this;
                int i = PipMediaController.$r8$clinit;
                pipMediaController.resolveActiveMediaController(list);
            }
        };
        this.mActionListeners = new ArrayList();
        this.mMetadataListeners = new ArrayList();
        this.mTokenListeners = new ArrayList();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.wm.shell.pip.PLAY");
        intentFilter.addAction("com.android.wm.shell.pip.PAUSE");
        intentFilter.addAction("com.android.wm.shell.pip.NEXT");
        intentFilter.addAction("com.android.wm.shell.pip.PREV");
        context.registerReceiverForAllUsers(r2, intentFilter, "com.android.systemui.permission.SELF", handler, 2);
        this.mPauseAction = getDefaultRemoteAction(R.string.pip_pause, R.drawable.pip_ic_pause_white, "com.android.wm.shell.pip.PAUSE");
        this.mPlayAction = getDefaultRemoteAction(R.string.pip_play, R.drawable.pip_ic_play_arrow_white, "com.android.wm.shell.pip.PLAY");
        this.mNextAction = getDefaultRemoteAction(R.string.pip_skip_to_next, R.drawable.pip_ic_skip_next_white, "com.android.wm.shell.pip.NEXT");
        this.mPrevAction = getDefaultRemoteAction(R.string.pip_skip_to_prev, R.drawable.pip_ic_skip_previous_white, "com.android.wm.shell.pip.PREV");
        this.mLastLocale = Locale.getDefault();
        this.mMediaSessionManager = (MediaSessionManager) context.getSystemService(MediaSessionManager.class);
    }

    public final RemoteAction getDefaultRemoteAction(int i, int i2, String str) {
        String string = this.mContext.getString(i);
        Intent intent = new Intent(str);
        intent.setPackage(this.mContext.getPackageName());
        return new RemoteAction(Icon.createWithResource(this.mContext, i2), string, string, PendingIntent.getBroadcast(this.mContext, 0, intent, 201326592));
    }

    public final List getMediaActions() {
        MediaController mediaController = this.mMediaController;
        if (mediaController == null) {
            int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("[PipMediaController] getMediaActions : emptyList, mMediaController=null caller=", Debug.getCallers(7), "PipTaskOrganizer");
            return EmptyList.INSTANCE;
        }
        mediaController.getClass();
        PlaybackState playbackState = mediaController.getPlaybackState();
        if (playbackState == null) {
            return EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        boolean isActive = playbackState.isActive();
        long actions = playbackState.getActions();
        int i2 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        Log.d("PipTaskOrganizer", "[PipMediaController] getMediaActions , isPlaying=" + isActive + " actions=" + actions);
        Locale locale = Locale.getDefault();
        if (!Intrinsics.areEqual(locale, this.mLastLocale)) {
            Log.d("PipTaskOrganizer", "[PipMediaController] recreate default actions last=" + this.mLastLocale + "cur=" + locale);
            this.mLastLocale = locale;
            this.mPauseAction = getDefaultRemoteAction(R.string.pip_pause, R.drawable.pip_ic_pause_white, "com.android.wm.shell.pip.PAUSE");
            this.mPlayAction = getDefaultRemoteAction(R.string.pip_play, R.drawable.pip_ic_play_arrow_white, "com.android.wm.shell.pip.PLAY");
            this.mNextAction = getDefaultRemoteAction(R.string.pip_skip_to_next, R.drawable.pip_ic_skip_next_white, "com.android.wm.shell.pip.NEXT");
            this.mPrevAction = getDefaultRemoteAction(R.string.pip_skip_to_prev, R.drawable.pip_ic_skip_previous_white, "com.android.wm.shell.pip.PREV");
        }
        this.mPrevAction.setEnabled((16 & actions) != 0);
        arrayList.add(this.mPrevAction);
        if (!isActive && (4 & actions) != 0) {
            arrayList.add(this.mPlayAction);
        } else if (isActive && (2 & actions) != 0) {
            arrayList.add(this.mPauseAction);
        }
        this.mNextAction.setEnabled((actions & 32) != 0);
        arrayList.add(this.mNextAction);
        return arrayList;
    }

    public final void resolveActiveMediaController(List list) {
        ComponentName componentName;
        if (list != null && (componentName = (ComponentName) PipUtils.getTopPipActivity(this.mContext).first) != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                MediaController mediaController = (MediaController) list.get(i);
                if (Intrinsics.areEqual(mediaController.getPackageName(), componentName.getPackageName())) {
                    setActiveMediaController(mediaController);
                    return;
                }
            }
        }
        setActiveMediaController(null);
    }

    public final void setActiveMediaController(MediaController mediaController) {
        if (Intrinsics.areEqual(mediaController, this.mMediaController)) {
            return;
        }
        MediaController mediaController2 = this.mMediaController;
        PipMediaController$mPlaybackChangedListener$1 pipMediaController$mPlaybackChangedListener$1 = this.mPlaybackChangedListener;
        if (mediaController2 != null) {
            mediaController2.unregisterCallback(pipMediaController$mPlaybackChangedListener$1);
        }
        this.mMediaController = mediaController;
        if (mediaController != null) {
            mediaController.registerCallback(pipMediaController$mPlaybackChangedListener$1, this.mMainHandler);
        }
        if (!this.mActionListeners.isEmpty()) {
            this.mActionListeners.forEach(new PipMediaController$notifyActionsChanged$1(getMediaActions()));
        }
        MediaController mediaController3 = this.mMediaController;
        MediaMetadata metadata = mediaController3 != null ? mediaController3.getMetadata() : null;
        if (!this.mMetadataListeners.isEmpty()) {
            this.mMetadataListeners.forEach(new PipMediaController$notifyMetadataChanged$1(metadata));
        }
        MediaController mediaController4 = this.mMediaController;
        final MediaSession.Token sessionToken = mediaController4 != null ? mediaController4.getSessionToken() : null;
        if (this.mTokenListeners.isEmpty()) {
            return;
        }
        this.mTokenListeners.forEach(new Consumer(sessionToken) { // from class: com.android.wm.shell.common.pip.PipMediaController$notifyTokenChanged$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                throw MediaControllerImplBase$$ExternalSyntheticOutline0.m(obj);
            }
        });
    }
}
