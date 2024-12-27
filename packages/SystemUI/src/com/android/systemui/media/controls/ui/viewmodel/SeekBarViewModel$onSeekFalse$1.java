package com.android.systemui.media.controls.ui.viewmodel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
