package com.android.systemui.media.controls.ui.viewmodel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
