package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class InsetsStateController$$ExternalSyntheticLambda2
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        WindowState windowState = (WindowState) obj;
        boolean z = false;
        if (windowState.mStartingData == null) {
            if ((windowState.shouldCheckTokenVisibleRequested()
                            ? windowState.isVisibleRequested()
                            : windowState.isVisible())
                    && windowState.mFrozenInsetsState == null) {
                z = true;
            }
        }
        if (z) {
            windowState.notifyInsetsChanged();
        }
    }
}
