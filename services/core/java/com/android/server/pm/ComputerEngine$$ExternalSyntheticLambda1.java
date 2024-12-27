package com.android.server.pm;

import android.content.pm.ProviderInfo;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ComputerEngine$$ExternalSyntheticLambda1 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ComputerEngine$$ExternalSyntheticLambda1 computerEngine$$ExternalSyntheticLambda1 =
                ComputerEngine.sProviderInitOrderSorter;
        int i = ((ProviderInfo) obj).initOrder;
        int i2 = ((ProviderInfo) obj2).initOrder;
        if (i > i2) {
            return -1;
        }
        return i < i2 ? 1 : 0;
    }
}
