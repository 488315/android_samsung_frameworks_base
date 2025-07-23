package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import java.io.Serializable;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $calculateSpace$inlined;
    final /* synthetic */ Flow[] $flows;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1$2, reason: invalid class name */
    public final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ Function2 $calculateSpace$inlined;
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Continuation continuation, Function2 function2) {
            super(3, continuation);
            this.$calculateSpace$inlined = function2;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj3, this.$calculateSpace$inlined);
            anonymousClass2.L$0 = (FlowCollector) obj;
            anonymousClass2.L$1 = (Object[]) obj2;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0088, code lost:
        
            if (r12.emit(r2, r11) == r0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x00ce, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0099, code lost:
        
            if (r12.emit(r1, r11) == r0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x00cc, code lost:
        
            if (r12.emit(r3, r11) == r0) goto L25;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r12) {
            /*
                r11 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r11.label
                r2 = 3
                r3 = 2
                r4 = 1
                if (r1 == 0) goto L1d
                if (r1 == r4) goto L18
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                goto L18
            L10:
                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                r11.<init>(r12)
                throw r11
            L18:
                kotlin.ResultKt.throwOnFailure(r12)
                goto Lcf
            L1d:
                kotlin.ResultKt.throwOnFailure(r12)
                java.lang.Object r12 = r11.L$0
                kotlinx.coroutines.flow.FlowCollector r12 = (kotlinx.coroutines.flow.FlowCollector) r12
                java.lang.Object r1 = r11.L$1
                java.lang.Object[] r1 = (java.lang.Object[]) r1
                java.io.Serializable[] r1 = (java.io.Serializable[]) r1
                r5 = 0
                r5 = r1[r5]
                java.lang.Boolean r5 = (java.lang.Boolean) r5
                boolean r5 = r5.booleanValue()
                r6 = r1[r4]
                kotlin.Pair r6 = (kotlin.Pair) r6
                java.lang.Object r7 = r6.component1()
                java.lang.Boolean r7 = (java.lang.Boolean) r7
                boolean r7 = r7.booleanValue()
                java.lang.Object r6 = r6.component2()
                java.lang.Boolean r6 = (java.lang.Boolean) r6
                boolean r6 = r6.booleanValue()
                r8 = r1[r3]
                java.lang.Boolean r8 = (java.lang.Boolean) r8
                boolean r8 = r8.booleanValue()
                r9 = r1[r2]
                java.lang.Float r9 = (java.lang.Float) r9
                float r9 = r9.floatValue()
                r10 = 5
                r1 = r1[r10]
                java.lang.Boolean r1 = (java.lang.Boolean) r1
                boolean r1 = r1.booleanValue()
                if (r8 != 0) goto L9c
                if (r5 == 0) goto L8b
                com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$LockscreenDisplayConfig r2 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$LockscreenDisplayConfig
                kotlin.jvm.functions.Function2 r3 = r11.$calculateSpace$inlined
                java.lang.Float r5 = new java.lang.Float
                r5.<init>(r9)
                java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
                java.lang.Object r1 = r3.invoke(r5, r1)
                java.lang.Number r1 = (java.lang.Number) r1
                int r1 = r1.intValue()
                r2.<init>(r6, r1)
                r11.label = r4
                java.lang.Object r11 = r12.emit(r2, r11)
                if (r11 != r0) goto Lcf
                goto Lce
            L8b:
                if (r7 == 0) goto Lcf
                com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$LockscreenDisplayConfig r1 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$LockscreenDisplayConfig
                r2 = -1
                r1.<init>(r6, r2)
                r11.label = r3
                java.lang.Object r11 = r12.emit(r1, r11)
                if (r11 != r0) goto Lcf
                goto Lce
            L9c:
                com.android.systemui.Dependency r3 = com.android.systemui.Dependency.sDependency
                java.lang.Class<com.android.systemui.statusbar.notification.stack.AmbientState> r4 = com.android.systemui.statusbar.notification.stack.AmbientState.class
                java.lang.Object r3 = r3.getDependencyInner(r4)
                com.android.systemui.statusbar.notification.stack.AmbientState r3 = (com.android.systemui.statusbar.notification.stack.AmbientState) r3
                boolean r3 = r3.isNeedsToExpandLocksNoti()
                if (r3 == 0) goto Lcf
                com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$LockscreenDisplayConfig r3 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$LockscreenDisplayConfig
                kotlin.jvm.functions.Function2 r4 = r11.$calculateSpace$inlined
                java.lang.Float r5 = new java.lang.Float
                r5.<init>(r9)
                java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
                java.lang.Object r1 = r4.invoke(r5, r1)
                java.lang.Number r1 = (java.lang.Number) r1
                int r1 = r1.intValue()
                r3.<init>(r6, r1)
                r11.label = r2
                java.lang.Object r11 = r12.emit(r3, r11)
                if (r11 != r0) goto Lcf
            Lce:
                return r0
            Lcf:
                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1(Flow[] flowArr, Continuation continuation, Function2 function2) {
        super(2, continuation);
        this.$flows = flowArr;
        this.$calculateSpace$inlined = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1 sharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1 = new SharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1(this.$flows, continuation, this.$calculateSpace$inlined);
        sharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1.L$0 = obj;
        return sharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final Flow[] flowArr = this.$flows;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1.1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new Serializable[flowArr.length];
                }
            };
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null, this.$calculateSpace$inlined);
            this.label = 1;
            if (CombineKt.combineInternal(flowArr, function0, anonymousClass2, flowCollector, this) == coroutineSingletons) {
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
