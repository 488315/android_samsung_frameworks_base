package com.android.server.wm;

import android.view.InsetsState;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowToken$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ WindowToken f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ WindowToken$$ExternalSyntheticLambda1(WindowToken windowToken, boolean z) {
        this.f$0 = windowToken;
        this.f$1 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        WindowToken windowToken = this.f$0;
        boolean z = this.f$1;
        WindowState windowState = (WindowState) obj;
        windowToken.getClass();
        if (windowState.mToken == windowToken) {
            if (!z) {
                windowState.mFrozenInsetsState = null;
            } else if (windowState.mFrozenInsetsState == null) {
                windowState.mFrozenInsetsState = new InsetsState(windowState.getInsetsState(false), true);
            }
        }
    }
}
