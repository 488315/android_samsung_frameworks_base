package com.android.systemui.biometrics.domain.interactor;

import com.android.systemui.biometrics.domain.model.SideFpsSensorLocation;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class SideFpsSensorInteractor$$ExternalSyntheticLambda1 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        SideFpsSensorLocation sideFpsSensorLocation = (SideFpsSensorLocation) obj;
        SideFpsSensorLocation sideFpsSensorLocation2 = (SideFpsSensorLocation) obj2;
        return Boolean.valueOf(sideFpsSensorLocation.left == sideFpsSensorLocation2.left && sideFpsSensorLocation.top == sideFpsSensorLocation2.top && sideFpsSensorLocation.length == sideFpsSensorLocation2.length && sideFpsSensorLocation.isSensorVerticalInDefaultOrientation == sideFpsSensorLocation2.isSensorVerticalInDefaultOrientation);
    }
}
