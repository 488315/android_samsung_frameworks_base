package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.Flags;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.shade.domain.interactor.BaseShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackAppearanceInteractor;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

public final class NotificationScrollViewModel extends FlowDumperImpl {
    public final Function1 currentGestureOverscrollConsumer;
    public final Flow expandFraction;
    public final Lazy isDozing$delegate;
    public final Flow isScrollable;
    public final StateFlow maxAlpha;
    public final SceneInteractor sceneInteractor;
    public final StateFlow scrolledToTop;
    public final Flow shadeScrimClipping;
    public final Function1 syntheticScrollConsumer;

    public NotificationScrollViewModel(DumpManager dumpManager, NotificationStackAppearanceInteractor notificationStackAppearanceInteractor, ShadeInteractor shadeInteractor, SceneInteractor sceneInteractor, final dagger.Lazy lazy) {
        super(dumpManager, null, 2, null);
        this.sceneInteractor = sceneInteractor;
        BaseShadeInteractor baseShadeInteractor = ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor;
        dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.combine(baseShadeInteractor.getShadeExpansion(), baseShadeInteractor.getShadeMode(), baseShadeInteractor.getQsExpansion(), sceneInteractor.transitionState, sceneInteractor.resolveSceneFamily(SceneFamilies.QuickSettings), new NotificationScrollViewModel$expandFraction$1(this, null))), "expandFraction");
        dumpWhileCollecting(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(notificationStackAppearanceInteractor.shadeScrimBounds, notificationStackAppearanceInteractor.shadeScrimRounding, new NotificationScrollViewModel$shadeScrimClipping$1(null)), "stackClipping");
        dumpValue(notificationStackAppearanceInteractor.alphaForBrightnessMirror, "maxAlpha");
        dumpValue(notificationStackAppearanceInteractor.scrolledToTop, "scrolledToTop");
        new NotificationScrollViewModel$syntheticScrollConsumer$1(notificationStackAppearanceInteractor);
        new NotificationScrollViewModel$currentGestureOverscrollConsumer$1(notificationStackAppearanceInteractor);
        final ReadonlyStateFlow readonlyStateFlow = sceneInteractor.currentScene;
        dumpWhileCollecting(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NotificationScrollViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, NotificationScrollViewModel notificationScrollViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = notificationScrollViewModel;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5b
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.compose.animation.scene.SceneKey r5 = (com.android.compose.animation.scene.SceneKey) r5
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel r6 = r4.this$0
                        com.android.systemui.scene.domain.interactor.SceneInteractor r6 = r6.sceneInteractor
                        com.android.compose.animation.scene.SceneKey r2 = com.android.systemui.scene.shared.model.SceneFamilies.NotifShade
                        boolean r6 = r6.isSceneInFamily(r5, r2)
                        if (r6 != 0) goto L4b
                        com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.scene.shared.model.Scenes.Lockscreen
                        boolean r5 = r5.equals(r6)
                        if (r5 == 0) goto L49
                        goto L4b
                    L49:
                        r5 = 0
                        goto L4c
                    L4b:
                        r5 = r3
                    L4c:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5b
                        return r1
                    L5b:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, "isScrollable");
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel$isDozing$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = SceneContainerFlag.$r8$clinit;
                Flags.sceneContainer();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
    }
}
