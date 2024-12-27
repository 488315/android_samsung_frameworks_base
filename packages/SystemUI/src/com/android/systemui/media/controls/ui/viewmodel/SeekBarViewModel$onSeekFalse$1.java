package com.android.systemui.media.controls.ui.viewmodel;

public final class SeekBarViewModel$onSeekFalse$1 implements Runnable {
    public final /* synthetic */ SeekBarViewModel this$0;

    public SeekBarViewModel$onSeekFalse$1(SeekBarViewModel seekBarViewModel) {
        this.this$0 = seekBarViewModel;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SeekBarViewModel seekBarViewModel = this.this$0;
        if (seekBarViewModel.scrubbing) {
            seekBarViewModel.isFalseSeek = true;
        }
    }
}
