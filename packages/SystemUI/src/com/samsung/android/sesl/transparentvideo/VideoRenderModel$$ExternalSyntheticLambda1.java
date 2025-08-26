package com.samsung.android.sesl.transparentvideo;

import android.util.Log;
import android.view.Surface;
import com.samsung.android.sesl.transparentvideo.VideoRenderModel;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda2;

/* loaded from: classes4.dex */
public final /* synthetic */ class VideoRenderModel$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VideoRenderModel f$0;

    public /* synthetic */ VideoRenderModel$$ExternalSyntheticLambda1(VideoRenderModel videoRenderModel, int i) {
        this.$r8$classId = i;
        this.f$0 = videoRenderModel;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        VideoRenderModel videoRenderModel = this.f$0;
        switch (i) {
            case 0:
                int i2 = VideoRenderModel.$r8$clinit;
                Log.i("VideoRenderModel", "Surface destroyed. Releasing all media objects.");
                BasicMediaPlayer basicMediaPlayer = videoRenderModel.mediaPlayer;
                if (basicMediaPlayer != null) {
                    basicMediaPlayer.release();
                }
                videoRenderModel.mediaPlayer = null;
                Surface surface = videoRenderModel.mediaPlayerSurface;
                if (surface != null) {
                    surface.release();
                }
                videoRenderModel.mediaPlayerSurface = null;
                videoRenderModel.state = VideoRenderModel.State.PAUSED;
                break;
            default:
                int i3 = VideoRenderModel.$r8$clinit;
                videoRenderModel.state = VideoRenderModel.State.READY;
                if (videoRenderModel.onCompletion == null) {
                    videoRenderModel.textureListener.notifyPlaybackStarted();
                    BasicMediaPlayer basicMediaPlayer2 = videoRenderModel.mediaPlayer;
                    if (basicMediaPlayer2 != null) {
                        basicMediaPlayer2.runOnPrepared(new BasicMediaPlayer$$ExternalSyntheticLambda2(basicMediaPlayer2, 5));
                    }
                    Log.i("VideoRenderModel", "Seek requested. positionMs=0");
                }
                Runnable runnable = videoRenderModel.onCompletion;
                if (runnable != null) {
                    runnable.run();
                    break;
                }
                break;
        }
    }
}
