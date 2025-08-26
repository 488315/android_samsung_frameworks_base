package com.android.systemui.navigationbar.views.buttons;

import android.view.View;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final /* synthetic */ class NearestTouchFrame$$ExternalSyntheticLambda1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((View) obj).isAttachedToWindow();
    }
}
