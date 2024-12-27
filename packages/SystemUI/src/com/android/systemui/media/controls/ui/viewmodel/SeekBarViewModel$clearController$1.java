package com.android.systemui.media.controls.ui.viewmodel;

import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SeekBarViewModel$clearController$1 implements Runnable {
    public final /* synthetic */ SeekBarViewModel this$0;

    public SeekBarViewModel$clearController$1(SeekBarViewModel seekBarViewModel) {
        this.this$0 = seekBarViewModel;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.setController(null);
        SeekBarViewModel seekBarViewModel = this.this$0;
        seekBarViewModel.playbackState = null;
        Runnable runnable = seekBarViewModel.cancel;
        if (runnable != null) {
            runnable.run();
        }
        SeekBarViewModel seekBarViewModel2 = this.this$0;
        seekBarViewModel2.cancel = null;
        seekBarViewModel2.set_data(SeekBarViewModel.Progress.copy$default(seekBarViewModel2._data, false, null, false, 126));
    }
}
