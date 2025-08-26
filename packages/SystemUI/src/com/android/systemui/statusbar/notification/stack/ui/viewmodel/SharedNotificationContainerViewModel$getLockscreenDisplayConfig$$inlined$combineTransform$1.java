package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import java.io.Serializable;
import kotlin.Pair;
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

/* loaded from: classes3.dex */
public final class SharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $calculateSpace$inlined;
    final /* synthetic */ Flow[] $flows;
    private /* synthetic */ Object L$0;
    int label;

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

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0088, code lost:
        
            if (r12.emit(r2, r11) == r0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0099, code lost:
        
            if (r12.emit(r1, r11) == r0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x00cc, code lost:
        
            if (r12.emit(r3, r11) == r0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x00ce, code lost:
        
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
                Serializable[] serializableArr = (Serializable[]) ((Object[]) this.L$1);
                boolean zBooleanValue = ((Boolean) serializableArr[0]).booleanValue();
                Pair pair = (Pair) serializableArr[1];
                boolean zBooleanValue2 = ((Boolean) pair.component1()).booleanValue();
                boolean zBooleanValue3 = ((Boolean) pair.component2()).booleanValue();
                boolean zBooleanValue4 = ((Boolean) serializableArr[2]).booleanValue();
                float fFloatValue = ((Float) serializableArr[3]).floatValue();
                boolean zBooleanValue5 = ((Boolean) serializableArr[5]).booleanValue();
                if (zBooleanValue4) {
                    if (((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).isNeedsToExpandLocksNoti()) {
                        SharedNotificationContainerViewModel.LockscreenDisplayConfig lockscreenDisplayConfig = new SharedNotificationContainerViewModel.LockscreenDisplayConfig(zBooleanValue3, ((Number) this.$calculateSpace$inlined.invoke(new Float(fFloatValue), Boolean.valueOf(zBooleanValue5))).intValue());
                        this.label = 3;
                    }
                } else if (zBooleanValue) {
                    SharedNotificationContainerViewModel.LockscreenDisplayConfig lockscreenDisplayConfig2 = new SharedNotificationContainerViewModel.LockscreenDisplayConfig(zBooleanValue3, ((Number) this.$calculateSpace$inlined.invoke(new Float(fFloatValue), Boolean.valueOf(zBooleanValue5))).intValue());
                    this.label = 1;
                } else if (zBooleanValue2) {
                    SharedNotificationContainerViewModel.LockscreenDisplayConfig lockscreenDisplayConfig3 = new SharedNotificationContainerViewModel.LockscreenDisplayConfig(zBooleanValue3, -1);
                    this.label = 2;
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
