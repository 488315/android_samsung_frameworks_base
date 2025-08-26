package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3 extends SuspendLambda implements Function3 {
    final /* synthetic */ ConfigurationInteractor $configurationInteractor$inlined;
    final /* synthetic */ UdfpsOverlayInteractor $udfpsOverlayInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DeviceEntryForegroundViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3(Continuation continuation, UdfpsOverlayInteractor udfpsOverlayInteractor, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel, ConfigurationInteractor configurationInteractor) {
        super(3, continuation);
        this.$udfpsOverlayInteractor$inlined = udfpsOverlayInteractor;
        this.this$0 = deviceEntryForegroundViewModel;
        this.$configurationInteractor$inlined = configurationInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3 deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3 = new DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3((Continuation) obj3, this.$udfpsOverlayInteractor$inlined, this.this$0, this.$configurationInteractor$inlined);
        deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3.L$0 = (FlowCollector) obj;
        deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3.L$1 = obj2;
        return deviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowDebounce;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                flowDebounce = FlowKt.debounce(this.$udfpsOverlayInteractor$inlined.iconPadding, this.this$0.context.getResources().getInteger(R.integer.udfps_padding_debounce_duration));
            } else {
                final ReadonlyStateFlow readonlyStateFlow = ((ConfigurationInteractorImpl) this.$configurationInteractor$inlined).scaleForResolution;
                final DeviceEntryForegroundViewModel deviceEntryForegroundViewModel = this.this$0;
                flowDebounce = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ DeviceEntryForegroundViewModel this$0;

                        /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$padding$lambda$5$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = deviceEntryForegroundViewModel;
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
                                Integer num = new Integer(MathKt__MathJVMKt.roundToInt(this.this$0.context.getResources().getDimensionPixelSize(R.dimen.lock_icon_padding) * ((Number) obj).floatValue()));
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                        Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector2, deviceEntryForegroundViewModel), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowDebounce, this) == coroutineSingletons) {
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
