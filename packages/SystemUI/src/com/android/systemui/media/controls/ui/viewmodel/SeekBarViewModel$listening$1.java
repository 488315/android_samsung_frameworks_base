package com.android.systemui.media.controls.ui.viewmodel;

import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SeekBarViewModel$listening$1 implements Runnable {
    public final /* synthetic */ boolean $value;
    public final /* synthetic */ SeekBarViewModel this$0;

    public SeekBarViewModel$listening$1(SeekBarViewModel seekBarViewModel, boolean z) {
        this.this$0 = seekBarViewModel;
        this.$value = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SeekBarViewModel seekBarViewModel = this.this$0;
        boolean z = seekBarViewModel.listening;
        boolean z2 = this.$value;
        if (z != z2) {
            seekBarViewModel.listening = z2;
            seekBarViewModel.checkIfPollingNeeded(true);
            SeekBarViewModel seekBarViewModel2 = this.this$0;
            seekBarViewModel2.set_data(SeekBarViewModel.Progress.copy$default(seekBarViewModel2._data, false, false, null, 0, this.$value, 63));
        }
    }
}
