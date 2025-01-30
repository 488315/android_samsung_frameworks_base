package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.dock.DockManagerExtensionsKt;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1", m277f = "KeyguardBottomAreaViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getAutoCallPickupState, 190}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ KeyguardQuickAffordancePosition $position$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ KeyguardBottomAreaViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1(Continuation continuation, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
        super(3, continuation);
        this.this$0 = keyguardBottomAreaViewModel;
        this.$position$inlined = keyguardQuickAffordancePosition;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1 keyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1 = new KeyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$position$inlined);
        keyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        keyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1.L$1 = obj2;
        return keyguardBottomAreaViewModel$button$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00b0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0087  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        KeyguardBottomAreaViewModel.PreviewMode previewMode;
        FlowCollector flowCollector2;
        Flow quickAffordanceAlwaysVisible;
        KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        Flow distinctUntilChanged;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            previewMode = (KeyguardBottomAreaViewModel.PreviewMode) this.L$1;
            if (previewMode.isInPreviewMode) {
                quickAffordanceAlwaysVisible = this.this$0.quickAffordanceInteractor.quickAffordanceAlwaysVisible(this.$position$inlined);
                Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(this.this$0.bottomAreaInteractor.animateDozingTransitions);
                KeyguardBottomAreaViewModel keyguardBottomAreaViewModel = this.this$0;
                Flow flow = keyguardBottomAreaViewModel.areQuickAffordancesFullyOpaque;
                StateFlowImpl stateFlowImpl = keyguardBottomAreaViewModel.selectedPreviewSlotId;
                keyguardQuickAffordanceInteractor = keyguardBottomAreaViewModel.quickAffordanceInteractor;
                keyguardQuickAffordanceInteractor.getClass();
                if (((FeatureFlagsRelease) keyguardQuickAffordanceInteractor.featureFlags).isEnabled(Flags.CUSTOMIZABLE_LOCK_SCREEN_QUICK_AFFORDANCES)) {
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                } else {
                    final Flow retrieveIsDocked = DockManagerExtensionsKt.retrieveIsDocked(keyguardQuickAffordanceInteractor.dockManager);
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$useLongPress$$inlined$map$1

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$useLongPress$$inlined$map$1$2 */
                        public final class C17022 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                            @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$useLongPress$$inlined$map$1$2", m277f = "KeyguardQuickAffordanceInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$useLongPress$$inlined$map$1$2$1, reason: invalid class name */
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
                                    return C17022.this.emit(null, this);
                                }
                            }

                            public C17022(FlowCollector flowCollector) {
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
                        public final Object collect(FlowCollector flowCollector3, Continuation continuation) {
                            Object collect = Flow.this.collect(new C17022(flowCollector3), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                }
                distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combine(quickAffordanceAlwaysVisible, distinctUntilChanged2, flow, stateFlowImpl, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, new KeyguardBottomAreaViewModel$button$1$1(this.$position$inlined, this.this$0, previewMode, null)));
                this.L$0 = null;
                this.L$1 = null;
                this.label = 2;
                if (FlowKt.emitAll(this, distinctUntilChanged, flowCollector) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor2 = this.this$0.quickAffordanceInteractor;
            KeyguardQuickAffordancePosition keyguardQuickAffordancePosition = this.$position$inlined;
            this.L$0 = flowCollector;
            this.L$1 = previewMode;
            this.label = 1;
            Object quickAffordance = keyguardQuickAffordanceInteractor2.quickAffordance(keyguardQuickAffordancePosition, this);
            if (quickAffordance == coroutineSingletons) {
                return coroutineSingletons;
            }
            flowCollector2 = flowCollector;
            obj = quickAffordance;
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            previewMode = (KeyguardBottomAreaViewModel.PreviewMode) this.L$1;
            flowCollector2 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        FlowCollector flowCollector3 = flowCollector2;
        quickAffordanceAlwaysVisible = (Flow) obj;
        flowCollector = flowCollector3;
        Flow distinctUntilChanged22 = FlowKt.distinctUntilChanged(this.this$0.bottomAreaInteractor.animateDozingTransitions);
        KeyguardBottomAreaViewModel keyguardBottomAreaViewModel2 = this.this$0;
        Flow flow2 = keyguardBottomAreaViewModel2.areQuickAffordancesFullyOpaque;
        StateFlowImpl stateFlowImpl2 = keyguardBottomAreaViewModel2.selectedPreviewSlotId;
        keyguardQuickAffordanceInteractor = keyguardBottomAreaViewModel2.quickAffordanceInteractor;
        keyguardQuickAffordanceInteractor.getClass();
        if (((FeatureFlagsRelease) keyguardQuickAffordanceInteractor.featureFlags).isEnabled(Flags.CUSTOMIZABLE_LOCK_SCREEN_QUICK_AFFORDANCES)) {
        }
        distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combine(quickAffordanceAlwaysVisible, distinctUntilChanged22, flow2, stateFlowImpl2, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, new KeyguardBottomAreaViewModel$button$1$1(this.$position$inlined, this.this$0, previewMode, null)));
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
        if (FlowKt.emitAll(this, distinctUntilChanged, flowCollector) == coroutineSingletons) {
        }
        return Unit.INSTANCE;
    }
}
