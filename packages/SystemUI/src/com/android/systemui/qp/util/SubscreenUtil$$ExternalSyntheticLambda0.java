package com.android.systemui.qp.util;

import android.util.Log;
import android.view.Display;
import java.util.function.Predicate;

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
