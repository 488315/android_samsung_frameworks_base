package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.CastController;
import java.util.function.Predicate;

public final /* synthetic */ class CastControllerImpl$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((CastController.CastDevice) obj).state == 2;
    }
}
