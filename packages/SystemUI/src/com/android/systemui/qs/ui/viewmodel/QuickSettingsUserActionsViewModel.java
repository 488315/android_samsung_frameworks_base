package com.android.systemui.qs.ui.viewmodel;

import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor$special$$inlined$map$1;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0;
import java.util.Map;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickSettingsUserActionsViewModel extends UserActionsViewModel {
    public final QuickSettingsUserActionsViewModel$special$$inlined$map$1 backScene;
    public final QSSceneAdapter qsSceneAdapter;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        QuickSettingsUserActionsViewModel create();
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$map$1] */
    public QuickSettingsUserActionsViewModel(QSSceneAdapter qSSceneAdapter, SceneBackInteractor sceneBackInteractor) {
        this.qsSceneAdapter = qSSceneAdapter;
        final SceneBackInteractor$special$$inlined$map$1 sceneBackInteractor$special$$inlined$map$1 = sceneBackInteractor.backScene;
        final Flow flow = new Flow() { // from class: com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$filter$1$2$1
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
                        r6 = r5
                        com.android.compose.animation.scene.SceneKey r6 = (com.android.compose.animation.scene.SceneKey) r6
                        com.android.compose.animation.scene.SceneKey r2 = com.android.systemui.scene.shared.model.Scenes.QuickSettings
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r6 != 0) goto L48
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.backScene = new Flow() { // from class: com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$map$1$2$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.systemui.scene.ui.viewmodel.UserActionsViewModel
    public final Object hydrateActions(final UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation) {
        Object collect = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((QSSceneAdapterImpl) this.qsSceneAdapter).isCustomizerShowing, this.backScene, new QuickSettingsUserActionsViewModel$hydrateActions$2(null)).collect(new FlowCollector() { // from class: com.android.systemui.qs.ui.viewmodel.QuickSettingsUserActionsViewModel$hydrateActions$3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                Function1.this.mo779invoke((Map) obj);
                return Unit.INSTANCE;
            }
        }, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
