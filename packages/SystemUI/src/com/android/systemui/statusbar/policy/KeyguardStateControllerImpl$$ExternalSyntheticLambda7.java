package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.Objects;
import java.util.function.Predicate;

public final /* synthetic */ class KeyguardStateControllerImpl$$ExternalSyntheticLambda7 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return Objects.nonNull((KeyguardStateController.Callback) obj);
    }
}
