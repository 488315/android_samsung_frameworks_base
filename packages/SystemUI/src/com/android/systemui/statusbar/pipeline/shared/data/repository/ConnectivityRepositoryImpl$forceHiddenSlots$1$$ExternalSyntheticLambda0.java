package com.android.systemui.statusbar.pipeline.shared.data.repository;

import com.android.systemui.tuner.TunerService;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ConnectivityRepositoryImpl$forceHiddenSlots$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ConnectivityRepositoryImpl$forceHiddenSlots$1$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((TunerService) this.f$0).removeTunable((ConnectivityRepositoryImpl$forceHiddenSlots$1$callback$1) this.f$1);
                break;
            default:
                ((ConnectivityRepositoryImpl) this.f$0).connectivityManager.unregisterNetworkCallback((ConnectivityRepositoryImpl$defaultNetworkCapabilities$1$callback$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
