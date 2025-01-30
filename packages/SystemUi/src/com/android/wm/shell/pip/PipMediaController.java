package com.android.wm.shell.pip;

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
import android.util.Log;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipMediaController {
    public final ArrayList mActionListeners;
    public final Context mContext;
    public final HandlerExecutor mHandlerExecutor;
    public Locale mLastLocale;
    public final Handler mMainHandler;
    public final C40381 mMediaActionReceiver;
    public MediaController mMediaController;
    public final MediaSessionManager mMediaSessionManager;
    public final ArrayList mMetadataListeners;
    public RemoteAction mNextAction;
    public RemoteAction mPauseAction;
    public RemoteAction mPlayAction;
    public final C40392 mPlaybackChangedListener;
    public RemoteAction mPrevAction;
    public final PipMediaController$$ExternalSyntheticLambda0 mSessionsChangedListener;
    public final ArrayList mTokenListeners;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ActionListener {
        void onMediaActionsChanged(List list);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.pip.PipMediaController$2] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.pip.PipMediaController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.BroadcastReceiver, com.android.wm.shell.pip.PipMediaController$1] */
    public PipMediaController(Context context, Handler handler) {
        ?? r1 = new BroadcastReceiver() { // from class: com.android.wm.shell.pip.PipMediaController.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                MediaController mediaController = PipMediaController.this.mMediaController;
                if (mediaController == null || mediaController.getTransportControls() == null) {
                    return;
                }
                String action = intent.getAction();
                action.getClass();
                switch (action) {
                    case "com.android.wm.shell.pip.NEXT":
                        PipMediaController.this.mMediaController.getTransportControls().skipToNext();
                        break;
                    case "com.android.wm.shell.pip.PLAY":
                        PipMediaController.this.mMediaController.getTransportControls().play();
                        break;
                    case "com.android.wm.shell.pip.PREV":
                        PipMediaController.this.mMediaController.getTransportControls().skipToPrevious();
                        break;
                    case "com.android.wm.shell.pip.PAUSE":
                        PipMediaController.this.mMediaController.getTransportControls().pause();
                        break;
                }
            }
        };
        this.mMediaActionReceiver = r1;
        this.mPlaybackChangedListener = new MediaController.Callback() { // from class: com.android.wm.shell.pip.PipMediaController.2
            @Override // android.media.session.MediaController.Callback
            public final void onMetadataChanged(MediaMetadata mediaMetadata) {
                ArrayList arrayList = PipMediaController.this.mMetadataListeners;
                if (arrayList.isEmpty()) {
                    return;
                }
                arrayList.forEach(new PipMediaController$$ExternalSyntheticLambda1(mediaMetadata, 0));
            }

            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                PipMediaController pipMediaController = PipMediaController.this;
                ArrayList arrayList = pipMediaController.mActionListeners;
                if (arrayList.isEmpty()) {
                    return;
                }
                arrayList.forEach(new PipMediaController$$ExternalSyntheticLambda1(pipMediaController.getMediaActions(), 2));
            }
        };
        this.mSessionsChangedListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.wm.shell.pip.PipMediaController$$ExternalSyntheticLambda0
            @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
            public final void onActiveSessionsChanged(List list) {
                PipMediaController.this.resolveActiveMediaController(list);
            }
        };
        this.mActionListeners = new ArrayList();
        this.mMetadataListeners = new ArrayList();
        this.mTokenListeners = new ArrayList();
        this.mContext = context;
        this.mMainHandler = handler;
        this.mHandlerExecutor = new HandlerExecutor(handler);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.wm.shell.pip.PLAY");
        intentFilter.addAction("com.android.wm.shell.pip.PAUSE");
        intentFilter.addAction("com.android.wm.shell.pip.NEXT");
        intentFilter.addAction("com.android.wm.shell.pip.PREV");
        context.registerReceiverForAllUsers(r1, intentFilter, "com.android.systemui.permission.SELF", handler, 2);
        this.mPauseAction = getDefaultRemoteAction(R.string.pip_pause, R.drawable.pip_ic_pause_white, "com.android.wm.shell.pip.PAUSE");
        this.mPlayAction = getDefaultRemoteAction(R.string.pip_play, R.drawable.pip_ic_play_arrow_white, "com.android.wm.shell.pip.PLAY");
        this.mNextAction = getDefaultRemoteAction(R.string.pip_skip_to_next, R.drawable.pip_ic_skip_next_white, "com.android.wm.shell.pip.NEXT");
        this.mPrevAction = getDefaultRemoteAction(R.string.pip_skip_to_prev, R.drawable.pip_ic_skip_previous_white, "com.android.wm.shell.pip.PREV");
        this.mLastLocale = Locale.getDefault();
        this.mMediaSessionManager = (MediaSessionManager) context.getSystemService(MediaSessionManager.class);
    }

    public final RemoteAction getDefaultRemoteAction(int i, int i2, String str) {
        Context context = this.mContext;
        String string = context.getString(i);
        Intent intent = new Intent(str);
        intent.setPackage(context.getPackageName());
        return new RemoteAction(Icon.createWithResource(context, i2), string, string, PendingIntent.getBroadcast(context, 0, intent, 201326592));
    }

    public final List getMediaActions() {
        PlaybackState playbackState;
        MediaController mediaController = this.mMediaController;
        if (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null) {
            Log.d("PipTaskOrganizer", "[PipMediaController] getMediaActions : emptyList, mMediaController=" + this.mMediaController + " PlaybackState=null caller=" + Debug.getCallers(7));
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        boolean isActive = playbackState.isActive();
        long actions = playbackState.getActions();
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("[PipMediaController] getMediaActions , isPlaying=", isActive, " actions=");
        m49m.append(Long.toHexString(actions));
        Log.d("PipTaskOrganizer", m49m.toString());
        Locale locale = Locale.getDefault();
        if (!locale.equals(this.mLastLocale)) {
            Log.d("PipTaskOrganizer", "[PipMediaController] recreate default actions last=" + this.mLastLocale + " cur=" + locale);
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
            for (int i = 0; i < list.size(); i++) {
                MediaController mediaController = (MediaController) list.get(i);
                if (mediaController.getPackageName().equals(componentName.getPackageName())) {
                    setActiveMediaController(mediaController);
                    return;
                }
            }
        }
        setActiveMediaController(null);
    }

    public final void setActiveMediaController(MediaController mediaController) {
        MediaController mediaController2 = this.mMediaController;
        if (mediaController != mediaController2) {
            C40392 c40392 = this.mPlaybackChangedListener;
            if (mediaController2 != null) {
                mediaController2.unregisterCallback(c40392);
            }
            this.mMediaController = mediaController;
            if (mediaController != null) {
                mediaController.registerCallback(c40392, this.mMainHandler);
            }
            ArrayList arrayList = this.mActionListeners;
            if (!arrayList.isEmpty()) {
                arrayList.forEach(new PipMediaController$$ExternalSyntheticLambda1(getMediaActions(), 2));
            }
            MediaController mediaController3 = this.mMediaController;
            MediaMetadata metadata = mediaController3 != null ? mediaController3.getMetadata() : null;
            ArrayList arrayList2 = this.mMetadataListeners;
            if (!arrayList2.isEmpty()) {
                arrayList2.forEach(new PipMediaController$$ExternalSyntheticLambda1(metadata, 0));
            }
            MediaController mediaController4 = this.mMediaController;
            MediaSession.Token sessionToken = mediaController4 != null ? mediaController4.getSessionToken() : null;
            ArrayList arrayList3 = this.mTokenListeners;
            if (arrayList3.isEmpty()) {
                return;
            }
            arrayList3.forEach(new PipMediaController$$ExternalSyntheticLambda1(sessionToken, 1));
        }
    }
}
