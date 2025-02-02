package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.data.BouncerView;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$2;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardBouncerViewModel {
    public final ReadonlyStateFlow bouncerExpansionAmount;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 bouncerShowMessage;
    public final PrimaryBouncerInteractor interactor;
    public final ReadonlyStateFlow isInflated;
    public final PrimaryBouncerInteractor$special$$inlined$map$2 isInteractable;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isShowing;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 keyguardAuthenticated;
    public final ReadonlyStateFlow keyguardPosition;
    public final ChannelLimitedFlowMerge shouldUpdateSideFps;
    public final ReadonlyStateFlow sideFpsShowing;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 startDisappearAnimation;
    public final PrimaryBouncerInteractor$special$$inlined$map$1 startingToHide;
    public final PrimaryBouncerInteractor$special$$inlined$filter$2 updateResources;
    public final BouncerView view;

    public KeyguardBouncerViewModel(BouncerView bouncerView, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.view = bouncerView;
        this.interactor = primaryBouncerInteractor;
        this.bouncerExpansionAmount = primaryBouncerInteractor.panelExpansionAmount;
        this.isInteractable = primaryBouncerInteractor.isInteractable;
        KeyguardBouncerViewModel$isShowing$1 keyguardBouncerViewModel$isShowing$1 = new KeyguardBouncerViewModel$isShowing$1(null);
        final ReadonlyStateFlow readonlyStateFlow = primaryBouncerInteractor.isShowing;
        this.isShowing = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, primaryBouncerInteractor.primaryBouncerUpdating, keyguardBouncerViewModel$isShowing$1);
        PrimaryBouncerInteractor$special$$inlined$map$1 primaryBouncerInteractor$special$$inlined$map$1 = primaryBouncerInteractor.startingToHide;
        this.startingToHide = primaryBouncerInteractor$special$$inlined$map$1;
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = primaryBouncerInteractor.startingDisappearAnimation;
        this.startDisappearAnimation = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
        this.keyguardPosition = primaryBouncerInteractor.keyguardPosition;
        this.updateResources = primaryBouncerInteractor.resourceUpdateRequests;
        this.bouncerShowMessage = primaryBouncerInteractor.showMessage;
        this.keyguardAuthenticated = primaryBouncerInteractor.keyguardAuthenticated;
        this.sideFpsShowing = primaryBouncerInteractor.sideFpsShowing;
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$map$1$2 */
            public final class C17562 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$map$1$2", m277f = "KeyguardBouncerViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        return C17562.this.emit(null, this);
                    }
                }

                public C17562(FlowCollector flowCollector) {
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
                                ((Boolean) obj).booleanValue();
                                Unit unit = Unit.INSTANCE;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
                Object collect = Flow.this.collect(new C17562(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$12 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1);
        this.shouldUpdateSideFps = FlowKt.merge(flow, primaryBouncerInteractor$special$$inlined$map$1, new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$map$2$2 */
            public final class C17572 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$map$2$2", m277f = "KeyguardBouncerViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        return C17572.this.emit(null, this);
                    }
                }

                public C17572(FlowCollector flowCollector) {
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
                                Unit unit = Unit.INSTANCE;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
                Object collect = Flow.this.collect(new C17572(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isInflated = primaryBouncerInteractor.isInflated;
    }
}
