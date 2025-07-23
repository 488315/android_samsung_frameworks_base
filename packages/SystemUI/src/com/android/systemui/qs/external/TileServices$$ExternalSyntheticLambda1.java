package com.android.systemui.qs.external;

import java.util.Comparator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileServices$$ExternalSyntheticLambda1 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        boolean z = TileServices.DEBUG;
        return -Integer.compare(((TileServiceManager) obj).mPriority, ((TileServiceManager) obj2).mPriority);
    }
}
