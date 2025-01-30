package com.android.systemui.keyguard.p009ui.viewmodel;

import com.android.systemui.common.shared.model.Position;
import com.android.systemui.doze.util.BurnInHelperWrapper;
import com.android.systemui.keyguard.domain.interactor.KeyguardBottomAreaInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardBottomAreaViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ChannelFlowTransformLatest alpha;
    public final Flow areQuickAffordancesFullyOpaque;
    public final KeyguardBottomAreaInteractor bottomAreaInteractor;
    public final BurnInHelperWrapper burnInHelperWrapper;
    public final ChannelFlowTransformLatest endButton;
    public final Flow indicationAreaTranslationX;
    public final Flow isIndicationAreaPadded;
    public final Flow isOverlayContainerVisible;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardLongPressViewModel longPressViewModel;
    public final StateFlowImpl previewMode;
    public final KeyguardQuickAffordanceInteractor quickAffordanceInteractor;
    public final StateFlowImpl selectedPreviewSlotId;
    public final KeyguardSettingsMenuViewModel settingsMenuViewModel;
    public final ChannelFlowTransformLatest startButton;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PreviewMode {
        public final boolean isInPreviewMode;
        public final boolean shouldHighlightSelectedAffordance;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public PreviewMode() {
            this(r2, r2, 3, null);
            boolean z = false;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PreviewMode)) {
                return false;
            }
            PreviewMode previewMode = (PreviewMode) obj;
            return this.isInPreviewMode == previewMode.isInPreviewMode && this.shouldHighlightSelectedAffordance == previewMode.shouldHighlightSelectedAffordance;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            boolean z = this.isInPreviewMode;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int i2 = i * 31;
            boolean z2 = this.shouldHighlightSelectedAffordance;
            return i2 + (z2 ? 1 : z2 ? 1 : 0);
        }

        public final String toString() {
            return "PreviewMode(isInPreviewMode=" + this.isInPreviewMode + ", shouldHighlightSelectedAffordance=" + this.shouldHighlightSelectedAffordance + ")";
        }

        public PreviewMode(boolean z, boolean z2) {
            this.isInPreviewMode = z;
            this.shouldHighlightSelectedAffordance = z2;
        }

        public /* synthetic */ PreviewMode(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2);
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardBottomAreaViewModel(KeyguardInteractor keyguardInteractor, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, KeyguardBottomAreaInteractor keyguardBottomAreaInteractor, BurnInHelperWrapper burnInHelperWrapper, KeyguardLongPressViewModel keyguardLongPressViewModel, KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel) {
        this.keyguardInteractor = keyguardInteractor;
        this.quickAffordanceInteractor = keyguardQuickAffordanceInteractor;
        this.bottomAreaInteractor = keyguardBottomAreaInteractor;
        this.burnInHelperWrapper = burnInHelperWrapper;
        this.longPressViewModel = keyguardLongPressViewModel;
        this.settingsMenuViewModel = keyguardSettingsMenuViewModel;
        boolean z = false;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new PreviewMode(z, z, 3, null));
        this.previewMode = MutableStateFlow;
        this.selectedPreviewSlotId = StateFlowKt.MutableStateFlow("bottom_start");
        final StateFlow stateFlow = keyguardBottomAreaInteractor.alpha;
        this.areQuickAffordancesFullyOpaque = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$1$2 */
            public final class C17522 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$1$2", m277f = "KeyguardBottomAreaViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        return C17522.this.emit(null, this);
                    }
                }

                public C17522(FlowCollector flowCollector) {
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
                                Boolean valueOf = Boolean.valueOf(((Number) obj).floatValue() >= 0.95f);
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
                Object collect = Flow.this.collect(new C17522(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(MutableStateFlow, new KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1(null, this, KeyguardQuickAffordancePosition.BOTTOM_START));
        this.startButton = transformLatest;
        ChannelFlowTransformLatest transformLatest2 = FlowKt.transformLatest(MutableStateFlow, new KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1(null, this, KeyguardQuickAffordancePosition.BOTTOM_END));
        this.endButton = transformLatest2;
        final StateFlow stateFlow2 = keyguardInteractor.isDozing;
        this.isOverlayContainerVisible = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$2$2 */
            public final class C17532 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$2$2", m277f = "KeyguardBottomAreaViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        return C17532.this.emit(null, this);
                    }
                }

                public C17532(FlowCollector flowCollector) {
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
                                Boolean valueOf = Boolean.valueOf(!((Boolean) obj).booleanValue());
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
                Object collect = Flow.this.collect(new C17532(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.alpha = FlowKt.transformLatest(MutableStateFlow, new KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1(null, this));
        this.isIndicationAreaPadded = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(transformLatest, transformLatest2, new KeyguardBottomAreaViewModel$isIndicationAreaPadded$1(null)));
        final StateFlow stateFlow3 = keyguardBottomAreaInteractor.clockPosition;
        this.indicationAreaTranslationX = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$3$2 */
            public final class C17542 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$3$2", m277f = "KeyguardBottomAreaViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        return C17542.this.emit(null, this);
                    }
                }

                public C17542(FlowCollector flowCollector) {
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
                                Float f = new Float(((Position) obj).f239x);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(f, anonymousClass1) == coroutineSingletons) {
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
                Object collect = Flow.this.collect(new C17542(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getAFFORDANCE_FULLY_OPAQUE_ALPHA_THRESHOLD$annotations() {
        }
    }
}
