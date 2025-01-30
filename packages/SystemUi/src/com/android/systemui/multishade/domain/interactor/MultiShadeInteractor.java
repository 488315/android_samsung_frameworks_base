package com.android.systemui.multishade.domain.interactor;

import com.android.systemui.multishade.data.model.MultiShadeInteractionModel;
import com.android.systemui.multishade.data.remoteproxy.MultiShadeInputProxy;
import com.android.systemui.multishade.data.repository.MultiShadeRepository;
import com.android.systemui.multishade.shared.model.ProxiedInputModel;
import com.android.systemui.multishade.shared.model.ShadeConfig;
import com.android.systemui.multishade.shared.model.ShadeId;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MultiShadeInteractor {
    public final MultiShadeInputProxy inputProxy;
    public final Flow isAnyShadeExpanded;
    public final ChannelFlowTransformLatest maxShadeExpansion;
    public final ReadonlySharedFlow processedProxiedInput;
    public final MultiShadeRepository repository;
    public final ReadonlyStateFlow shadeConfig;

    public MultiShadeInteractor(CoroutineScope coroutineScope, MultiShadeRepository multiShadeRepository, MultiShadeInputProxy multiShadeInputProxy) {
        this.repository = multiShadeRepository;
        this.inputProxy = multiShadeInputProxy;
        ReadonlyStateFlow readonlyStateFlow = multiShadeRepository.shadeConfig;
        this.shadeConfig = readonlyStateFlow;
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(readonlyStateFlow, new MultiShadeInteractor$special$$inlined$flatMapLatest$1(null, this));
        this.maxShadeExpansion = transformLatest;
        this.isAnyShadeExpanded = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$special$$inlined$map$1$2 */
            public final class C18382 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$special$$inlined$map$1$2", m277f = "MultiShadeInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C18382.this.emit(null, this);
                    }
                }

                public C18382(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Boolean valueOf = Boolean.valueOf(!(Math.abs(((Number) obj).floatValue()) < 1.0E-4f));
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C18382(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, FlowKt.distinctUntilChanged(multiShadeRepository.proxiedInput), MultiShadeInteractor$processedProxiedInput$2.INSTANCE);
        Flow flow = new Flow() { // from class: com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$special$$inlined$map$2$2 */
            public final class C18392 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MultiShadeInteractor this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$special$$inlined$map$2$2", m277f = "MultiShadeInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.multishade.domain.interactor.MultiShadeInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C18392.this.emit(null, this);
                    }
                }

                public C18392(FlowCollector flowCollector, MultiShadeInteractor multiShadeInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = multiShadeInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Pair pair = (Pair) obj;
                                ShadeConfig shadeConfig = (ShadeConfig) pair.component1();
                                ProxiedInputModel proxiedInputModel = (ProxiedInputModel) pair.component2();
                                boolean z = proxiedInputModel instanceof ProxiedInputModel.OnTap;
                                MultiShadeInteractor multiShadeInteractor = this.this$0;
                                if (!z) {
                                    multiShadeInteractor.repository._forceCollapseAll.setValue(Boolean.FALSE);
                                }
                                if (proxiedInputModel instanceof ProxiedInputModel.OnDrag) {
                                    float f = ((ProxiedInputModel.OnDrag) proxiedInputModel).xFraction;
                                    multiShadeInteractor.getClass();
                                    ShadeId shadeId = shadeConfig instanceof ShadeConfig.DualShadeConfig ? f <= ((ShadeConfig.DualShadeConfig) shadeConfig).splitFraction ? ShadeId.LEFT : ShadeId.RIGHT : ShadeId.SINGLE;
                                    MultiShadeRepository multiShadeRepository = multiShadeInteractor.repository;
                                    if (multiShadeRepository.shadeInteraction.getValue() == null) {
                                        multiShadeRepository._shadeInteraction.setValue(new MultiShadeInteractionModel(shadeId, true));
                                    }
                                } else if (z) {
                                    multiShadeInteractor.repository._forceCollapseAll.setValue(Boolean.TRUE);
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(proxiedInputModel, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C18392(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.processedProxiedInput = FlowKt.shareIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, 1);
    }
}
