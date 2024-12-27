package com.android.systemui.shade.carrier;

import com.android.systemui.shade.carrier.ShadeCarrierGroupController;
import java.util.function.Predicate;

public final /* synthetic */ class ShadeCarrierGroupController$$ExternalSyntheticLambda2 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = ((ShadeCarrierGroupController.IconData) obj).slotIndex;
        return i < 3 && i != -1;
    }
}
