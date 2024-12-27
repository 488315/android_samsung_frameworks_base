package com.android.keyguard.emm;

import android.content.Context;
import com.android.systemui.LsRune;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class EngineeringModeManagerWrapper {
    public final Flow callbackFlow;
    public final Context context;
    public final Lazy emm$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.keyguard.emm.EngineeringModeManagerWrapper$emm$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new EngineeringModeManager(EngineeringModeManagerWrapper.this.context);
        }
    });
    public boolean isCaptureEnabled;
    public final KeyguardStateController keyguardStateController;

    /* renamed from: com.android.keyguard.emm.EngineeringModeManagerWrapper$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.keyguard.emm.EngineeringModeManagerWrapper$1$1, reason: invalid class name and collision with other inner class name */
        public final class C00231 implements FlowCollector {
            public final /* synthetic */ EngineeringModeManagerWrapper this$0;

            public C00231(EngineeringModeManagerWrapper engineeringModeManagerWrapper) {
                this.this$0 = engineeringModeManagerWrapper;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                return emit(((Boolean) obj).booleanValue(), continuation);
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(boolean r4, kotlin.coroutines.Continuation r5) {
                /*
                    r3 = this;
                    boolean r4 = r5 instanceof com.android.keyguard.emm.EngineeringModeManagerWrapper$1$1$emit$1
                    if (r4 == 0) goto L13
                    r4 = r5
                    com.android.keyguard.emm.EngineeringModeManagerWrapper$1$1$emit$1 r4 = (com.android.keyguard.emm.EngineeringModeManagerWrapper$1$1$emit$1) r4
                    int r0 = r4.label
                    r1 = -2147483648(0xffffffff80000000, float:-0.0)
                    r2 = r0 & r1
                    if (r2 == 0) goto L13
                    int r0 = r0 - r1
                    r4.label = r0
                    goto L18
                L13:
                    com.android.keyguard.emm.EngineeringModeManagerWrapper$1$1$emit$1 r4 = new com.android.keyguard.emm.EngineeringModeManagerWrapper$1$1$emit$1
                    r4.<init>(r3, r5)
                L18:
                    java.lang.Object r5 = r4.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r1 = r4.label
                    r2 = 1
                    if (r1 == 0) goto L33
                    if (r1 != r2) goto L2b
                    java.lang.Object r3 = r4.L$0
                    com.android.keyguard.emm.EngineeringModeManagerWrapper r3 = (com.android.keyguard.emm.EngineeringModeManagerWrapper) r3
                    kotlin.ResultKt.throwOnFailure(r5)
                    goto L4e
                L2b:
                    java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                    java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                    r3.<init>(r4)
                    throw r3
                L33:
                    kotlin.ResultKt.throwOnFailure(r5)
                    com.android.keyguard.emm.EngineeringModeManagerWrapper r3 = r3.this$0
                    r4.L$0 = r3
                    r4.label = r2
                    r3.getClass()
                    kotlinx.coroutines.scheduling.DefaultScheduler r5 = kotlinx.coroutines.Dispatchers.Default
                    com.android.keyguard.emm.EngineeringModeManagerWrapper$getEmmStatus$2 r1 = new com.android.keyguard.emm.EngineeringModeManagerWrapper$getEmmStatus$2
                    r2 = 0
                    r1.<init>(r3, r2)
                    java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r5, r1, r4)
                    if (r5 != r0) goto L4e
                    return r0
                L4e:
                    java.lang.Boolean r5 = (java.lang.Boolean) r5
                    boolean r4 = r5.booleanValue()
                    r3.isCaptureEnabled = r4
                    kotlin.Unit r3 = kotlin.Unit.INSTANCE
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.emm.EngineeringModeManagerWrapper.AnonymousClass1.C00231.emit(boolean, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EngineeringModeManagerWrapper.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                EngineeringModeManagerWrapper engineeringModeManagerWrapper = EngineeringModeManagerWrapper.this;
                Flow flow = engineeringModeManagerWrapper.callbackFlow;
                C00231 c00231 = new C00231(engineeringModeManagerWrapper);
                this.label = 1;
                if (flow.collect(c00231, this) == coroutineSingletons) {
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

    public EngineeringModeManagerWrapper(Context context, CoroutineScope coroutineScope, KeyguardStateController keyguardStateController) {
        this.context = context;
        this.keyguardStateController = keyguardStateController;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        EngineeringModeManagerWrapper$callbackFlow$1 engineeringModeManagerWrapper$callbackFlow$1 = new EngineeringModeManagerWrapper$callbackFlow$1(this, null);
        conflatedCallbackFlow.getClass();
        this.callbackFlow = FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(engineeringModeManagerWrapper$callbackFlow$1));
        if (LsRune.KEYGUARD_EM_TOKEN_CAPTURE_WINDOW) {
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
        }
    }
}
