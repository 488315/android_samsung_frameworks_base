package com.android.server.wm;

import com.android.internal.util.ToBooleanFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowState$$ExternalSyntheticLambda6
        implements ToBooleanFunction {
    public final boolean apply(Object obj) {
        WindowState windowState = (WindowState) obj;
        if (!windowState.isSelfAnimating(0, 128)) {
            return false;
        }
        windowState.cancelAnimation();
        return true;
    }
}
