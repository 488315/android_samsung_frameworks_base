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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SubscreenMusicWidgetSubroom implements SubRoom {
    public final Context mContext;
    public final SecMediaHost mMediaHost;
    public FrameLayout mRootView;

    public SubscreenMusicWidgetSubroom(Context context, SecMediaHost secMediaHost) {
        this.mContext = context;
        this.mMediaHost = secMediaHost;
        this.mRootView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.subscreen_media_container, (ViewGroup) null);
        MediaType mediaType = MediaType.COVER;
        secMediaHost.removeMediaFrame(mediaType);
        secMediaHost.addMediaFrame(mediaType, this.mRootView);
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final View getView(Context context) {
        return this.mRootView;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final Bundle request(String str, Bundle bundle) {
        CoverMusicCapsuleController coverMusicCapsuleController;
        MediaController mediaController;
        if (SubRoom.STATE_MUSIC_CAPSULE_INFO.equals(str)) {
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m(" request ", str, "SubLauncherMusic");
            SecMediaHost secMediaHost = this.mMediaHost;
            if (bundle == null || !bundle.getBoolean("capsule", false)) {
                this.mRootView = (FrameLayout) LayoutInflater.from(this.mContext).inflate(R.layout.subscreen_media_container, (ViewGroup) null);
                MediaType mediaType = MediaType.COVER;
                secMediaHost.removeMediaFrame(mediaType);
                secMediaHost.addMediaFrame(mediaType, this.mRootView);
            } else {
                MediaType mediaType2 = MediaType.COVER;
                secMediaHost.getClass();
                if (mediaType2.getSupportCapsule()) {
                    if (secMediaHost.mCurrentMediaData == null) {
                        Log.d("SecMediaHost", "There is no current media data, exit");
                    } else {
                        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(mediaType2);
                        if (secMediaPlayerData == null) {
                            Log.d("SecMediaHost", "There is no frame, exit");
                        } else {
                            SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().get(secMediaHost.mCurrentMediaData.key);
                            if (secMediaControlPanel == null) {
                                Log.d("SecMediaHost", "There is no player for key, exit");
                            } else if (secMediaControlPanel.mIsPlayerCoverPlayed && (coverMusicCapsuleController = secMediaControlPanel.mCoverMusicCapsuleController) != null && (mediaController = secMediaControlPanel.mController) != null) {
                                coverMusicCapsuleController.updateEqualizerState(mediaController.getPlaybackState());
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void removeListener() {
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void setListener(SubRoom.StateChangeListener stateChangeListener) {
    }
}
