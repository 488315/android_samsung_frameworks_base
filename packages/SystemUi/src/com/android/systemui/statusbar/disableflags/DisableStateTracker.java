package com.android.systemui.statusbar.disableflags;

import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DisableStateTracker implements CommandQueue.Callbacks {
    public final Callback callback;
    public Integer displayId;
    public boolean isDisabled;
    public final int mask1;
    public final int mask2;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    public DisableStateTracker(int i, int i2, Callback callback) {
        this.mask1 = i;
        this.mask2 = i2;
        this.callback = callback;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        Integer num = this.displayId;
        if (num == null || i != num.intValue()) {
            return;
        }
        boolean z2 = ((this.mask1 & i2) == 0 && (this.mask2 & i3) == 0) ? false : true;
        if (this.isDisabled == z2) {
            return;
        }
        this.isDisabled = z2;
        ((KeyguardStatusBarViewController$$ExternalSyntheticLambda2) this.callback).f$0.updateViewState();
    }
}
