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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ EditWidgetsActivity this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00901 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;

            public C00901(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00901 c00901 = new C00901(continuation);
                c00901.L$0 = obj;
                return c00901;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00901) create((SceneKey) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            /* synthetic */ boolean Z$0;
            int label;

            public AnonymousClass2(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
                anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
                return anonymousClass2;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Boolean.valueOf(!this.Z$0);
            }
        }

        public AnonymousClass1(EditWidgetsActivity editWidgetsActivity) {
            this.this$0 = editWidgetsActivity;
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x007f, code lost:
        
            if (kotlinx.coroutines.flow.FlowKt.first(r11, r1, r10) != r0) goto L22;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0081, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0069, code lost:
        
            if (kotlinx.coroutines.flow.FlowKt.first(r11, r1, r10) == r0) goto L21;
         */
        /* JADX WARN: Removed duplicated region for block: B:23:0x003f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(boolean r10, kotlin.coroutines.Continuation r11) {
            /*
                r9 = this;
                boolean r10 = r11 instanceof com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1
                if (r10 == 0) goto L13
                r10 = r11
                com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1 r10 = (com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1) r10
                int r0 = r10.label
                r1 = -2147483648(0xffffffff80000000, float:-0.0)
                r2 = r0 & r1
                if (r2 == 0) goto L13
                int r0 = r0 - r1
                r10.label = r0
                goto L18
            L13:
                com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1 r10 = new com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1
                r10.<init>(r9, r11)
            L18:
                java.lang.Object r11 = r10.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r10.label
                r2 = 0
                r3 = 2
                r4 = 1
                if (r1 == 0) goto L3f
                if (r1 == r4) goto L37
                if (r1 != r3) goto L2f
                java.lang.Object r9 = r10.L$0
                com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1 r9 = (com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1.AnonymousClass1) r9
                kotlin.ResultKt.throwOnFailure(r11)
                goto L82
            L2f:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r10)
                throw r9
            L37:
                java.lang.Object r9 = r10.L$0
                com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1 r9 = (com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1.AnonymousClass1) r9
                kotlin.ResultKt.throwOnFailure(r11)
                goto L6c
            L3f:
                kotlin.ResultKt.throwOnFailure(r11)
                com.android.systemui.communal.widgets.EditWidgetsActivity r11 = r9.this$0
                com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel r1 = r11.communalViewModel
                com.android.compose.animation.scene.SceneKey r5 = com.android.systemui.communal.shared.model.CommunalScenes.Blank
                com.android.systemui.communal.shared.model.CommunalTransitionKeys r6 = com.android.systemui.communal.shared.model.CommunalTransitionKeys.INSTANCE
                r6.getClass()
                com.android.compose.animation.scene.TransitionKey r6 = com.android.systemui.communal.shared.model.CommunalTransitionKeys.ToEditMode
                com.android.systemui.keyguard.shared.model.KeyguardState r7 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
                com.android.systemui.communal.domain.interactor.CommunalSceneInteractor r1 = r1.communalSceneInteractor
                java.lang.String r8 = "edit mode opening"
                r1.changeScene(r5, r8, r6, r7)
                com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel r11 = r11.communalViewModel
                kotlinx.coroutines.flow.ReadonlyStateFlow r11 = r11.currentScene
                com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$1 r1 = new com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$1
                r1.<init>(r2)
                r10.L$0 = r9
                r10.label = r4
                java.lang.Object r11 = kotlinx.coroutines.flow.FlowKt.first(r11, r1, r10)
                if (r11 != r0) goto L6c
                goto L81
            L6c:
                com.android.systemui.communal.widgets.EditWidgetsActivity r11 = r9.this$0
                com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r11 = r11.keyguardInteractor
                kotlinx.coroutines.flow.ReadonlyStateFlow r11 = r11.isDreaming
                com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$2 r1 = new com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$2
                r1.<init>(r2)
                r10.L$0 = r9
                r10.label = r3
                java.lang.Object r10 = kotlinx.coroutines.flow.FlowKt.first(r11, r1, r10)
                if (r10 != r0) goto L82
            L81:
                return r0
            L82:
                com.android.systemui.communal.widgets.EditWidgetsActivity r10 = r9.this$0
                com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel r10 = r10.communalViewModel
                com.android.systemui.communal.shared.model.EditModeState r11 = com.android.systemui.communal.shared.model.EditModeState.SHOWING
                com.android.systemui.communal.domain.interactor.CommunalSceneInteractor r10 = r10.communalSceneInteractor
                kotlinx.coroutines.flow.StateFlowImpl r10 = r10._editModeState
                r10.setValue(r11)
                com.android.systemui.communal.widgets.EditWidgetsActivity r9 = r9.this$0
                com.android.systemui.communal.widgets.EditWidgetsActivity$ActivityControllerImpl r10 = r9.activityController
                r10.activityFullyVisible = r4
                boolean r10 = r9.shouldOpenWidgetPickerOnStart
                if (r10 == 0) goto Lab
                androidx.lifecycle.LifecycleRegistry r10 = r9.lifecycleRegistry
                androidx.lifecycle.LifecycleCoroutineScopeImpl r10 = androidx.lifecycle.LifecycleKt.getCoroutineScope(r10)
                com.android.systemui.communal.widgets.EditWidgetsActivity$onOpenWidgetPicker$1 r11 = new com.android.systemui.communal.widgets.EditWidgetsActivity$onOpenWidgetPicker$1
                r11.<init>(r9, r2)
                r0 = 7
                com.android.app.tracing.coroutines.CoroutineTracingKt.launchTraced$default(r10, r2, r2, r11, r0)
                r10 = 0
                r9.shouldOpenWidgetPickerOnStart = r10
            Lab:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1.AnonymousClass1.emit(boolean, kotlin.coroutines.Continuation):java.lang.Object");
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Boolean) obj).booleanValue(), continuation);
        }
    }
}
