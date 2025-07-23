package com.android.systemui.statusbar.disableflags;

import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DisableStateTracker implements CommandQueue.Callbacks {
    public final Callback callback;
    public Integer displayId;
    public boolean isDisabled;
    public final int mask1;
    public final int mask2;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        if (num == null || num == null || i != num.intValue()) {
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
