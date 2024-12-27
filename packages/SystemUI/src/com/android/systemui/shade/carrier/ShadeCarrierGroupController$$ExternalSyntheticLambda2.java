package com.android.systemui.shade.carrier;

import com.android.systemui.shade.carrier.ShadeCarrierGroupController;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShadeCarrierGroupController$$ExternalSyntheticLambda2 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = ((ShadeCarrierGroupController.IconData) obj).slotIndex;
        return i < 3 && i != -1;
    }
}
