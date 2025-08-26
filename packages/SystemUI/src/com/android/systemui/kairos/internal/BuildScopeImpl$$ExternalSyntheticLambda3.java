package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.BuildScopeKt$$ExternalSyntheticLambda6;
import com.android.systemui.kairos.CoalescingMutableEvents;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class BuildScopeImpl$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ BuildScopeImpl f$0;
    public final /* synthetic */ BuildScopeKt$$ExternalSyntheticLambda6 f$1;
    public final /* synthetic */ Function2 f$2;

    public /* synthetic */ BuildScopeImpl$$ExternalSyntheticLambda3(BuildScopeImpl buildScopeImpl, BuildScopeKt$$ExternalSyntheticLambda6 buildScopeKt$$ExternalSyntheticLambda6, Function2 function2) {
        this.f$0 = buildScopeImpl;
        this.f$1 = buildScopeKt$$ExternalSyntheticLambda6;
        this.f$2 = function2;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        CoalescingMutableEvents coalescingMutableEvents = new CoalescingMutableEvents(null, new BuildScopeImpl$$ExternalSyntheticLambda11(this.f$2, 0), this.f$0.getNetwork(), this.f$1, (InputNode) obj);
        return new Pair(coalescingMutableEvents, new BuildScopeImpl$coalescingEvents$1$1(coalescingMutableEvents));
    }
}
