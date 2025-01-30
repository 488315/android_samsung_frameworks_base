package com.android.systemui.media;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        }
    }
}
