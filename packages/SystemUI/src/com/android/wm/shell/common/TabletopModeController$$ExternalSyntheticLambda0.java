package com.android.wm.shell.common;

import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda16;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class TabletopModeController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ TabletopModeController$$ExternalSyntheticLambda0(boolean z) {
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        boolean z = this.f$0;
        boolean z2 = TabletopModeController.PREFER_TOP_HALF_IN_TABLETOP;
        ((PipController$$ExternalSyntheticLambda16) obj).onTabletopModeChanged(z);
    }
}
