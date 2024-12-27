package com.android.systemui.communal.widgets;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$filter$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

final class EditWidgetsActivity$listenForTransitionAndChangeScene$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ EditWidgetsActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditWidgetsActivity$listenForTransitionAndChangeScene$1(EditWidgetsActivity editWidgetsActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = editWidgetsActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditWidgetsActivity$listenForTransitionAndChangeScene$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditWidgetsActivity$listenForTransitionAndChangeScene$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            EditWidgetsActivity editWidgetsActivity = this.this$0;
            CommunalEditModeViewModel$special$$inlined$filter$1 communalEditModeViewModel$special$$inlined$filter$1 = editWidgetsActivity.communalViewModel.canShowEditMode;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(editWidgetsActivity);
            this.label = 1;
            if (communalEditModeViewModel$special$$inlined$filter$1.collect(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    /* renamed from: com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ EditWidgetsActivity this$0;

        /* renamed from: com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00771 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;

            public C00771(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00771 c00771 = new C00771(continuation);
                c00771.L$0 = obj;
                return c00771;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00771) create((SceneKey) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Boolean.valueOf(Intrinsics.areEqual((SceneKey) this.L$0, CommunalScenes.Blank));
            }
        }

        public AnonymousClass1(EditWidgetsActivity editWidgetsActivity) {
            this.this$0 = editWidgetsActivity;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(boolean r6, kotlin.coroutines.Continuation r7) {
            /*
                r5 = this;
                boolean r6 = r7 instanceof com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1
                if (r6 == 0) goto L13
                r6 = r7
                com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1 r6 = (com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1) r6
                int r0 = r6.label
                r1 = -2147483648(0xffffffff80000000, float:-0.0)
                r2 = r0 & r1
                if (r2 == 0) goto L13
                int r0 = r0 - r1
                r6.label = r0
                goto L18
            L13:
                com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1 r6 = new com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1
                r6.<init>(r5, r7)
            L18:
                java.lang.Object r7 = r6.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r6.label
                r2 = 1
                if (r1 == 0) goto L33
                if (r1 != r2) goto L2b
                java.lang.Object r5 = r6.L$0
                com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1 r5 = (com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1.AnonymousClass1) r5
                kotlin.ResultKt.throwOnFailure(r7)
                goto L61
            L2b:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L33:
                kotlin.ResultKt.throwOnFailure(r7)
                com.android.systemui.communal.widgets.EditWidgetsActivity r7 = r5.this$0
                com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel r1 = r7.communalViewModel
                com.android.compose.animation.scene.SceneKey r3 = com.android.systemui.communal.shared.model.CommunalScenes.Blank
                com.android.systemui.communal.shared.model.CommunalTransitionKeys r4 = com.android.systemui.communal.shared.model.CommunalTransitionKeys.INSTANCE
                r4.getClass()
                com.android.compose.animation.scene.TransitionKey r4 = com.android.systemui.communal.shared.model.CommunalTransitionKeys.ToEditMode
                com.android.systemui.communal.domain.interactor.CommunalSceneInteractor r1 = r1.communalSceneInteractor
                com.android.systemui.communal.data.repository.CommunalSceneRepository r1 = r1.communalSceneRepository
                com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl r1 = (com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl) r1
                r1.changeScene(r3, r4)
                com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel r7 = r7.communalViewModel
                kotlinx.coroutines.flow.Flow r7 = r7.currentScene
                com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$1 r1 = new com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$1
                r3 = 0
                r1.<init>(r3)
                r6.L$0 = r5
                r6.label = r2
                java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.first(r7, r1, r6)
                if (r6 != r0) goto L61
                return r0
            L61:
                com.android.systemui.communal.widgets.EditWidgetsActivity r5 = r5.this$0
                com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel r5 = r5.communalViewModel
                com.android.systemui.communal.shared.model.EditModeState r6 = com.android.systemui.communal.shared.model.EditModeState.SHOWING
                com.android.systemui.communal.domain.interactor.CommunalSceneInteractor r5 = r5.communalSceneInteractor
                kotlinx.coroutines.flow.StateFlowImpl r5 = r5._editModeState
                r5.setValue(r6)
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1.AnonymousClass1.emit(boolean, kotlin.coroutines.Continuation):java.lang.Object");
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Boolean) obj).booleanValue(), continuation);
        }
    }
}
