package com.android.systemui.communal.widgets;

import androidx.lifecycle.LifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.shared.model.CommunalTransitionKeys;
import com.android.systemui.communal.shared.model.EditModeState;
import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel;
import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$filter$1;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

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

    /* renamed from: com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ EditWidgetsActivity this$0;

        /* renamed from: com.android.systemui.communal.widgets.EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C01721 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;

            public C01721(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C01721 c01721 = new C01721(continuation);
                c01721.L$0 = obj;
                return c01721;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01721) create((SceneKey) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

        /* JADX WARN: Code restructure failed: missing block: B:20:0x007f, code lost:
        
            if (kotlinx.coroutines.flow.FlowKt.first(r11, r1, r10) == r0) goto L21;
         */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(boolean z, Continuation continuation) {
            EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1 editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1;
            if (continuation instanceof EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1) {
                editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1 = (EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1) continuation;
                int i = editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1 = new EditWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1(this, continuation);
                }
            }
            Object obj = editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                EditWidgetsActivity editWidgetsActivity = this.this$0;
                CommunalEditModeViewModel communalEditModeViewModel = editWidgetsActivity.communalViewModel;
                SceneKey sceneKey = CommunalScenes.Blank;
                CommunalTransitionKeys.INSTANCE.getClass();
                communalEditModeViewModel.communalSceneInteractor.changeScene(sceneKey, "edit mode opening", CommunalTransitionKeys.ToEditMode, KeyguardState.GONE);
                ReadonlyStateFlow readonlyStateFlow = editWidgetsActivity.communalViewModel.currentScene;
                C01721 c01721 = new C01721(null);
                editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1.L$0 = this;
                editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1.label = 1;
                if (FlowKt.first(readonlyStateFlow, c01721, editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                this = (AnonymousClass1) editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1.L$0;
                ResultKt.throwOnFailure(obj);
                CommunalEditModeViewModel communalEditModeViewModel2 = this.this$0.communalViewModel;
                communalEditModeViewModel2.communalSceneInteractor._editModeState.setValue(EditModeState.SHOWING);
                EditWidgetsActivity editWidgetsActivity2 = this.this$0;
                editWidgetsActivity2.activityController.activityFullyVisible = true;
                if (editWidgetsActivity2.shouldOpenWidgetPickerOnStart) {
                    CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(editWidgetsActivity2.lifecycleRegistry), null, null, new EditWidgetsActivity$onOpenWidgetPicker$1(editWidgetsActivity2, null), 7);
                    editWidgetsActivity2.shouldOpenWidgetPickerOnStart = false;
                }
                return Unit.INSTANCE;
            }
            this = (AnonymousClass1) editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1.L$0;
            ResultKt.throwOnFailure(obj);
            ReadonlyStateFlow readonlyStateFlow2 = this.this$0.keyguardInteractor.isDreaming;
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1.L$0 = this;
            editWidgetsActivity$listenForTransitionAndChangeScene$1$1$emit$1.label = 2;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Boolean) obj).booleanValue(), continuation);
        }
    }
}
