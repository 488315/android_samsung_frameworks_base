package com.android.systemui.common.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.common.data.repository.PackageChangeRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class PackageChangeInteractor$packageChanged$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ String $packageName$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PackageChangeInteractor this$0;

    public PackageChangeInteractor$packageChanged$$inlined$flatMapLatest$1(Continuation continuation, PackageChangeInteractor packageChangeInteractor, String str) {
        super(3, continuation);
        this.this$0 = packageChangeInteractor;
        this.$packageName$inlined = str;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PackageChangeInteractor$packageChanged$$inlined$flatMapLatest$1 packageChangeInteractor$packageChanged$$inlined$flatMapLatest$1 = new PackageChangeInteractor$packageChanged$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$packageName$inlined);
        packageChangeInteractor$packageChanged$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        packageChangeInteractor$packageChanged$$inlined$flatMapLatest$1.L$1 = obj2;
        return packageChangeInteractor$packageChanged$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int intValue = ((Number) this.L$1).intValue();
            PackageChangeInteractor packageChangeInteractor = this.this$0;
            UserHandle of = UserHandle.of(intValue);
            PackageChangeInteractor$packageChangedInternal$$inlined$filter$1 packageChangeInteractor$packageChangedInternal$$inlined$filter$1 = new PackageChangeInteractor$packageChangedInternal$$inlined$filter$1(((PackageChangeRepositoryImpl) packageChangeInteractor.packageChangeRepository).packageChanged(of), this.$packageName$inlined);
            this.label = 1;
            if (FlowKt.emitAll(this, packageChangeInteractor$packageChangedInternal$$inlined$filter$1, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
