package com.android.systemui.qs.external;

import java.util.Comparator;

/* loaded from: classes2.dex */
public final /* synthetic */ class TileServices$$ExternalSyntheticLambda1 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        boolean z = TileServices.DEBUG;
        return -Integer.compare(((TileServiceManager) obj).mPriority, ((TileServiceManager) obj2).mPriority);
    }
}
