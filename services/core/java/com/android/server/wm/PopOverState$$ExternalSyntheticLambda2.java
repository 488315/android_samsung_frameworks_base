package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class PopOverState$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((WindowState) obj).mPopOverDimmerNeeded = false;
    }
}
