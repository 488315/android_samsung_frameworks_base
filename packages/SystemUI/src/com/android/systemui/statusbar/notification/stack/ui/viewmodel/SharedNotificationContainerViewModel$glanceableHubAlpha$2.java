package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class SharedNotificationContainerViewModel$glanceableHubAlpha$2 extends SuspendLambda implements Function6 {
    /* synthetic */ float F$0;
    private /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public SharedNotificationContainerViewModel$glanceableHubAlpha$2(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
        boolean zBooleanValue3 = ((Boolean) obj4).booleanValue();
        float fFloatValue = ((Number) obj5).floatValue();
        SharedNotificationContainerViewModel$glanceableHubAlpha$2 sharedNotificationContainerViewModel$glanceableHubAlpha$2 = new SharedNotificationContainerViewModel$glanceableHubAlpha$2((Continuation) obj6);
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.L$0 = (FlowCollector) obj;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.Z$0 = zBooleanValue;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.Z$1 = zBooleanValue2;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.Z$2 = zBooleanValue3;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.F$0 = fFloatValue;
        return sharedNotificationContainerViewModel$glanceableHubAlpha$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x003d, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x004d, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x005d, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005f, code lost:
    
        return r0;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            boolean z = this.Z$0;
            boolean z2 = this.Z$1;
            boolean z3 = this.Z$2;
            float f = this.F$0;
            if ((z || z3) && !z2) {
                Float f2 = new Float(0.0f);
                this.label = 1;
            } else if (z) {
                Float f3 = new Float(f);
                this.label = 2;
            } else {
                Float f4 = new Float(1.0f);
                this.label = 3;
            }
        } else {
            if (i != 1 && i != 2 && i != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
