package com.android.systemui.navigationbar.buttons;

import android.view.View;
import java.util.function.Predicate;

public final /* synthetic */ class NearestTouchFrame$$ExternalSyntheticLambda1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((View) obj).isAttachedToWindow();
    }
}
