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
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes.dex */
public final class EngineeringModeManagerWrapper {
    public final Flow callbackFlow;
    public final Context context;
    public final Lazy emm$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.keyguard.emm.EngineeringModeManagerWrapper$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new EngineeringModeManager(this.f$0.context);
        }
    });
    public boolean isCaptureEnabled;
    public final KeyguardStateController keyguardStateController;

    /* renamed from: com.android.keyguard.emm.EngineeringModeManagerWrapper$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.keyguard.emm.EngineeringModeManagerWrapper$1$1, reason: invalid class name and collision with other inner class name */
        public final class C00481 implements FlowCollector {
            public final /* synthetic */ EngineeringModeManagerWrapper this$0;

            public C00481(EngineeringModeManagerWrapper engineeringModeManagerWrapper) {
                this.this$0 = engineeringModeManagerWrapper;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                return emit(((Boolean) obj).booleanValue(), continuation);
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(boolean z, Continuation continuation) throws Throwable {
                EngineeringModeManagerWrapper$1$1$emit$1 engineeringModeManagerWrapper$1$1$emit$1;
                EngineeringModeManagerWrapper engineeringModeManagerWrapper;
                if (continuation instanceof EngineeringModeManagerWrapper$1$1$emit$1) {
                    engineeringModeManagerWrapper$1$1$emit$1 = (EngineeringModeManagerWrapper$1$1$emit$1) continuation;
                    int i = engineeringModeManagerWrapper$1$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        engineeringModeManagerWrapper$1$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        engineeringModeManagerWrapper$1$1$emit$1 = new EngineeringModeManagerWrapper$1$1$emit$1(this, continuation);
                    }
                }
                Object objWithContext = engineeringModeManagerWrapper$1$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = engineeringModeManagerWrapper$1$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(objWithContext);
                    engineeringModeManagerWrapper = this.this$0;
                    engineeringModeManagerWrapper$1$1$emit$1.L$0 = engineeringModeManagerWrapper;
                    engineeringModeManagerWrapper$1$1$emit$1.label = 1;
                    engineeringModeManagerWrapper.getClass();
                    objWithContext = BuildersKt.withContext(Dispatchers.Default, new EngineeringModeManagerWrapper$getEmmStatus$2(engineeringModeManagerWrapper, null), engineeringModeManagerWrapper$1$1$emit$1);
                    if (objWithContext == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    engineeringModeManagerWrapper = (EngineeringModeManagerWrapper) engineeringModeManagerWrapper$1$1$emit$1.L$0;
                    ResultKt.throwOnFailure(objWithContext);
                }
                engineeringModeManagerWrapper.isCaptureEnabled = ((Boolean) objWithContext).booleanValue();
                return Unit.INSTANCE;
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
                C00481 c00481 = new C00481(engineeringModeManagerWrapper);
                this.label = 1;
                if (flow.collect(c00481, this) == coroutineSingletons) {
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
