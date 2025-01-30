package com.android.keyguard.emm;

import android.content.Context;
import com.android.systemui.LsRune;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.keyguard.emm.EngineeringModeManagerWrapper$1", m277f = "EngineeringModeManagerWrapper.kt", m278l = {55}, m279m = "invokeSuspend")
    /* renamed from: com.android.keyguard.emm.EngineeringModeManagerWrapper$1 */
    public static final class C08601 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.keyguard.emm.EngineeringModeManagerWrapper$1$1, reason: invalid class name */
        public final class AnonymousClass1 implements FlowCollector {
            public final /* synthetic */ EngineeringModeManagerWrapper this$0;

            public AnonymousClass1(EngineeringModeManagerWrapper engineeringModeManagerWrapper) {
                this.this$0 = engineeringModeManagerWrapper;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                ((Boolean) obj).booleanValue();
                return emit(continuation);
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(Continuation continuation) {
                EngineeringModeManagerWrapper$1$1$emit$1 engineeringModeManagerWrapper$1$1$emit$1;
                int i;
                EngineeringModeManagerWrapper engineeringModeManagerWrapper;
                if (continuation instanceof EngineeringModeManagerWrapper$1$1$emit$1) {
                    engineeringModeManagerWrapper$1$1$emit$1 = (EngineeringModeManagerWrapper$1$1$emit$1) continuation;
                    int i2 = engineeringModeManagerWrapper$1$1$emit$1.label;
                    if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                        engineeringModeManagerWrapper$1$1$emit$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                        Object obj = engineeringModeManagerWrapper$1$1$emit$1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        i = engineeringModeManagerWrapper$1$1$emit$1.label;
                        if (i != 0) {
                            ResultKt.throwOnFailure(obj);
                            engineeringModeManagerWrapper = this.this$0;
                            engineeringModeManagerWrapper$1$1$emit$1.L$0 = engineeringModeManagerWrapper;
                            engineeringModeManagerWrapper$1$1$emit$1.label = 1;
                            engineeringModeManagerWrapper.getClass();
                            obj = BuildersKt.withContext(Dispatchers.Default, new EngineeringModeManagerWrapper$getEmmStatus$2(engineeringModeManagerWrapper, null), engineeringModeManagerWrapper$1$1$emit$1);
                            if (obj == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            engineeringModeManagerWrapper = (EngineeringModeManagerWrapper) engineeringModeManagerWrapper$1$1$emit$1.L$0;
                            ResultKt.throwOnFailure(obj);
                        }
                        engineeringModeManagerWrapper.isCaptureEnabled = ((Boolean) obj).booleanValue();
                        return Unit.INSTANCE;
                    }
                }
                engineeringModeManagerWrapper$1$1$emit$1 = new EngineeringModeManagerWrapper$1$1$emit$1(this, continuation);
                Object obj2 = engineeringModeManagerWrapper$1$1$emit$1.result;
                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = engineeringModeManagerWrapper$1$1$emit$1.label;
                if (i != 0) {
                }
                engineeringModeManagerWrapper.isCaptureEnabled = ((Boolean) obj2).booleanValue();
                return Unit.INSTANCE;
            }
        }

        public C08601(Continuation<? super C08601> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EngineeringModeManagerWrapper.this.new C08601(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08601) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                EngineeringModeManagerWrapper engineeringModeManagerWrapper = EngineeringModeManagerWrapper.this;
                Flow flow = engineeringModeManagerWrapper.callbackFlow;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(engineeringModeManagerWrapper);
                this.label = 1;
                if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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
        this.callbackFlow = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(engineeringModeManagerWrapper$callbackFlow$1));
        if (LsRune.KEYGUARD_EM_TOKEN_CAPTURE_WINDOW) {
            BuildersKt.launch$default(coroutineScope, null, null, new C08601(null), 3);
        }
    }
}
