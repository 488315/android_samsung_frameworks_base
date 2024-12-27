package com.android.systemui.qs.external;

import java.util.Comparator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileServices$$ExternalSyntheticLambda2 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return -Integer.compare(((TileServiceManager) obj).mPriority, ((TileServiceManager) obj2).mPriority);
    }
}
