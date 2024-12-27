package com.android.systemui.shade.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;

public final class ShadeInteractorSceneContainerImpl implements BaseShadeInteractor {
    public final ReadonlyStateFlow anyExpansion;
    public final ReadonlyStateFlow isAnyExpanded;
    public final Flow isQsBypassingShade;
    public final ReadonlyStateFlow isQsExpanded;
    public final Flow isQsFullscreen;
    public final Flow isUserInteractingWithQs;
    public final Flow isUserInteractingWithShade;
    public final ReadonlyStateFlow qsExpansion;
    public final ReadonlyStateFlow shadeExpansion;
    public final ReadonlyStateFlow shadeMode;

    public ShadeInteractorSceneContainerImpl(CoroutineScope coroutineScope, SceneInteractor sceneInteractor, SharedNotificationContainerInteractor sharedNotificationContainerInteractor, ShadeRepository shadeRepository) {
        this.shadeMode = ((ShadeRepositoryImpl) shadeRepository).shadeMode;
        SceneKey sceneKey = SceneFamilies.NotifShade;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(LatestConflatedKt.flatMapLatestConflated(sceneInteractor.resolveSceneFamily(sceneKey), new ShadeInteractorSceneContainerImpl$sceneBasedExpansion$1(sceneInteractor, null)));
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(distinctUntilChanged, coroutineScope, startedEagerly, Float.valueOf(0.0f));
        this.shadeExpansion = stateIn;
        SceneKey sceneKey2 = SceneFamilies.QuickSettings;
        final ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.combine(sharedNotificationContainerInteractor.isSplitShadeEnabled, stateIn, FlowKt.distinctUntilChanged(LatestConflatedKt.flatMapLatestConflated(sceneInteractor.resolveSceneFamily(sceneKey2), new ShadeInteractorSceneContainerImpl$sceneBasedExpansion$1(sceneInteractor, null))), new ShadeInteractorSceneContainerImpl$qsExpansion$1(null)), coroutineScope, startedEagerly, Float.valueOf(0.0f));
        this.qsExpansion = stateIn2;
        Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 0
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 <= 0) goto L3f
                        r5 = r3
                        goto L40
                    L3f:
                        r5 = 0
                    L40:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        Boolean bool = Boolean.FALSE;
        this.isQsExpanded = FlowKt.stateIn(distinctUntilChanged2, coroutineScope, startedEagerly, bool);
        this.isQsBypassingShade = FlowKt.distinctUntilChanged(LatestConflatedKt.flatMapLatestConflated(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(sceneInteractor.resolveSceneFamily(sceneKey2), sceneInteractor.resolveSceneFamily(sceneKey), ShadeInteractorSceneContainerImpl$isQsBypassingShade$2.INSTANCE), new ShadeInteractorSceneContainerImpl$isQsBypassingShade$3(sceneInteractor, null)));
        FlowKt.distinctUntilChanged(LatestConflatedKt.flatMapLatestConflated(sceneInteractor.resolveSceneFamily(sceneKey2), new ShadeInteractorSceneContainerImpl$isQsFullscreen$1(sceneInteractor, null)));
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, stateIn2, new ShadeInteractorKt$createAnyExpansionFlow$1(null));
        companion.getClass();
        final ReadonlyStateFlow stateIn3 = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedEagerly, Float.valueOf(0.0f));
        this.anyExpansion = stateIn3;
        this.isAnyExpanded = FlowKt.stateIn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 0
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 <= 0) goto L3f
                        r5 = r3
                        goto L40
                    L3f:
                        r5 = 0
                    L40:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineScope, startedEagerly, bool);
        ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1 shadeInteractorSceneContainerImpl$sceneBasedInteracting$1 = new ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1(sceneInteractor, sceneKey, null);
        ReadonlyStateFlow readonlyStateFlow = sceneInteractor.transitionState;
        this.isUserInteractingWithShade = FlowKt.distinctUntilChanged(LatestConflatedKt.flatMapLatestConflated(readonlyStateFlow, shadeInteractorSceneContainerImpl$sceneBasedInteracting$1));
        this.isUserInteractingWithQs = FlowKt.distinctUntilChanged(LatestConflatedKt.flatMapLatestConflated(readonlyStateFlow, new ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1(sceneInteractor, sceneKey2, null)));
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getAnyExpansion() {
        return this.anyExpansion;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getQsExpansion() {
        return this.qsExpansion;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getShadeExpansion() {
        return this.shadeExpansion;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getShadeMode() {
        return this.shadeMode;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow isAnyExpanded() {
        return this.isAnyExpanded;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isQsBypassingShade() {
        return this.isQsBypassingShade;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow isQsExpanded() {
        return this.isQsExpanded;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isUserInteractingWithQs() {
        return this.isUserInteractingWithQs;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isUserInteractingWithShade() {
        return this.isUserInteractingWithShade;
    }
}
