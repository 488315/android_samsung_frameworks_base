package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.BuildScopeImpl$observe$outputNode$2$scope$1;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class BuildScopeKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BuildScopeKt$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [kotlin.coroutines.jvm.internal.SuspendLambda, kotlin.jvm.functions.Function2] */
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BuildScopeImpl$observe$outputNode$2$scope$1 buildScopeImpl$observe$outputNode$2$scope$1 = (BuildScopeImpl$observe$outputNode$2$scope$1) ((EffectScope) obj);
                buildScopeImpl$observe$outputNode$2$scope$1.async(EmptyCoroutineContext.INSTANCE, CoroutineStart.DEFAULT, new BuildScopeKt$asyncEffect$job$1$1((CompletableDeferredImpl) this.f$0, (SuspendLambda) this.f$1, null));
                return Unit.INSTANCE;
            case 1:
                ((DisposableHandle) this.f$0).dispose();
                ((Job) this.f$1).cancel(null);
                return Unit.INSTANCE;
            default:
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                return buildScopeImpl.observe(buildScopeImpl.getNow(), (CoroutineContext) this.f$0, new BuildScopeKt$$ExternalSyntheticLambda7((Function1) this.f$1, 0));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ BuildScopeKt$$ExternalSyntheticLambda0(CompletableDeferredImpl completableDeferredImpl, Function2 function2) {
        this.$r8$classId = 0;
        this.f$0 = completableDeferredImpl;
        this.f$1 = (SuspendLambda) function2;
    }
}
