package com.android.settingslib.bluetooth;

import java.util.function.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class BluetoothEventManager$$ExternalSyntheticLambda2 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((LocalBluetoothProfile) obj) instanceof CsipSetCoordinatorProfile;
    }
}
