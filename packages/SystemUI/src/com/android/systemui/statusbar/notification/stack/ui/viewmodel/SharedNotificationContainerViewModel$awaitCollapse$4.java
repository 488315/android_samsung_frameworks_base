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

/* loaded from: classes3.dex */
final class SharedNotificationContainerViewModel$awaitCollapse$4 extends SuspendLambda implements Function3 {
    final /* synthetic */ Ref$BooleanRef $aodTransitionIsComplete;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SharedNotificationContainerViewModel$awaitCollapse$4(Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
        super(3, continuation);
        this.$aodTransitionIsComplete = ref$BooleanRef;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SharedNotificationContainerViewModel$awaitCollapse$4 sharedNotificationContainerViewModel$awaitCollapse$4 = new SharedNotificationContainerViewModel$awaitCollapse$4(this.$aodTransitionIsComplete, (Continuation) obj3);
        sharedNotificationContainerViewModel$awaitCollapse$4.L$0 = (FlowCollector) obj;
        sharedNotificationContainerViewModel$awaitCollapse$4.L$1 = (Pair) obj2;
        return sharedNotificationContainerViewModel$awaitCollapse$4.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004e, code lost:
    
        if (r10.emit(r1, r9) == r0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0063, code lost:
    
        if (r10.emit(r1, r9) == r0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0065, code lost:
    
        return r0;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        boolean z = false;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Pair pair = (Pair) this.L$1;
            boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
            boolean zBooleanValue2 = ((Boolean) pair.component2()).booleanValue();
            Ref$BooleanRef ref$BooleanRef = this.$aodTransitionIsComplete;
            if (ref$BooleanRef.element || zBooleanValue2) {
                if (zBooleanValue2) {
                    ref$BooleanRef.element = false;
                } else if (zBooleanValue) {
                    Boolean bool = Boolean.TRUE;
                    this.L$0 = null;
                    this.label = 2;
                }
                z = true;
            } else {
                ref$BooleanRef.element = true;
                Boolean bool2 = Boolean.FALSE;
                this.L$0 = null;
                this.label = 1;
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
