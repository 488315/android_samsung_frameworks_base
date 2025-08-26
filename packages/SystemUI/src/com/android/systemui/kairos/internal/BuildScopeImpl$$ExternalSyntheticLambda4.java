package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.MutableEvents;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class BuildScopeImpl$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BuildScopeImpl$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((BuildScopeImpl$observe$handle$1) this.f$0).dispose();
                return Unit.INSTANCE;
            default:
                MutableEvents mutableEvents = new MutableEvents(((BuildScopeImpl) this.f$0).getNetwork(), (InputNode) obj);
                return new Pair(mutableEvents, new BuildScopeImpl$events$1$1(mutableEvents));
        }
    }
}
