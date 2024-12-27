package com.android.systemui.scene.ui.viewmodel;

import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.TransitionKeys;
import com.android.systemui.shade.domain.interactor.BaseShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.Unit;
import kotlin.collections.builders.MapBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

public final class GoneSceneViewModel {
    public final ReadonlyStateFlow destinationScenes;

    public GoneSceneViewModel(CoroutineScope coroutineScope, ShadeInteractor shadeInteractor) {
        BaseShadeInteractor baseShadeInteractor = ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor;
        final StateFlow shadeMode = baseShadeInteractor.getShadeMode();
        this.destinationScenes = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ GoneSceneViewModel receiver$inlined;

                /* renamed from: com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, GoneSceneViewModel goneSceneViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.receiver$inlined = goneSceneViewModel;
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
                        boolean r0 = r6 instanceof com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.shade.shared.model.ShadeMode r5 = (com.android.systemui.shade.shared.model.ShadeMode) r5
                        com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel r6 = r4.receiver$inlined
                        r6.getClass()
                        kotlin.collections.builders.MapBuilder r5 = com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel.destinationScenes(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.ui.viewmodel.GoneSceneViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), destinationScenes((ShadeMode) baseShadeInteractor.getShadeMode().getValue()));
    }

    public static MapBuilder destinationScenes(ShadeMode shadeMode) {
        MapBuilder mapBuilder = new MapBuilder();
        if ((shadeMode instanceof ShadeMode.Single) || (shadeMode instanceof ShadeMode.Dual)) {
            mapBuilder.put(new Swipe(SwipeDirection.Down, 2, Edge.Top), new UserActionResult(SceneFamilies.QuickSettings, null, 2, null));
        }
        Swipe swipe = new Swipe(SwipeDirection.Down, 0, null, 6, null);
        SceneKey sceneKey = SceneFamilies.NotifShade;
        TransitionKeys.INSTANCE.getClass();
        mapBuilder.put(swipe, new UserActionResult(sceneKey, shadeMode instanceof ShadeMode.Split ? TransitionKeys.ToSplitShade : null));
        return mapBuilder.build();
    }
}
