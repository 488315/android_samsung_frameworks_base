package com.android.systemui.media;

import com.android.systemui.media.SecSeekBarViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecSeekBarViewModel$listening$1 implements Runnable {
    public final /* synthetic */ boolean $value;
    public final /* synthetic */ SecSeekBarViewModel this$0;

    public SecSeekBarViewModel$listening$1(SecSeekBarViewModel secSeekBarViewModel, boolean z) {
        this.this$0 = secSeekBarViewModel;
        this.$value = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SecSeekBarViewModel secSeekBarViewModel = this.this$0;
        boolean z = secSeekBarViewModel.listening;
        boolean z2 = this.$value;
        if (z != z2) {
            secSeekBarViewModel.listening = z2;
            secSeekBarViewModel.checkIfPollingNeeded();
            SecSeekBarViewModel secSeekBarViewModel2 = this.this$0;
            secSeekBarViewModel2.set_data(SecSeekBarViewModel.Progress.copy$default(secSeekBarViewModel2._data, false, null, this.$value, 63));
        }
    }
}
