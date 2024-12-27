package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class TransitionController$$ExternalSyntheticLambda7
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        WindowState windowState = (WindowState) obj;
        if (windowState.mToken.mRoundedCornerOverlay && windowState.mHasSurface) {
            windowState.mSyncMethodOverride = 1;
        }
    }
}
