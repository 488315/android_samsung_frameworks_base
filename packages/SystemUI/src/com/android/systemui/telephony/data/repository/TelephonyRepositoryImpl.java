package com.android.systemui.telephony.data.repository;

import android.content.Context;
import android.telecom.TelecomManager;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class TelephonyRepositoryImpl implements TelephonyRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final Flow callState;
    public final boolean hasTelephonyRadio;
    public final ReadonlyStateFlow isInCall;
    public final TelephonyListenerManager manager;
    public final TelecomManager telecomManager;

    public TelephonyRepositoryImpl(CoroutineScope coroutineScope, Context context, CoroutineDispatcher coroutineDispatcher, TelephonyListenerManager telephonyListenerManager, TelecomManager telecomManager) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.manager = telephonyListenerManager;
        this.telecomManager = telecomManager;
        final Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new TelephonyRepositoryImpl$callState$1(this, null));
        this.callState = flowConflatedCallbackFlow;
        this.isInCall = FlowKt.stateIn(telecomManager == null ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : new Flow() { // from class: com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ TelephonyRepositoryImpl this$0;

                /* renamed from: com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, TelephonyRepositoryImpl telephonyRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = telephonyRepositoryImpl;
                }

                /* JADX WARN: Code restructure failed: missing block: B:20:0x0061, code lost:
                
                    if (r6.emit(r8, r0) == r1) goto L21;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objWithContext = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objWithContext);
                        ((Number) obj).intValue();
                        TelephonyRepositoryImpl telephonyRepositoryImpl = this.this$0;
                        CoroutineDispatcher coroutineDispatcher = telephonyRepositoryImpl.backgroundDispatcher;
                        TelephonyRepositoryImpl$isInCall$1$1 telephonyRepositoryImpl$isInCall$1$1 = new TelephonyRepositoryImpl$isInCall$1$1(telephonyRepositoryImpl, null);
                        flowCollector = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector;
                        anonymousClass1.label = 1;
                        objWithContext = BuildersKt.withContext(coroutineDispatcher, telephonyRepositoryImpl$isInCall$1$1, anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(objWithContext);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(objWithContext);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowConflatedCallbackFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
        this.hasTelephonyRadio = context.getPackageManager().hasSystemFeature("android.hardware.telephony");
    }
}
