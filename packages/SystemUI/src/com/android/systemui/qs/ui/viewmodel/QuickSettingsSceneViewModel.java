package com.android.systemui.qs.ui.viewmodel;

import com.android.compose.animation.scene.Back;
import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.qs.FooterActionsController;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor$special$$inlined$map$1;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.settings.brightness.ui.viewModel.BrightnessMirrorViewModel;
import com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
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

public final class QuickSettingsSceneViewModel {
    public final ReadonlyStateFlow backScene;
    public final BrightnessMirrorViewModel brightnessMirrorViewModel;
    public final ReadonlyStateFlow destinationScenes;
    public final FooterActionsController footerActionsController;
    public final AtomicBoolean footerActionsControllerInitialized;
    public final FooterActionsViewModel.Factory footerActionsViewModelFactory;
    public final ReadonlyStateFlow isMediaVisible;
    public final NotificationsPlaceholderViewModel notifications;
    public final QSSceneAdapter qsSceneAdapter;
    public final ShadeHeaderViewModel shadeHeaderViewModel;

    public QuickSettingsSceneViewModel(CoroutineScope coroutineScope, BrightnessMirrorViewModel brightnessMirrorViewModel, ShadeHeaderViewModel shadeHeaderViewModel, QSSceneAdapter qSSceneAdapter, NotificationsPlaceholderViewModel notificationsPlaceholderViewModel, FooterActionsViewModel.Factory factory, FooterActionsController footerActionsController, SceneBackInteractor sceneBackInteractor, MediaCarouselInteractor mediaCarouselInteractor) {
        final SceneBackInteractor$special$$inlined$map$1 sceneBackInteractor$special$$inlined$map$1 = sceneBackInteractor.backScene;
        final Flow flow = new Flow() { // from class: com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$filter$1

            /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$filter$1$2$1
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
                        r6 = r5
                        com.android.compose.animation.scene.SceneKey r6 = (com.android.compose.animation.scene.SceneKey) r6
                        com.android.compose.animation.scene.SceneKey r2 = com.android.systemui.scene.shared.model.Scenes.QuickSettings
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        r6 = r6 ^ r3
                        if (r6 == 0) goto L49
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flow2 = new Flow() { // from class: com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L43
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.compose.animation.scene.SceneKey r5 = (com.android.compose.animation.scene.SceneKey) r5
                        if (r5 != 0) goto L38
                        com.android.compose.animation.scene.SceneKey r5 = com.android.systemui.scene.shared.model.Scenes.Shade
                    L38:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L43
                        return r1
                    L43:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow2, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Scenes.Shade);
        QSSceneAdapterImpl qSSceneAdapterImpl = (QSSceneAdapterImpl) qSSceneAdapter;
        this.destinationScenes = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(qSSceneAdapterImpl.isCustomizerShowing, stateIn, new QuickSettingsSceneViewModel$destinationScenes$1(this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), destinationScenes((SceneKey) stateIn.$$delegate_0.getValue(), ((Boolean) qSSceneAdapterImpl.isCustomizerShowing.$$delegate_0.getValue()).booleanValue()));
        ReadonlyStateFlow readonlyStateFlow = mediaCarouselInteractor.hasAnyMediaOrRecommendation;
        new AtomicBoolean(false);
    }

    public static MapBuilder destinationScenes(SceneKey sceneKey, boolean z) {
        MapBuilder mapBuilder = new MapBuilder();
        if (!z) {
            mapBuilder.put(Back.INSTANCE, new UserActionResult(sceneKey == null ? Scenes.Shade : sceneKey, null, 2, null));
            SwipeDirection swipeDirection = SwipeDirection.Up;
            Swipe swipe = new Swipe(swipeDirection, 0, null, 6, null);
            if (sceneKey == null) {
                sceneKey = Scenes.Shade;
            }
            mapBuilder.put(swipe, new UserActionResult(sceneKey, null, 2, null));
            mapBuilder.put(new Swipe(swipeDirection, 0, Edge.Bottom, 2, null), new UserActionResult(SceneFamilies.Home, null, 2, null));
        }
        return mapBuilder.build();
    }
}
