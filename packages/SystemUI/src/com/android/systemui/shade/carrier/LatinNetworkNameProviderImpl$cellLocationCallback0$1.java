package com.android.systemui.shade.carrier;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final /* synthetic */ class LatinNetworkNameProviderImpl$cellLocationCallback0$1 extends FunctionReferenceImpl implements Function1 {
    public LatinNetworkNameProviderImpl$cellLocationCallback0$1(Object obj) {
        super(1, obj, LatinNetworkNameProviderImpl.class, "handleCellLocationChanged", "handleCellLocationChanged(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LatinNetworkNameProviderImpl.access$handleCellLocationChanged((LatinNetworkNameProviderImpl) this.receiver, ((Number) obj).intValue());
        return Unit.INSTANCE;
    }
}
