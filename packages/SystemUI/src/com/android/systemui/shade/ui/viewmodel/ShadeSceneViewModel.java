package com.android.systemui.shade.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.compose.animation.scene.TransitionKey;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.qs.FooterActionsController;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.shared.model.TransitionKeys;
import com.android.systemui.settings.brightness.ui.viewModel.BrightnessMirrorViewModel;
import com.android.systemui.shade.domain.interactor.BaseShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.collections.builders.MapBuilder;
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
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShadeSceneViewModel {
    public final BrightnessMirrorViewModel brightnessMirrorViewModel;
    public final ReadonlyStateFlow destinationScenes;
    public final FooterActionsController footerActionsController;
    public final AtomicBoolean footerActionsControllerInitialized;
    public final FooterActionsViewModel.Factory footerActionsViewModelFactory;
    public final ReadonlyStateFlow isClickable;
    public final ReadonlyStateFlow isMediaVisible;
    public final NotificationsPlaceholderViewModel notifications;
    public final QSSceneAdapter qsSceneAdapter;
    public final SceneInteractor sceneInteractor;
    public final ShadeHeaderViewModel shadeHeaderViewModel;
    public final StateFlow shadeMode;
    public final UnfoldTransitionInteractor unfoldTransitionInteractor;
    public final ShadeSceneViewModel$special$$inlined$map$1 upDestinationSceneKey;

    public ShadeSceneViewModel(CoroutineScope coroutineScope, QSSceneAdapter qSSceneAdapter, ShadeHeaderViewModel shadeHeaderViewModel, NotificationsPlaceholderViewModel notificationsPlaceholderViewModel, BrightnessMirrorViewModel brightnessMirrorViewModel, MediaCarouselInteractor mediaCarouselInteractor, ShadeInteractor shadeInteractor, FooterActionsViewModel.Factory factory, FooterActionsController footerActionsController, SceneInteractor sceneInteractor, UnfoldTransitionInteractor unfoldTransitionInteractor) {
        this.sceneInteractor = sceneInteractor;
        BaseShadeInteractor baseShadeInteractor = ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor;
        QSSceneAdapterImpl qSSceneAdapterImpl = (QSSceneAdapterImpl) qSSceneAdapter;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(baseShadeInteractor.getShadeMode(), qSSceneAdapterImpl.isCustomizerShowing, new ShadeSceneViewModel$destinationScenes$1(this, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), destinationScenes((ShadeMode) baseShadeInteractor.getShadeMode().getValue(), ((Boolean) qSSceneAdapterImpl.isCustomizerShowing.$$delegate_0.getValue()).booleanValue()));
        this.destinationScenes = stateIn;
        final Flow flatMapLatestConflated = LatestConflatedKt.flatMapLatestConflated(new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                    /*
                        r10 = this;
                        boolean r0 = r12 instanceof com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r12
                        com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r12)
                    L18:
                        java.lang.Object r12 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto L57
                    L27:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r12)
                        java.util.Map r11 = (java.util.Map) r11
                        com.android.compose.animation.scene.Swipe r12 = new com.android.compose.animation.scene.Swipe
                        com.android.compose.animation.scene.SwipeDirection r5 = com.android.compose.animation.scene.SwipeDirection.Up
                        r8 = 6
                        r9 = 0
                        r6 = 0
                        r7 = 0
                        r4 = r12
                        r4.<init>(r5, r6, r7, r8, r9)
                        java.lang.Object r11 = r11.get(r12)
                        com.android.compose.animation.scene.UserActionResult r11 = (com.android.compose.animation.scene.UserActionResult) r11
                        if (r11 == 0) goto L4b
                        com.android.compose.animation.scene.SceneKey r11 = r11.toScene
                        goto L4c
                    L4b:
                        r11 = 0
                    L4c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r10 = r10.$this_unsafeFlow
                        java.lang.Object r10 = r10.emit(r11, r0)
                        if (r10 != r1) goto L57
                        return r1
                    L57:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new ShadeSceneViewModel$isClickable$1(this, null));
        FlowKt.stateIn(new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.compose.animation.scene.SceneKey r5 = (com.android.compose.animation.scene.SceneKey) r5
                        com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.scene.shared.model.Scenes.Lockscreen
                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ui.viewmodel.ShadeSceneViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        baseShadeInteractor.getShadeMode();
        ReadonlyStateFlow readonlyStateFlow = mediaCarouselInteractor.hasActiveMediaOrRecommendation;
        new AtomicBoolean(false);
    }

    public static MapBuilder destinationScenes(ShadeMode shadeMode, boolean z) {
        MapBuilder mapBuilder = new MapBuilder();
        if (!z) {
            Swipe swipe = new Swipe(SwipeDirection.Up, 0, null, 6, null);
            SceneKey sceneKey = SceneFamilies.Home;
            TransitionKeys.INSTANCE.getClass();
            TransitionKey transitionKey = TransitionKeys.ToSplitShade;
            if (!(shadeMode instanceof ShadeMode.Split)) {
                transitionKey = null;
            }
            mapBuilder.put(swipe, new UserActionResult(sceneKey, transitionKey));
        }
        if (shadeMode instanceof ShadeMode.Single) {
            mapBuilder.put(new Swipe(SwipeDirection.Down, 0, null, 6, null), new UserActionResult(Scenes.QuickSettings, null, 2, null));
        }
        return mapBuilder.build();
    }
}
