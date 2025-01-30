package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$awaitTimeSpentNotDozing$2", m277f = "KeyguardCoordinator.kt", m278l = {173, 175}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class KeyguardCoordinator$awaitTimeSpentNotDozing$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ long $duration;
    private /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardCoordinator$awaitTimeSpentNotDozing$2(long j, Continuation<? super KeyguardCoordinator$awaitTimeSpentNotDozing$2> continuation) {
        super(3, continuation);
        this.$duration = j;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        KeyguardCoordinator$awaitTimeSpentNotDozing$2 keyguardCoordinator$awaitTimeSpentNotDozing$2 = new KeyguardCoordinator$awaitTimeSpentNotDozing$2(this.$duration, (Continuation) obj3);
        keyguardCoordinator$awaitTimeSpentNotDozing$2.L$0 = (FlowCollector) obj;
        keyguardCoordinator$awaitTimeSpentNotDozing$2.Z$0 = booleanValue;
        return keyguardCoordinator$awaitTimeSpentNotDozing$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0046 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        Unit unit;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            if (!this.Z$0) {
                long j = this.$duration;
                this.L$0 = flowCollector;
                this.label = 1;
                if (DelayKt.m2868delayVtjQ1oo(j, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                unit = Unit.INSTANCE;
                this.L$0 = null;
                this.label = 2;
                if (flowCollector.emit(unit, this) == coroutineSingletons) {
                }
            }
        } else if (i == 1) {
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            unit = Unit.INSTANCE;
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(unit, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
