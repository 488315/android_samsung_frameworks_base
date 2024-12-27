package com.android.systemui.telephony.data.repository;

import android.content.Context;
import android.telecom.TelecomManager;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        TelephonyRepositoryImpl$callState$1 telephonyRepositoryImpl$callState$1 = new TelephonyRepositoryImpl$callState$1(this, null);
        conflatedCallbackFlow.getClass();
        final Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(telephonyRepositoryImpl$callState$1);
        this.callState = conflatedCallbackFlow2;
        this.isInCall = FlowKt.stateIn(telecomManager == null ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : new Flow() { // from class: com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ TelephonyRepositoryImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:19:0x0063 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 0
                        r4 = 2
                        r5 = 1
                        if (r2 == 0) goto L3b
                        if (r2 == r5) goto L33
                        if (r2 != r4) goto L2b
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L64
                    L2b:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L33:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L59
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.lang.Number r7 = (java.lang.Number) r7
                        r7.intValue()
                        com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl r7 = r6.this$0
                        kotlinx.coroutines.CoroutineDispatcher r8 = r7.backgroundDispatcher
                        com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$isInCall$1$1 r2 = new com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$isInCall$1$1
                        r2.<init>(r7, r3)
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        r0.L$0 = r6
                        r0.label = r5
                        java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r8, r2, r0)
                        if (r8 != r1) goto L59
                        return r1
                    L59:
                        r0.L$0 = r3
                        r0.label = r4
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L64
                        return r1
                    L64:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
        this.hasTelephonyRadio = context.getPackageManager().hasSystemFeature("android.hardware.telephony");
    }
}
