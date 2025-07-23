package com.android.systemui.communal.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.CoreStartable;
import com.android.systemui.communal.data.repository.CommunalSceneTransitionRepository;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import java.util.UUID;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSceneTransitionInteractor implements CoreStartable, CommunalSceneInteractor.OnSceneAboutToChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public UUID currentTransitionId;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
    public final CoroutineDispatcher mainImmediateDispatcher;
    public final ReadonlyStateFlow nextKeyguardState;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 nextKeyguardStateInternal;
    public StandaloneCoroutine progressJob;
    public final CommunalSceneTransitionRepository repository;
    public final CommunalSceneInteractor sceneInteractor;
    public final CommunalSettingsInteractor settingsInteractor;
    public final KeyguardTransitionInteractor transitionInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CommunalSceneTransitionInteractor(KeyguardTransitionInteractor keyguardTransitionInteractor, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, CommunalSettingsInteractor communalSettingsInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CommunalSceneInteractor communalSceneInteractor, CommunalSceneTransitionRepository communalSceneTransitionRepository, PowerInteractor powerInteractor, KeyguardInteractor keyguardInteractor) {
        this.transitionInteractor = keyguardTransitionInteractor;
        this.internalTransitionInteractor = internalKeyguardTransitionInteractor;
        this.settingsInteractor = communalSettingsInteractor;
        this.applicationScope = coroutineScope;
        this.mainImmediateDispatcher = coroutineDispatcher;
        this.sceneInteractor = communalSceneInteractor;
        this.repository = communalSceneTransitionRepository;
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.combine(powerInteractor.isAsleep, keyguardInteractor.isDreamingWithOverlay, keyguardInteractor.isKeyguardOccluded, keyguardInteractor.isKeyguardGoingAway, keyguardInteractor.isKeyguardShowing, new CommunalSceneTransitionInteractor$nextKeyguardStateInternal$1(null)));
        this.nextKeyguardStateInternal = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(communalSceneTransitionRepository.nextLockscreenTargetState, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CommunalSceneTransitionInteractor$nextKeyguardState$1(null), flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1), new CommunalSceneTransitionInteractor$nextKeyguardState$2(null));
        SharingStarted.Companion.getClass();
        this.nextKeyguardState = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, KeyguardState.LOCKSCREEN);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x005a, code lost:
    
        if (r5.finishCurrentTransition(r0) == r1) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008b, code lost:
    
        if (r5.transitionKtfTo(r6, r0) == r1) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$handleIdle(com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor r5, com.android.compose.animation.scene.ObservableTransitionState r6, com.android.compose.animation.scene.ObservableTransitionState.Idle r7, kotlin.coroutines.Continuation r8) {
        /*
            r5.getClass()
            boolean r0 = r8 instanceof com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$handleIdle$1
            if (r0 == 0) goto L16
            r0 = r8
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$handleIdle$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$handleIdle$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$handleIdle$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$handleIdle$1
            r0.<init>(r5, r8)
        L1b:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3d
            if (r2 == r4) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r5 = r0.L$0
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor r5 = (com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor) r5
            kotlin.ResultKt.throwOnFailure(r8)
            goto L8e
        L31:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L39:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L5d
        L3d:
            kotlin.ResultKt.throwOnFailure(r8)
            boolean r8 = r6 instanceof com.android.compose.animation.scene.ObservableTransitionState.Transition
            if (r8 == 0) goto L60
            java.util.UUID r8 = r5.currentTransitionId
            if (r8 == 0) goto L60
            com.android.compose.animation.scene.SceneKey r8 = r7.currentScene
            com.android.compose.animation.scene.ObservableTransitionState$Transition r6 = (com.android.compose.animation.scene.ObservableTransitionState.Transition) r6
            com.android.compose.animation.scene.ContentKey r6 = r6.toContent
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r6)
            if (r6 == 0) goto L60
            r0.label = r4
            java.lang.Object r5 = r5.finishCurrentTransition(r0)
            if (r5 != r1) goto L5d
            goto L8d
        L5d:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L60:
            com.android.compose.animation.scene.SceneKey r6 = r7.currentScene
            com.android.compose.animation.scene.SceneKey r7 = com.android.systemui.communal.shared.model.CommunalScenes.Communal
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r7)
            if (r6 == 0) goto L6d
            com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.GLANCEABLE_HUB
            goto L83
        L6d:
            com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r6 = r5.internalTransitionInteractor
            com.android.systemui.keyguard.shared.model.TransitionInfo r6 = r6.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core()
            com.android.systemui.keyguard.shared.model.KeyguardState r6 = r6.to
            com.android.systemui.keyguard.shared.model.KeyguardState r7 = com.android.systemui.keyguard.shared.model.KeyguardState.GLANCEABLE_HUB
            if (r6 != r7) goto L99
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r5.nextKeyguardState
            kotlinx.coroutines.flow.StateFlow r6 = r6.$$delegate_0
            java.lang.Object r6 = r6.getValue()
            com.android.systemui.keyguard.shared.model.KeyguardState r6 = (com.android.systemui.keyguard.shared.model.KeyguardState) r6
        L83:
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r6 = r5.transitionKtfTo(r6, r0)
            if (r6 != r1) goto L8e
        L8d:
            return r1
        L8e:
            com.android.systemui.communal.data.repository.CommunalSceneTransitionRepository r5 = r5.repository
            kotlinx.coroutines.flow.StateFlowImpl r5 = r5.nextLockscreenTargetState
            r6 = 0
            r5.setValue(r6)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L99:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor.access$handleIdle(com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor, com.android.compose.animation.scene.ObservableTransitionState, com.android.compose.animation.scene.ObservableTransitionState$Idle, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00aa, code lost:
    
        if (r10.transitionKtfTo(r11, r0) == r1) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ee, code lost:
    
        if (r10.transitionKtfTo(r11, r0) == r1) goto L48;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$handleTransition(com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor r10, com.android.compose.animation.scene.ObservableTransitionState r11, com.android.compose.animation.scene.ObservableTransitionState.Transition r12, kotlin.coroutines.Continuation r13) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor.access$handleTransition(com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor, com.android.compose.animation.scene.ObservableTransitionState, com.android.compose.animation.scene.ObservableTransitionState$Transition, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void collectProgress(ObservableTransitionState.Transition transition) {
        StandaloneCoroutine standaloneCoroutine = this.progressJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.progressJob = CoroutineTracingKt.launchTraced$default(this.applicationScope, this.mainImmediateDispatcher, null, new CommunalSceneTransitionInteractor$collectProgress$1(transition, this, null), 4);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object finishCurrentTransition(kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishCurrentTransition$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishCurrentTransition$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishCurrentTransition$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishCurrentTransition$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishCurrentTransition$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor r5 = (com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4e
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            java.util.UUID r6 = r5.currentTransitionId
            if (r6 != 0) goto L3d
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L3d:
            com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.FINISHED
            r0.L$0 = r5
            r0.label = r3
            com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r3 = r5.internalTransitionInteractor
            r4 = 1065353216(0x3f800000, float:1.0)
            java.lang.Object r6 = r3.updateTransition(r6, r4, r2, r0)
            if (r6 != r1) goto L4e
            return r1
        L4e:
            r5.resetTransitionData()
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor.finishCurrentTransition(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object finishReversedTransitionTo(com.android.systemui.keyguard.shared.model.KeyguardState r12, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishReversedTransitionTo$1
            if (r0 == 0) goto L13
            r0 = r13
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishReversedTransitionTo$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishReversedTransitionTo$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishReversedTransitionTo$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$finishReversedTransitionTo$1
            r0.<init>(r11, r13)
        L18:
            java.lang.Object r13 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L42
            if (r2 == r4) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r11 = r0.L$0
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor r11 = (com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor) r11
            kotlin.ResultKt.throwOnFailure(r13)
            goto L88
        L2e:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L36:
            java.lang.Object r11 = r0.L$1
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor r11 = (com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor) r11
            java.lang.Object r12 = r0.L$0
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor r12 = (com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor) r12
            kotlin.ResultKt.throwOnFailure(r13)
            goto L6a
        L42:
            kotlin.ResultKt.throwOnFailure(r13)
            com.android.systemui.keyguard.shared.model.TransitionInfo r5 = new com.android.systemui.keyguard.shared.model.TransitionInfo
            com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r13 = r11.internalTransitionInteractor
            com.android.systemui.keyguard.shared.model.TransitionInfo r2 = r13.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core()
            com.android.systemui.keyguard.shared.model.KeyguardState r7 = r2.to
            com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled r10 = com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled.REVERSE
            java.lang.String r6 = "CommunalSceneTransitionInteractor"
            r9 = 0
            r8 = r12
            r5.<init>(r6, r7, r8, r9, r10)
            r0.L$0 = r11
            r0.L$1 = r11
            r0.label = r4
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r12 = r13.repository
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl r12 = (com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl) r12
            java.lang.Object r13 = r12.startTransition(r5, r0)
            if (r13 != r1) goto L69
            goto L86
        L69:
            r12 = r11
        L6a:
            java.util.UUID r13 = (java.util.UUID) r13
            r11.currentTransitionId = r13
            com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r11 = r12.internalTransitionInteractor
            java.util.UUID r13 = r12.currentTransitionId
            r13.getClass()
            com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.FINISHED
            r0.L$0 = r12
            r4 = 0
            r0.L$1 = r4
            r0.label = r3
            r3 = 1065353216(0x3f800000, float:1.0)
            java.lang.Object r11 = r11.updateTransition(r13, r3, r2, r0)
            if (r11 != r1) goto L87
        L86:
            return r1
        L87:
            r11 = r12
        L88:
            r11.resetTransitionData()
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor.finishReversedTransitionTo(com.android.systemui.keyguard.shared.model.KeyguardState, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.communal.domain.interactor.CommunalSceneInteractor.OnSceneAboutToChangeListener
    public final void onSceneAboutToChange(SceneKey sceneKey, KeyguardState keyguardState) {
        if (!Intrinsics.areEqual(sceneKey, CommunalScenes.Blank) || keyguardState == null) {
            return;
        }
        this.repository.nextLockscreenTargetState.updateState(null, keyguardState);
    }

    public final void resetTransitionData() {
        StandaloneCoroutine standaloneCoroutine = this.progressJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.progressJob = null;
        this.currentTransitionId = null;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (this.settingsInteractor.isCommunalFlagEnabled()) {
            CommunalSceneInteractor communalSceneInteractor = this.sceneInteractor;
            communalSceneInteractor.getClass();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i = SceneContainerFlag.$r8$clinit;
            communalSceneInteractor.onSceneAboutToChangeListener.add(this);
            CoroutineTracingKt.launchTraced$default(this.applicationScope, this.mainImmediateDispatcher, null, new CommunalSceneTransitionInteractor$listenForSceneTransitionProgress$1(this, null), 4);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object startTransition(com.android.systemui.keyguard.shared.model.TransitionInfo r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$startTransition$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$startTransition$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$startTransition$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$startTransition$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$startTransition$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor r4 = (com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4e
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            java.util.UUID r6 = r4.currentTransitionId
            if (r6 == 0) goto L3d
            r4.resetTransitionData()
        L3d:
            r0.L$0 = r4
            r0.label = r3
            com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r6 = r4.internalTransitionInteractor
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r6 = r6.repository
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl r6 = (com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl) r6
            java.lang.Object r6 = r6.startTransition(r5, r0)
            if (r6 != r1) goto L4e
            return r1
        L4e:
            java.util.UUID r6 = (java.util.UUID) r6
            r4.currentTransitionId = r6
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor.startTransition(com.android.systemui.keyguard.shared.model.TransitionInfo, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Object transitionKtfTo(KeyguardState keyguardState, ContinuationImpl continuationImpl) {
        TransitionStep transitionStep = (TransitionStep) this.transitionInteractor.transitionState.$$delegate_0.getValue();
        KeyguardState keyguardState2 = transitionStep.to;
        if (keyguardState2 == keyguardState) {
            if (transitionStep.transitionState == TransitionState.FINISHED) {
                resetTransitionData();
                return Unit.INSTANCE;
            }
        }
        if (keyguardState == null || keyguardState2 == keyguardState) {
            Object finishCurrentTransition = finishCurrentTransition(continuationImpl);
            return finishCurrentTransition == CoroutineSingletons.COROUTINE_SUSPENDED ? finishCurrentTransition : Unit.INSTANCE;
        }
        Object finishReversedTransitionTo = finishReversedTransitionTo(keyguardState, continuationImpl);
        return finishReversedTransitionTo == CoroutineSingletons.COROUTINE_SUSPENDED ? finishReversedTransitionTo : Unit.INSTANCE;
    }
}
