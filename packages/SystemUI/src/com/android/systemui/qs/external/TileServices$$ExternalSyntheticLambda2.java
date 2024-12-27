package com.android.systemui.qs.external;

import java.util.Comparator;

public final /* synthetic */ class TileServices$$ExternalSyntheticLambda2 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return -Integer.compare(((TileServiceManager) obj).mPriority, ((TileServiceManager) obj2).mPriority);
    }
}
