package com.android.systemui.qp.util;

import android.util.Log;
import android.view.Display;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscreenUtil$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Display display = (Display) obj;
        if (display == null) {
            Log.d("SubscreenUtil", "Do not show SubScreen UI on null display");
        } else {
            if (display.getDisplayId() == 1) {
                Log.d("SubscreenUtil", "Show SubScreen UI on this display " + display);
                return true;
            }
            Log.d("SubscreenUtil", "Do not show SubScreen UI on this display " + display);
        }
        return false;
    }
}
