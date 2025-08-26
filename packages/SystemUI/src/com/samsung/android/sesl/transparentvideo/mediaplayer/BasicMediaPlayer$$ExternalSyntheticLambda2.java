package com.samsung.android.sesl.transparentvideo.mediaplayer;

import android.media.MediaPlayer;
import android.util.Log;
import com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer;
import java.io.IOException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes4.dex */
public final /* synthetic */ class BasicMediaPlayer$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BasicMediaPlayer f$0;

    public /* synthetic */ BasicMediaPlayer$$ExternalSyntheticLambda2(BasicMediaPlayer basicMediaPlayer, int i) {
        this.$r8$classId = i;
        this.f$0 = basicMediaPlayer;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() throws IllegalStateException, IOException {
        switch (this.$r8$classId) {
            case 0:
                BasicMediaPlayer basicMediaPlayer = this.f$0;
                Log.i("BasicMediaPlayer", "play() lastCallState=" + basicMediaPlayer.lastCallState);
                if (!basicMediaPlayer.mediaPlayer.isPlaying()) {
                    basicMediaPlayer.executeSafely("play", IMediaPlayer$ErrorType.INVALID_STATE, new BasicMediaPlayer$$ExternalSyntheticLambda2(basicMediaPlayer, 2));
                }
                basicMediaPlayer.lastCallState = BasicMediaPlayer.MediaCallState.PLAY;
                break;
            case 1:
                this.f$0.mediaPlayer.prepareAsync();
                break;
            case 2:
                this.f$0.mediaPlayer.start();
                break;
            case 3:
                this.f$0.mediaPlayer.prepareAsync();
                break;
            case 4:
                this.f$0.mediaPlayer.pause();
                break;
            case 5:
                final BasicMediaPlayer basicMediaPlayer2 = this.f$0;
                Log.i("BasicMediaPlayer", "seekTo(0) lastCallState=" + basicMediaPlayer2.lastCallState);
                basicMediaPlayer2.mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() { // from class: com.samsung.android.sesl.transparentvideo.mediaplayer.BasicMediaPlayer$$ExternalSyntheticLambda16
                    @Override // android.media.MediaPlayer.OnSeekCompleteListener
                    public final void onSeekComplete(MediaPlayer mediaPlayer) {
                        BasicMediaPlayer basicMediaPlayer3 = basicMediaPlayer2;
                        basicMediaPlayer3.isSeeking = false;
                        Long l = basicMediaPlayer3.pendingSeekPosition;
                        if (l != null) {
                            basicMediaPlayer3.seekInternal(l.longValue());
                        }
                        basicMediaPlayer3.pendingSeekPosition = null;
                    }
                });
                if (basicMediaPlayer2.isSeeking) {
                    basicMediaPlayer2.pendingSeekPosition = 0L;
                    Log.w("BasicMediaPlayer", "Already seeking! just updated pending seek position to " + ((Object) 0L) + ".");
                } else {
                    basicMediaPlayer2.seekInternal(0L);
                }
                break;
            case 6:
                BasicMediaPlayer basicMediaPlayer3 = this.f$0;
                basicMediaPlayer3.mediaPlayer.prepare();
                basicMediaPlayer3.isPrepared = true;
                break;
            case 7:
                BasicMediaPlayer basicMediaPlayer4 = this.f$0;
                Log.i("BasicMediaPlayer", "pause() lastCallState=" + basicMediaPlayer4.lastCallState);
                basicMediaPlayer4.lastCallState = BasicMediaPlayer.MediaCallState.PAUSE;
                if (basicMediaPlayer4.mediaPlayer.isPlaying()) {
                    basicMediaPlayer4.executeSafely("pause", IMediaPlayer$ErrorType.INVALID_STATE, new BasicMediaPlayer$$ExternalSyntheticLambda2(basicMediaPlayer4, 4));
                }
                break;
            default:
                this.f$0.mediaPlayer.reset();
                break;
        }
        return Unit.INSTANCE;
    }
}
