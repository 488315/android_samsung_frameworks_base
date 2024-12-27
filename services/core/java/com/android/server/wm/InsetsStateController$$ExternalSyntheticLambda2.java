package com.android.server.wm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class InsetsStateController$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        WindowState windowState = (WindowState) obj;
        boolean z = false;
        if (windowState.mStartingData == null) {
            if ((windowState.shouldCheckTokenVisibleRequested() ? windowState.isVisibleRequested() : windowState.isVisible()) && windowState.mFrozenInsetsState == null) {
                z = true;
            }
        }
        if (z) {
            windowState.notifyInsetsChanged();
        }
    }
}
