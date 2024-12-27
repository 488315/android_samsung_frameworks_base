package com.android.server.pm;

import android.content.pm.ProviderInfo;

import java.util.Comparator;

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
