package com.android.systemui.media;

import com.android.systemui.media.SecSeekBarViewModel;

/* loaded from: classes2.dex */
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
        SecSeekBarViewModel.AnonymousClass1 anonymousClass1 = secSeekBarViewModel.cancel;
        if (anonymousClass1 != null) {
            anonymousClass1.run();
        }
        SecSeekBarViewModel secSeekBarViewModel2 = this.this$0;
        secSeekBarViewModel2.cancel = null;
        secSeekBarViewModel2.set_data(SecSeekBarViewModel.Progress.copy$default(secSeekBarViewModel2._data, false, false, null, 0, false, 126));
    }
}
