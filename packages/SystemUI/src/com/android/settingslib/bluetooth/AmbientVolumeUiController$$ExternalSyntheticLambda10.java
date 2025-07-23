package com.android.settingslib.bluetooth;

import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AmbientVolumeUiController$$ExternalSyntheticLambda10 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((LocalBluetoothProfile) obj) instanceof VolumeControlProfile;
    }
}
