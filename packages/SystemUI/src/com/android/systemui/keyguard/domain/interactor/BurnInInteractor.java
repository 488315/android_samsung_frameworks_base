package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.doze.util.BurnInHelperKt;
import com.android.systemui.doze.util.BurnInHelperWrapper;
import com.android.systemui.keyguard.shared.model.BurnInModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class BurnInInteractor {
    public final BurnInHelperWrapper burnInHelperWrapper;
    public final ConfigurationInteractor configurationInteractor;
    public final Context context;
    public final ReadonlyStateFlow deviceEntryIconXOffset;
    public final ReadonlyStateFlow deviceEntryIconYOffset;
    public final KeyguardInteractor keyguardInteractor;
    public final ReadonlyStateFlow udfpsProgress;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.BurnInInteractor$burnIn$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        /* synthetic */ int I$0;
        /* synthetic */ int I$1;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            int iIntValue = ((Number) obj).intValue();
            int iIntValue2 = ((Number) obj2).intValue();
            AnonymousClass2 anonymousClass2 = BurnInInteractor.this.new AnonymousClass2((Continuation) obj3);
            anonymousClass2.I$0 = iIntValue;
            anonymousClass2.I$1 = iIntValue2;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            int i = this.I$0;
            int i2 = this.I$1;
            BurnInInteractor.this.burnInHelperWrapper.getClass();
            return new BurnInModel(i, i2, BurnInHelperKt.zigzag(System.currentTimeMillis() / 60000.0f, 0.2f, 181.0f) + 0.8f, false, 8, null);
        }
    }

    public BurnInInteractor(Context context, BurnInHelperWrapper burnInHelperWrapper, CoroutineScope coroutineScope, ConfigurationInteractor configurationInteractor, KeyguardInteractor keyguardInteractor) {
        this.context = context;
        this.burnInHelperWrapper = burnInHelperWrapper;
        this.configurationInteractor = configurationInteractor;
        this.keyguardInteractor = keyguardInteractor;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(((ConfigurationInteractorImpl) configurationInteractor).scaleForResolution, new BurnInInteractor$burnInOffsetDefinedInPixels$$inlined$flatMapLatest$1(null, this, R.dimen.udfps_burn_in_offset_x, true));
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.deviceEntryIconXOffset = FlowKt.stateIn(channelFlowTransformLatestTransformLatest, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.deviceEntryIconYOffset = FlowKt.stateIn(FlowKt.transformLatest(((ConfigurationInteractorImpl) configurationInteractor).scaleForResolution, new BurnInInteractor$burnInOffsetDefinedInPixels$$inlined$flatMapLatest$1(null, this, R.dimen.udfps_burn_in_offset_y, false)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        ChannelFlowTransformLatest channelFlowTransformLatestMapLatest = FlowKt.mapLatest(keyguardInteractor.dozeTimeTick, new BurnInInteractor$udfpsProgress$1(this, null));
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        burnInHelperWrapper.getClass();
        this.udfpsProgress = FlowKt.stateIn(channelFlowTransformLatestMapLatest, coroutineScope, startedWhileSubscribedWhileSubscribed$default, Float.valueOf(BurnInHelperKt.zigzag(System.currentTimeMillis() / 60000.0f, 1.0f, 89.0f)));
    }

    public final Flow burnIn(final int i) {
        ConfigurationInteractor configurationInteractor = this.configurationInteractor;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(((ConfigurationInteractorImpl) configurationInteractor).onAnyConfigurationChange, new BurnInInteractor$burnInOffset$$inlined$flatMapLatest$1(null, this, R.dimen.burn_in_prevention_offset_x, true));
        final ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest2 = FlowKt.transformLatest(((ConfigurationInteractorImpl) configurationInteractor).onAnyConfigurationChange, new BurnInInteractor$burnInOffset$$inlined$flatMapLatest$1(null, this, i, false));
        return FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(channelFlowTransformLatestTransformLatest, new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.BurnInInteractor$burnIn$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.BurnInInteractor$burnIn$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ int $yDimenResourceId$inlined;
                public final /* synthetic */ BurnInInteractor this$0;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.BurnInInteractor$burnIn$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, BurnInInteractor burnInInteractor, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = burnInInteractor;
                    this.$yDimenResourceId$inlined = i;
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
                        Integer num = new Integer((((Number) obj).intValue() * 2) - this.this$0.context.getResources().getDimensionPixelSize(this.$yDimenResourceId$inlined));
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelFlowTransformLatestTransformLatest2.collect(new AnonymousClass2(flowCollector, this, i), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new AnonymousClass2(null)));
    }
}
