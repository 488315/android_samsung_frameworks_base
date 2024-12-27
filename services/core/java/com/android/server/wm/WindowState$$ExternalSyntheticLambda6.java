package com.android.server.wm;

import com.android.internal.util.ToBooleanFunction;

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
