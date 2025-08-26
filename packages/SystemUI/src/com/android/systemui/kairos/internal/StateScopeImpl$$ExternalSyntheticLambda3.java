package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.EventsInit;
import com.android.systemui.kairos.MergeKt;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda3;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class StateScopeImpl$$ExternalSyntheticLambda3 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ StateScopeImpl f$1;

    public /* synthetic */ StateScopeImpl$$ExternalSyntheticLambda3(Object obj, StateScopeImpl stateScopeImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = stateScopeImpl;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return MergeKt.merge((EventsInit) this.f$0, this.f$1.getEndSignal());
            default:
                return ((StateScope$DefaultImpls$$ExternalSyntheticLambda3) this.f$0).mo781invoke(this.f$1);
        }
    }
}
