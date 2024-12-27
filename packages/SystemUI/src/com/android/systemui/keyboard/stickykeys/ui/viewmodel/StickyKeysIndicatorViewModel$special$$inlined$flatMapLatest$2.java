package com.android.systemui.keyboard.stickykeys.ui.viewmodel;

import com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepository;
import com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class StickyKeysIndicatorViewModel$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ StickyKeysRepository $stickyKeysRepository$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StickyKeysIndicatorViewModel$special$$inlined$flatMapLatest$2(Continuation continuation, StickyKeysRepository stickyKeysRepository) {
        super(3, continuation);
        this.$stickyKeysRepository$inlined = stickyKeysRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StickyKeysIndicatorViewModel$special$$inlined$flatMapLatest$2 stickyKeysIndicatorViewModel$special$$inlined$flatMapLatest$2 = new StickyKeysIndicatorViewModel$special$$inlined$flatMapLatest$2((Continuation) obj3, this.$stickyKeysRepository$inlined);
        stickyKeysIndicatorViewModel$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        stickyKeysIndicatorViewModel$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return stickyKeysIndicatorViewModel$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = ((Boolean) this.L$1).booleanValue() ? ((StickyKeysRepositoryImpl) this.$stickyKeysRepository$inlined).stickyKeys : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(MapsKt__MapsKt.emptyMap());
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
