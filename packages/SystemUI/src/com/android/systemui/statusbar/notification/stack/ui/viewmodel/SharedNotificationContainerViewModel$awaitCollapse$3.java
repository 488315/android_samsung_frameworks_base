package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SharedNotificationContainerViewModel$awaitCollapse$3 extends SuspendLambda implements Function3 {
    final /* synthetic */ Ref$BooleanRef $aodTransitionIsComplete;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SharedNotificationContainerViewModel$awaitCollapse$3(Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
        super(3, continuation);
        this.$aodTransitionIsComplete = ref$BooleanRef;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SharedNotificationContainerViewModel$awaitCollapse$3 sharedNotificationContainerViewModel$awaitCollapse$3 = new SharedNotificationContainerViewModel$awaitCollapse$3(this.$aodTransitionIsComplete, (Continuation) obj3);
        sharedNotificationContainerViewModel$awaitCollapse$3.L$0 = (FlowCollector) obj;
        sharedNotificationContainerViewModel$awaitCollapse$3.L$1 = (Pair) obj2;
        return sharedNotificationContainerViewModel$awaitCollapse$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        boolean z = false;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Pair pair = (Pair) this.L$1;
            boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
            boolean booleanValue2 = ((Boolean) pair.component2()).booleanValue();
            Ref$BooleanRef ref$BooleanRef = this.$aodTransitionIsComplete;
            if (ref$BooleanRef.element || booleanValue2) {
                if (booleanValue2) {
                    ref$BooleanRef.element = false;
                } else if (booleanValue) {
                    Boolean bool = Boolean.TRUE;
                    this.L$0 = null;
                    this.label = 2;
                    if (flowCollector.emit(bool, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                z = true;
            } else {
                ref$BooleanRef.element = true;
                Boolean bool2 = Boolean.FALSE;
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(bool2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1 && i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Boolean.valueOf(z);
    }
}
