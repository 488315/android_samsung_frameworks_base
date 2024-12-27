package com.android.systemui.communal.widgets;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.IWindowManager;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.shared.model.CommunalTransitionKeys;
import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

final class EditWidgetsActivity$onEditDone$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ EditWidgetsActivity this$0;

    /* renamed from: com.android.systemui.communal.widgets.EditWidgetsActivity$onEditDone$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.Z$0);
        }
    }

    public EditWidgetsActivity$onEditDone$1(EditWidgetsActivity editWidgetsActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = editWidgetsActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditWidgetsActivity$onEditDone$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EditWidgetsActivity$onEditDone$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IWindowManager iWindowManager;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.this$0.communalViewModel.communalSceneInteractor._editModeState.setValue(null);
            CommunalEditModeViewModel communalEditModeViewModel = this.this$0.communalViewModel;
            SceneKey sceneKey = CommunalScenes.Communal;
            CommunalTransitionKeys.INSTANCE.getClass();
            ((CommunalSceneRepositoryImpl) communalEditModeViewModel.communalSceneInteractor.communalSceneRepository).changeScene(sceneKey, CommunalTransitionKeys.FromEditMode);
            ReadonlyStateFlow readonlyStateFlow = this.this$0.communalViewModel.isIdleOnCommunal;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
            this.label = 1;
            if (FlowKt.first(readonlyStateFlow, anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        EditWidgetsActivity editWidgetsActivity = this.this$0;
        int i2 = EditWidgetsActivity.$r8$clinit;
        editWidgetsActivity.getClass();
        try {
            iWindowManager = editWidgetsActivity.windowManagerService;
        } catch (RemoteException unused) {
            Log.e("EditWidgetsActivity", "Couldn't lock the device as WindowManager is dead.");
        }
        if (iWindowManager == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        iWindowManager.lockNow((Bundle) null);
        this.this$0.finish();
        return Unit.INSTANCE;
    }
}
