package com.android.systemui.statusbar.disableflags;

import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisableStateTracker implements CommandQueue.Callbacks {
    public final Callback callback;
    public Integer displayId;
    public boolean isDisabled;
    public final int mask1;
    public final int mask2;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
