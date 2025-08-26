package com.android.systemui.deviceentry.domain.interactor;

import android.R;
import com.android.systemui.deviceentry.shared.model.FingerprintFailureMessage;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class BiometricMessageInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ DeviceEntryFingerprintAuthInteractor $fingerprintAuthInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BiometricMessageInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricMessageInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, BiometricMessageInteractor biometricMessageInteractor) {
        super(3, continuation);
        this.$fingerprintAuthInteractor$inlined = deviceEntryFingerprintAuthInteractor;
        this.this$0 = biometricMessageInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricMessageInteractor$special$$inlined$flatMapLatest$1 biometricMessageInteractor$special$$inlined$flatMapLatest$1 = new BiometricMessageInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$fingerprintAuthInteractor$inlined, this.this$0);
        biometricMessageInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        biometricMessageInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return biometricMessageInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final boolean zBooleanValue = ((Boolean) this.L$1).booleanValue();
            final Flow flowSample = FlowKt.sample(this.$fingerprintAuthInteractor$inlined.fingerprintFailure, this.this$0.biometricSettingsInteractor.fingerprintAuthCurrentlyAllowed);
            final Flow flow = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$filter$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        Object L$1;
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

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i = anonymousClass1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                anonymousClass1.label = i - Integer.MIN_VALUE;
                            } else {
                                anonymousClass1 = new AnonymousClass1(continuation);
                            }
                        }
                        Object obj2 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            if (((Boolean) obj).booleanValue()) {
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            }
                        } else {
                            if (i2 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object objCollect = flowSample.collect(new AnonymousClass2(flowCollector2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            final BiometricMessageInteractor biometricMessageInteractor = this.this$0;
            Flow flow2 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ boolean $isUdfps$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ BiometricMessageInteractor this$0;

                    /* renamed from: com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor$fingerprintFailMessage$lambda$9$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, boolean z, BiometricMessageInteractor biometricMessageInteractor) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$isUdfps$inlined = z;
                        this.this$0 = biometricMessageInteractor;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i = anonymousClass1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                anonymousClass1.label = i - Integer.MIN_VALUE;
                            } else {
                                anonymousClass1 = new AnonymousClass1(continuation);
                            }
                        }
                        Object obj2 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            ((Boolean) obj).getClass();
                            boolean z = this.$isUdfps$inlined;
                            BiometricMessageInteractor biometricMessageInteractor = this.this$0;
                            FingerprintFailureMessage fingerprintFailureMessage = new FingerprintFailureMessage(z ? biometricMessageInteractor.resources.getString(R.string.mediasize_iso_c0) : biometricMessageInteractor.resources.getString(R.string.mediasize_iso_a5));
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(fingerprintFailureMessage, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i2 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object objCollect = flow.collect(new AnonymousClass2(flowCollector2, zBooleanValue, biometricMessageInteractor), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (kotlinx.coroutines.flow.FlowKt.emitAll(flowCollector, flow2, this) == coroutineSingletons) {
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
