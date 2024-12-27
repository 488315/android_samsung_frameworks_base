package com.android.systemui.media;

import com.android.systemui.media.SecSeekBarViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        Runnable runnable = secSeekBarViewModel.cancel;
        if (runnable != null) {
            runnable.run();
        }
        SecSeekBarViewModel secSeekBarViewModel2 = this.this$0;
        secSeekBarViewModel2.cancel = null;
        secSeekBarViewModel2.set_data(SecSeekBarViewModel.Progress.copy$default(secSeekBarViewModel2._data, false, null, false, 126));
    }
}
