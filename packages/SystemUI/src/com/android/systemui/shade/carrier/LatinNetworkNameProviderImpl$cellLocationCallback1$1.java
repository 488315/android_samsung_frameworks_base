package com.android.systemui.shade.carrier;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class LatinNetworkNameProviderImpl$cellLocationCallback1$1 extends FunctionReferenceImpl implements Function1 {
    public LatinNetworkNameProviderImpl$cellLocationCallback1$1(Object obj) {
        super(1, obj, LatinNetworkNameProviderImpl.class, "handleCellLocationChanged", "handleCellLocationChanged(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LatinNetworkNameProviderImpl.access$handleCellLocationChanged((LatinNetworkNameProviderImpl) this.receiver, ((Number) obj).intValue());
        return Unit.INSTANCE;
    }
}
