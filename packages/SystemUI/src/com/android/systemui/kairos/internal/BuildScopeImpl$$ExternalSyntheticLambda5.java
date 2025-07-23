package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.CoalescingMutableEvents;
import com.android.systemui.kairos.LocalNetwork;
import com.android.systemui.kairos.util.Maybe;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class BuildScopeImpl$$ExternalSyntheticLambda5 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BuildScopeImpl$$ExternalSyntheticLambda5(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((AtomicReference) this.f$0).set(Maybe.Absent.INSTANCE);
                return Unit.INSTANCE;
            case 1:
                Ref$ObjectRef ref$ObjectRef = (Ref$ObjectRef) this.f$0;
                T t = ref$ObjectRef.element;
                if (t == 0) {
                    throw new IllegalStateException("[null] already deactivated".toString());
                }
                ((Job) t).cancel(null);
                ref$ObjectRef.element = null;
                return Unit.INSTANCE;
            case 2:
                Unit unit = Unit.INSTANCE;
                ((CoalescingMutableEvents) this.f$0).emit(unit);
                return unit;
            default:
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) this.f$0;
                return new LocalNetwork(buildScopeImpl.getNetwork(), buildScopeImpl.coroutineScope, buildScopeImpl.stateScope.getEndSignal());
        }
    }
}
