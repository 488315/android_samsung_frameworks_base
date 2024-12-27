package com.android.systemui.scene.domain.resolver;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.BaseShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;

public final class QuickSettingsSceneFamilyResolver implements SceneResolver {
    public static final Set quickSettingsScenes;
    public final ReadonlyStateFlow resolvedScene;
    public final SceneKey targetFamily = SceneFamilies.QuickSettings;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        quickSettingsScenes = SetsKt__SetsKt.setOf(Scenes.QuickSettings, Scenes.QuickSettingsShade, Scenes.Shade);
    }

    public QuickSettingsSceneFamilyResolver(CoroutineScope coroutineScope, ShadeInteractor shadeInteractor) {
        SceneKey sceneKey;
        BaseShadeInteractor baseShadeInteractor = ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor;
        final StateFlow shadeMode = baseShadeInteractor.getShadeMode();
        Flow flow = new Flow() { // from class: com.android.systemui.scene.domain.resolver.QuickSettingsSceneFamilyResolver$special$$inlined$map$1

            /* renamed from: com.android.systemui.scene.domain.resolver.QuickSettingsSceneFamilyResolver$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ QuickSettingsSceneFamilyResolver receiver$inlined;

                /* renamed from: com.android.systemui.scene.domain.resolver.QuickSettingsSceneFamilyResolver$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, QuickSettingsSceneFamilyResolver quickSettingsSceneFamilyResolver) {
                    this.$this_unsafeFlow = flowCollector;
                    this.receiver$inlined = quickSettingsSceneFamilyResolver;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.scene.domain.resolver.QuickSettingsSceneFamilyResolver$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.scene.domain.resolver.QuickSettingsSceneFamilyResolver$special$$inlined$map$1$2$1 r0 = (com.android.systemui.scene.domain.resolver.QuickSettingsSceneFamilyResolver$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.scene.domain.resolver.QuickSettingsSceneFamilyResolver$special$$inlined$map$1$2$1 r0 = new com.android.systemui.scene.domain.resolver.QuickSettingsSceneFamilyResolver$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.shade.shared.model.ShadeMode r5 = (com.android.systemui.shade.shared.model.ShadeMode) r5
                        java.util.Set r6 = com.android.systemui.scene.domain.resolver.QuickSettingsSceneFamilyResolver.quickSettingsScenes
                        com.android.systemui.scene.domain.resolver.QuickSettingsSceneFamilyResolver r6 = r4.receiver$inlined
                        r6.getClass()
                        boolean r6 = r5 instanceof com.android.systemui.shade.shared.model.ShadeMode.Single
                        if (r6 == 0) goto L42
                        com.android.compose.animation.scene.SceneKey r5 = com.android.systemui.scene.shared.model.Scenes.QuickSettings
                        goto L4f
                    L42:
                        boolean r6 = r5 instanceof com.android.systemui.shade.shared.model.ShadeMode.Dual
                        if (r6 == 0) goto L49
                        com.android.compose.animation.scene.SceneKey r5 = com.android.systemui.scene.shared.model.Scenes.QuickSettingsShade
                        goto L4f
                    L49:
                        boolean r5 = r5 instanceof com.android.systemui.shade.shared.model.ShadeMode.Split
                        if (r5 == 0) goto L5d
                        com.android.compose.animation.scene.SceneKey r5 = com.android.systemui.scene.shared.model.Scenes.Shade
                    L4f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    L5d:
                        kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                        r4.<init>()
                        throw r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.domain.resolver.QuickSettingsSceneFamilyResolver$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ShadeMode shadeMode2 = (ShadeMode) baseShadeInteractor.getShadeMode().getValue();
        if (shadeMode2 instanceof ShadeMode.Single) {
            sceneKey = Scenes.QuickSettings;
        } else if (shadeMode2 instanceof ShadeMode.Dual) {
            sceneKey = Scenes.QuickSettingsShade;
        } else {
            if (!(shadeMode2 instanceof ShadeMode.Split)) {
                throw new NoWhenBranchMatchedException();
            }
            sceneKey = Scenes.Shade;
        }
        this.resolvedScene = FlowKt.stateIn(flow, coroutineScope, startedEagerly, sceneKey);
    }

    @Override // com.android.systemui.scene.domain.resolver.SceneResolver
    public final StateFlow getResolvedScene() {
        return this.resolvedScene;
    }

    @Override // com.android.systemui.scene.domain.resolver.SceneResolver
    public final SceneKey getTargetFamily() {
        return this.targetFamily;
    }

    @Override // com.android.systemui.scene.domain.resolver.SceneResolver
    public final boolean includesScene(SceneKey sceneKey) {
        return quickSettingsScenes.contains(sceneKey);
    }
}
