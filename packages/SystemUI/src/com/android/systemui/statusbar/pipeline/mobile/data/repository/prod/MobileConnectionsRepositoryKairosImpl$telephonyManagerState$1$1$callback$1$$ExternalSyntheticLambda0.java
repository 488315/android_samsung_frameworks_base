package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import java.util.Set;
import kotlin.Pair;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        int i = this.f$0;
        Pair pair = (Pair) obj;
        switch (this.$r8$classId) {
            case 0:
                int i2 = MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1.$r8$clinit;
                return new Pair((Integer) pair.component1(), SetsKt___SetsKt.minus((Set) pair.component2(), Integer.valueOf(i)));
            case 1:
                int i3 = MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1.$r8$clinit;
                return new Pair((Integer) pair.component1(), SetsKt___SetsKt.plus((Set) pair.component2(), Integer.valueOf(i)));
            default:
                int i4 = MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1.$r8$clinit;
                return new Pair(Integer.valueOf(i), (Set) pair.component2());
        }
    }
}
