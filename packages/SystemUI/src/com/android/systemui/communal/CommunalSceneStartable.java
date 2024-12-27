package com.android.systemui.communal;

import com.android.systemui.CoreStartable;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.util.settings.SettingsProxyExt;
import com.android.systemui.util.settings.SystemSettings;
import java.util.Optional;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

public final class CommunalSceneStartable implements CoreStartable {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public static final long AWAKE_DEBOUNCE_DELAY;
    public static final Companion Companion;
    public static final int DEFAULT_SCREEN_TIMEOUT;
    public final CoroutineScope applicationScope;
    public final CoroutineScope bgScope;
    public final Optional centralSurfaces$delegate;
    public final CommunalInteractor communalInteractor;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final DockManager dockManager;
    public boolean isDreaming;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final CoroutineDispatcher mainDispatcher;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public int screenTimeout = DEFAULT_SCREEN_TIMEOUT;
    public final SystemSettings systemSettings;
    public Job timeoutJob;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(CommunalSceneStartable.class, "centralSurfaces", "getCentralSurfaces()Lcom/android/systemui/statusbar/phone/CentralSurfaces;", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference1Impl};
        Companion = new Companion(null);
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.SECONDS;
        AWAKE_DEBOUNCE_DELAY = DurationKt.toDuration(5, durationUnit);
        DurationKt.toDuration(1, durationUnit);
        DEFAULT_SCREEN_TIMEOUT = 15000;
    }

    public CommunalSceneStartable(DockManager dockManager, CommunalInteractor communalInteractor, CommunalSceneInteractor communalSceneInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, SystemSettings systemSettings, Optional<CentralSurfaces> optional, NotificationShadeWindowController notificationShadeWindowController, CoroutineScope coroutineScope, CoroutineScope coroutineScope2, CoroutineDispatcher coroutineDispatcher) {
        this.dockManager = dockManager;
        this.communalInteractor = communalInteractor;
        this.communalSceneInteractor = communalSceneInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.systemSettings = systemSettings;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.applicationScope = coroutineScope;
        this.bgScope = coroutineScope2;
        this.mainDispatcher = coroutineDispatcher;
        this.centralSurfaces$delegate = optional;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$determineSceneAfterTransition(com.android.systemui.communal.CommunalSceneStartable r6, com.android.systemui.keyguard.shared.model.TransitionStep r7, kotlin.coroutines.Continuation r8) {
        /*
            r6.getClass()
            boolean r0 = r8 instanceof com.android.systemui.communal.CommunalSceneStartable$determineSceneAfterTransition$1
            if (r0 == 0) goto L16
            r0 = r8
            com.android.systemui.communal.CommunalSceneStartable$determineSceneAfterTransition$1 r0 = (com.android.systemui.communal.CommunalSceneStartable$determineSceneAfterTransition$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.communal.CommunalSceneStartable$determineSceneAfterTransition$1 r0 = new com.android.systemui.communal.CommunalSceneStartable$determineSceneAfterTransition$1
            r0.<init>(r6, r8)
        L1b:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r8)
            goto L93
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.systemui.keyguard.shared.model.KeyguardState r8 = r7.to
            com.android.systemui.dock.DockManager r2 = r6.dockManager
            r2.getClass()
            java.util.Optional r2 = r6.centralSurfaces$delegate
            kotlin.reflect.KProperty[] r4 = com.android.systemui.communal.CommunalSceneStartable.$$delegatedProperties
            r5 = 0
            r4 = r4[r5]
            java.lang.Object r2 = com.android.systemui.util.kotlin.DaggerKt.getValue(r2, r6, r4)
            com.android.systemui.statusbar.phone.CentralSurfaces r2 = (com.android.systemui.statusbar.phone.CentralSurfaces) r2
            if (r2 == 0) goto L4f
            com.android.systemui.statusbar.phone.CentralSurfacesImpl r2 = (com.android.systemui.statusbar.phone.CentralSurfacesImpl) r2
            boolean r5 = r2.mIsLaunchingActivityOverLockscreen
        L4f:
            com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.OCCLUDED
            if (r8 != r2) goto L59
            if (r5 != 0) goto L59
            com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.communal.shared.model.CommunalScenes.Blank
        L57:
            r1 = r6
            goto L98
        L59:
            com.android.systemui.keyguard.shared.model.KeyguardState r4 = com.android.systemui.keyguard.shared.model.KeyguardState.GLANCEABLE_HUB
            if (r8 != r4) goto L64
            com.android.systemui.keyguard.shared.model.KeyguardState r7 = r7.from
            if (r7 != r2) goto L64
            com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.communal.shared.model.CommunalScenes.Communal
            goto L57
        L64:
            com.android.systemui.keyguard.shared.model.KeyguardState r7 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
            if (r8 != r7) goto L7d
            com.android.systemui.communal.domain.interactor.CommunalInteractor r6 = r6.communalInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r6.editModeOpen
            kotlinx.coroutines.flow.StateFlow r6 = r6.$$delegate_0
            java.lang.Object r6 = r6.getValue()
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 != 0) goto L7d
            com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.communal.shared.model.CommunalScenes.Blank
            goto L57
        L7d:
            com.android.systemui.keyguard.shared.model.KeyguardState$Companion r6 = com.android.systemui.keyguard.shared.model.KeyguardState.Companion
            r6.getClass()
            boolean r6 = com.android.systemui.keyguard.shared.model.KeyguardState.Companion.deviceIsAwakeInState(r8)
            if (r6 != 0) goto L96
            r0.label = r3
            long r6 = com.android.systemui.communal.CommunalSceneStartable.AWAKE_DEBOUNCE_DELAY
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.m2546delayVtjQ1oo(r6, r0)
            if (r6 != r1) goto L93
            goto L98
        L93:
            com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.communal.shared.model.CommunalScenes.Blank
            goto L57
        L96:
            r6 = 0
            goto L57
        L98:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.CommunalSceneStartable.access$determineSceneAfterTransition(com.android.systemui.communal.CommunalSceneStartable, com.android.systemui.keyguard.shared.model.TransitionStep, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.mapLatest(this.keyguardTransitionInteractor.startedKeyguardTransitionStep, new CommunalSceneStartable$start$1(this))), new CommunalSceneStartable$start$2(this, null)), this.applicationScope);
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), SettingsProxyExt.INSTANCE.observerFlow(this.systemSettings, "screen_off_timeout")), new CommunalSceneStartable$start$3(this, null));
        CoroutineScope coroutineScope = this.bgScope;
        FlowKt.launchIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope);
        BuildersKt.launch$default(coroutineScope, null, null, new CommunalSceneStartable$start$4(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new CommunalSceneStartable$start$5(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new CommunalSceneStartable$start$6(this, null), 3);
    }
}
