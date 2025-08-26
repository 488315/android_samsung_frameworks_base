package com.android.wm.shell.common;

import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda16;
import java.util.function.Consumer;

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
