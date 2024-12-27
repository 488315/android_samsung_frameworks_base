package com.android.systemui.media.controls.ui.viewmodel;

import android.media.session.MediaController;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            SeekBarViewModel.access$checkPlaybackPosition(this.this$0);
            return;
        }
        Function0 function0 = seekBarViewModel.logSeek;
        if (function0 == null) {
            function0 = null;
        }
        function0.invoke();
        MediaController mediaController = this.this$0.controller;
        if (mediaController != null && (transportControls = mediaController.getTransportControls()) != null) {
            transportControls.seekTo(this.$position);
        }
        SeekBarViewModel seekBarViewModel2 = this.this$0;
        seekBarViewModel2.playbackState = null;
        SeekBarViewModel.access$setScrubbing(seekBarViewModel2, false);
    }
}
