package com.android.systemui.wallpaper.video;

import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VideoPlayer$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VideoPlayer f$0;

    public /* synthetic */ VideoPlayer$$ExternalSyntheticLambda2(VideoPlayer videoPlayer, int i) {
        this.$r8$classId = i;
        this.f$0 = videoPlayer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                VideoPlayer videoPlayer = this.f$0;
                videoPlayer.releaseMediaPlayer(videoPlayer.mSemMediaPlayer);
                break;
            default:
                VideoPlayer videoPlayer2 = this.f$0;
                if (videoPlayer2.mSemMediaPlayer == null) {
                    Log.w("VideoPlayer", "stopDrawing() mediaPlayer is null");
                    break;
                } else {
                    if (videoPlayer2.mIsPendingStarted) {
                        Log.d("VideoPlayer", "stopDrawing() Do not play for previous request.");
                        videoPlayer2.mIsPendingStarted = false;
                    }
                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("stopDrawing() mIsPrepared = "), videoPlayer2.mIsPrepared, "VideoPlayer");
                    try {
                        if (videoPlayer2.mIsPrepared && videoPlayer2.mSemMediaPlayer.isPlaying()) {
                            videoPlayer2.mSemMediaPlayer.pause();
                            VideoPlayer.HandlerC36981 handlerC36981 = videoPlayer2.mMediaControlHandler;
                            if (handlerC36981.hasMessages(1001)) {
                                handlerC36981.removeMessages(1001);
                                break;
                            }
                        }
                    } catch (IllegalStateException e) {
                        Log.e("VideoPlayer", "stopDrawing() failed pause");
                        e.printStackTrace();
                        return;
                    }
                }
                break;
        }
    }
}
