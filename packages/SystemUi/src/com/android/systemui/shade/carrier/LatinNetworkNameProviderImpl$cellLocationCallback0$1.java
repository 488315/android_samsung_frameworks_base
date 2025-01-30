package com.android.systemui.shade.carrier;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public /* synthetic */ class LatinNetworkNameProviderImpl$cellLocationCallback0$1 extends FunctionReferenceImpl implements Function1 {
    public LatinNetworkNameProviderImpl$cellLocationCallback0$1(Object obj) {
        super(1, obj, LatinNetworkNameProviderImpl.class, "handleCellLocationChanged", "handleCellLocationChanged(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LatinNetworkNameProviderImpl.access$handleCellLocationChanged((LatinNetworkNameProviderImpl) this.receiver, ((Number) obj).intValue());
        return Unit.INSTANCE;
    }
}
