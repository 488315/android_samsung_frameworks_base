package com.android.systemui.media;

import com.android.systemui.media.SecSeekBarViewModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecSeekBarViewModel$clearController$1 implements Runnable {
    public final /* synthetic */ SecSeekBarViewModel this$0;

    public SecSeekBarViewModel$clearController$1(SecSeekBarViewModel secSeekBarViewModel) {
        this.this$0 = secSeekBarViewModel;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.setController(null);
        SecSeekBarViewModel secSeekBarViewModel = this.this$0;
        secSeekBarViewModel.playbackState = null;
        Runnable runnable = secSeekBarViewModel.cancel;
        if (runnable != null) {
            runnable.run();
        }
        SecSeekBarViewModel secSeekBarViewModel2 = this.this$0;
        secSeekBarViewModel2.cancel = null;
        secSeekBarViewModel2.set_data(SecSeekBarViewModel.Progress.copy$default(secSeekBarViewModel2._data, false, null, 62));
    }
}
