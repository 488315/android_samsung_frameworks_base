package com.android.systemui.qp.util;

import android.util.Log;
import android.view.Display;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
