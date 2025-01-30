package com.android.systemui.wallpaper.video;

import android.util.Log;
import com.android.systemui.wallpaper.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VideoPlayer$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VideoPlayer f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ VideoPlayer$$ExternalSyntheticLambda3(VideoPlayer videoPlayer, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = videoPlayer;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                VideoPlayer videoPlayer = this.f$0;
                int i = this.f$1;
                if (!videoPlayer.mIsPrepared) {
                    Log.w("VideoPlayer", "startDrawing() mediaPlayer is not prepared");
                    videoPlayer.mIsPendingStarted = true;
                    break;
                } else if (videoPlayer.mSemMediaPlayer == null) {
                    Log.w("VideoPlayer", "startDrawing() mediaPlayer is null");
                    break;
                } else {
                    Log.d("VideoPlayer", "startDrawing() mIsPrepared = " + videoPlayer.mIsPrepared + ", playTime = " + i);
                    try {
                        if (videoPlayer.mIsPrepared && !videoPlayer.mSemMediaPlayer.isPlaying()) {
                            videoPlayer.mSemMediaPlayer.start();
                            VideoPlayer.HandlerC36981 handlerC36981 = videoPlayer.mMediaControlHandler;
                            if (handlerC36981.hasMessages(1001)) {
                                handlerC36981.removeMessages(1001);
                            }
                            if (i > 0) {
                                handlerC36981.sendEmptyMessageDelayed(1001, i);
                                break;
                            }
                        }
                    } catch (IllegalStateException e) {
                        Log.e("VideoPlayer", "startDrawing() failed start");
                        e.printStackTrace();
                    }
                }
                break;
            default:
                VideoPlayer videoPlayer2 = this.f$0;
                int i2 = this.f$1;
                if (videoPlayer2.mSemMediaPlayer == null) {
                    Log.w("VideoPlayer", "seekTo() mediaPlayer is null");
                    break;
                } else if (!videoPlayer2.mIsPrepared) {
                    Log.w("VideoPlayer", "seekTo() mediaPlayer is not prepared");
                    break;
                } else {
                    try {
                        Log.i("VideoPlayer", "seekTo: " + i2);
                        videoPlayer2.mSemMediaPlayer.seekTo(i2);
                        break;
                    } catch (IllegalStateException e2) {
                        e2.printStackTrace();
                        Log.e("VideoPlayer", "failed seekTo");
                        return;
                    }
                }
        }
    }
}
