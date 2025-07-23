package com.android.systemui.qp.util;

import android.util.Log;
import android.view.Display;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscreenUtil$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Display display = (Display) obj;
        if (display == null) {
            Log.d("SubscreenUtil", "Do not show SubScreen UI on null display");
            return false;
        }
        if (display.getDisplayId() == 1) {
            Log.d("SubscreenUtil", "Show SubScreen UI on this display " + display);
            return true;
        }
        Log.d("SubscreenUtil", "Do not show SubScreen UI on this display " + display);
        return false;
    }
}
