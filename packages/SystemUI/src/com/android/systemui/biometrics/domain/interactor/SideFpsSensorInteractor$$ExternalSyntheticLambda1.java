package com.android.systemui.biometrics.domain.interactor;

import com.android.systemui.biometrics.domain.model.SideFpsSensorLocation;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final /* synthetic */ class SideFpsSensorInteractor$$ExternalSyntheticLambda1 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        SideFpsSensorLocation sideFpsSensorLocation = (SideFpsSensorLocation) obj;
        SideFpsSensorLocation sideFpsSensorLocation2 = (SideFpsSensorLocation) obj2;
        return Boolean.valueOf(sideFpsSensorLocation.left == sideFpsSensorLocation2.left && sideFpsSensorLocation.top == sideFpsSensorLocation2.top && sideFpsSensorLocation.length == sideFpsSensorLocation2.length && sideFpsSensorLocation.isSensorVerticalInDefaultOrientation == sideFpsSensorLocation2.isSensorVerticalInDefaultOrientation);
    }
}
