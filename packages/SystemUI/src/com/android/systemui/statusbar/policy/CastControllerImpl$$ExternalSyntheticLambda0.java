package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.CastDevice;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class CastControllerImpl$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((CastDevice) obj).state == CastDevice.CastState.Connected;
    }
}
