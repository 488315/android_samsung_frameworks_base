package com.android.systemui.media;

import android.content.Context;
import android.media.session.MediaController;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.subscreen.SubRoom;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SubscreenMusicWidgetSubroom implements SubRoom {
    public final Context mContext;
    public final SecMediaHost mMediaHost;
    public FrameLayout mRootView;

    public SubscreenMusicWidgetSubroom(Context context, SecMediaHost secMediaHost) {
        this.mContext = context;
        this.mMediaHost = secMediaHost;
        init();
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final View getView(Context context) {
        return this.mRootView;
    }

    public final void init() {
        this.mRootView = (FrameLayout) LayoutInflater.from(this.mContext).inflate(R.layout.subscreen_media_container, (ViewGroup) null);
        MediaType mediaType = MediaType.COVER;
        SecMediaHost secMediaHost = this.mMediaHost;
        secMediaHost.removeMediaFrame(mediaType);
        secMediaHost.addMediaFrame(this.mRootView, mediaType);
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final Bundle request(String str, Bundle bundle) {
        CoverMusicCapsuleController coverMusicCapsuleController;
        MediaController mediaController;
        if (!SubRoom.STATE_MUSIC_CAPSULE_INFO.equals(str)) {
            return null;
        }
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m(" request ", str, "SubLauncherMusic");
        if (bundle == null || !bundle.getBoolean("capsule", false)) {
            init();
            return null;
        }
        MediaType mediaType = MediaType.COVER;
        SecMediaHost secMediaHost = this.mMediaHost;
        secMediaHost.getClass();
        if (!mediaType.getSupportCapsule()) {
            return null;
        }
        if (secMediaHost.mCurrentMediaData == null) {
            Log.d("SecMediaHost", "There is no current media data, exit");
            return null;
        }
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(mediaType);
        if (secMediaPlayerData == null) {
            Log.d("SecMediaHost", "There is no frame, exit");
            return null;
        }
        SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().get(secMediaHost.mCurrentMediaData.key);
        if (secMediaControlPanel == null) {
            Log.d("SecMediaHost", "There is no player for key, exit");
        }
        if (!secMediaControlPanel.mIsPlayerCoverPlayed || (coverMusicCapsuleController = secMediaControlPanel.mCoverMusicCapsuleController) == null || (mediaController = secMediaControlPanel.mController) == null) {
            return null;
        }
        coverMusicCapsuleController.updateEqualizerState(mediaController.getPlaybackState());
        return null;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void setListener(SubRoom.StateChangeListener stateChangeListener) {
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void removeListener() {
    }
}
