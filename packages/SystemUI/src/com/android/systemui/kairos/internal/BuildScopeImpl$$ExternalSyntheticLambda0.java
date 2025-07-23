package com.android.systemui.kairos.internal;

import com.android.systemui.KairosBuilderImpl$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda12;
import com.android.systemui.kairos.CoalescingMutableEvents;
import com.android.systemui.kairos.internal.util.UtilKt$invokeOnCancel$1;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class BuildScopeImpl$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ BuildScopeImpl f$1;

    public /* synthetic */ BuildScopeImpl$$ExternalSyntheticLambda0(BuildScopeImpl buildScopeImpl, ContextScope contextScope) {
        this.$r8$classId = 1;
        this.f$1 = buildScopeImpl;
        this.f$0 = contextScope;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return ((Function1) this.f$0).mo779invoke(this.f$1);
            case 1:
                BuildScopeImpl buildScopeImpl = this.f$1;
                buildScopeImpl.getClass();
                CoalescingMutableEvents coalescingMutableEvents = new CoalescingMutableEvents("mutableChildBuildScope", new BuildScopeImpl$$ExternalSyntheticLambda17(), buildScopeImpl.getNetwork(), new BuildScopeImpl$$ExternalSyntheticLambda18(), null, 16, null);
                BuildersKt.launch((ContextScope) this.f$0, EmptyCoroutineContext.INSTANCE, CoroutineStart.UNDISPATCHED, new UtilKt$invokeOnCancel$1(new BuildScopeImpl$$ExternalSyntheticLambda5(coalescingMutableEvents, 2), null));
                return coalescingMutableEvents;
            case 2:
                return ((BuildScope$DefaultImpls$$ExternalSyntheticLambda12) this.f$0).mo779invoke(this.f$1);
            default:
                ((KairosBuilderImpl$$ExternalSyntheticLambda0) this.f$0).mo779invoke(this.f$1);
                return Unit.INSTANCE;
        }
    }

    public /* synthetic */ BuildScopeImpl$$ExternalSyntheticLambda0(Function1 function1, BuildScopeImpl buildScopeImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = function1;
        this.f$1 = buildScopeImpl;
    }
}
