package com.android.systemui.media.controls.ui.viewmodel;

import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;

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
        SeekBarViewModel.AnonymousClass1 anonymousClass1 = seekBarViewModel.cancel;
        if (anonymousClass1 != null) {
            anonymousClass1.run();
        }
        SeekBarViewModel seekBarViewModel2 = this.this$0;
        seekBarViewModel2.cancel = null;
        seekBarViewModel2.set_data(SeekBarViewModel.Progress.copy$default(seekBarViewModel2._data, false, false, null, 0, false, 126));
    }
}
