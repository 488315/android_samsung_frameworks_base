package com.android.systemui.keyguard.data.repository;

import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardClockRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final ClockRegistry clockRegistry;
    public final KeyguardClockRepository$special$$inlined$mapNotNull$1 currentClockId;
    public final SecureSettings secureSettings;
    public final KeyguardClockRepository$special$$inlined$map$1 selectedClockSize;

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$mapNotNull$1] */
    public KeyguardClockRepository(SecureSettings secureSettings, ClockRegistry clockRegistry, CoroutineDispatcher coroutineDispatcher) {
        this.secureSettings = secureSettings;
        this.clockRegistry = clockRegistry;
        this.backgroundDispatcher = coroutineDispatcher;
        SettingsProxyExt.INSTANCE.getClass();
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardClockRepository$selectedClockSize$1(null), SettingsProxyExt.observerFlow(secureSettings, 0, "lockscreen_use_double_line_clock"));
        this.selectedClockSize = new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$map$1$2 */
            public final class C16082 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardClockRepository this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$map$1$2", m277f = "KeyguardClockRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        return C16082.this.emit(null, this);
                    }
                }

                public C16082(FlowCollector flowCollector, KeyguardClockRepository keyguardClockRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardClockRepository;
                }

                /* JADX WARN: Removed duplicated region for block: B:19:0x0064 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object obj2;
                    CoroutineSingletons coroutineSingletons;
                    int i;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            obj2 = anonymousClass1.result;
                            coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                FlowCollector flowCollector2 = this.$this_unsafeFlow;
                                anonymousClass1.L$0 = flowCollector2;
                                anonymousClass1.label = 1;
                                KeyguardClockRepository keyguardClockRepository = this.this$0;
                                keyguardClockRepository.getClass();
                                obj2 = BuildersKt.withContext(keyguardClockRepository.backgroundDispatcher, new KeyguardClockRepository$getClockSize$2(keyguardClockRepository, null), anonymousClass1);
                                if (obj2 == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                                flowCollector = flowCollector2;
                            } else {
                                if (i != 1) {
                                    if (i != 2) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                    return Unit.INSTANCE;
                                }
                                flowCollector = (FlowCollector) anonymousClass1.L$0;
                                ResultKt.throwOnFailure(obj2);
                            }
                            anonymousClass1.L$0 = null;
                            anonymousClass1.label = 2;
                            if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    obj2 = anonymousClass1.result;
                    coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                    if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C16082(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final CallbackFlowBuilder callbackFlow = FlowKt.callbackFlow(new KeyguardClockRepository$currentClockId$1(this, null));
        this.currentClockId = new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$mapNotNull$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$mapNotNull$1$2 */
            public final class C16092 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$mapNotNull$1$2", m277f = "KeyguardClockRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getUsbConnectionType}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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
                        return C16092.this.emit(null, this);
                    }
                }

                public C16092(FlowCollector flowCollector) {
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
                                String str = (String) obj;
                                if (str != null) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
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
                Object collect = Flow.this.collect(new C16092(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
