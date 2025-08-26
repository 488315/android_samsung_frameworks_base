package com.android.systemui.media.controls.ui.viewmodel;

import android.media.session.MediaController;
import com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda1;

/* loaded from: classes2.dex */
public final class SeekBarViewModel$onSeek$1 implements Runnable {
    public final /* synthetic */ long $position;
    public final /* synthetic */ SeekBarViewModel this$0;

    public SeekBarViewModel$onSeek$1(SeekBarViewModel seekBarViewModel, long j) {
        this.this$0 = seekBarViewModel;
        this.$position = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MediaController.TransportControls transportControls;
        SeekBarViewModel seekBarViewModel = this.this$0;
        if (seekBarViewModel.isFalseSeek) {
            SeekBarViewModel.access$setScrubbing(seekBarViewModel, false);
            this.this$0.checkPlaybackPosition();
            return;
        }
        MediaControlPanel$$ExternalSyntheticLambda1 mediaControlPanel$$ExternalSyntheticLambda1 = seekBarViewModel.logSeek;
        if (mediaControlPanel$$ExternalSyntheticLambda1 == null) {
            mediaControlPanel$$ExternalSyntheticLambda1 = null;
        }
        mediaControlPanel$$ExternalSyntheticLambda1.invoke();
        MediaController mediaController = this.this$0.controller;
        if (mediaController != null && (transportControls = mediaController.getTransportControls()) != null) {
            transportControls.seekTo(this.$position);
        }
        SeekBarViewModel seekBarViewModel2 = this.this$0;
        seekBarViewModel2.playbackState = null;
        SeekBarViewModel.access$setScrubbing(seekBarViewModel2, false);
    }
}
