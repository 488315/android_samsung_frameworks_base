package com.android.systemui.communal.widgets;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.IWindowManager;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.shared.model.CommunalTransitionKeys;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class EditWidgetsActivity$onEditDone$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ EditWidgetsActivity this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
            CommunalEditModeViewModel communalEditModeViewModel = this.this$0.communalViewModel;
            communalEditModeViewModel.communalSceneInteractor._editModeState.setValue(null);
            int i2 = communalEditModeViewModel.currentScrollIndex;
            int i3 = communalEditModeViewModel.currentScrollOffset;
            CommunalInteractor communalInteractor = ((BaseCommunalViewModel) communalEditModeViewModel).communalInteractor;
            communalInteractor._firstVisibleItemIndex = i2;
            communalInteractor._firstVisibleItemOffset = i3;
            CommunalEditModeViewModel communalEditModeViewModel2 = this.this$0.communalViewModel;
            SceneKey sceneKey = CommunalScenes.Communal;
            CommunalTransitionKeys.INSTANCE.getClass();
            BaseCommunalViewModel.changeScene$default(communalEditModeViewModel2, sceneKey, "edit mode closing", CommunalTransitionKeys.FromEditMode, 8);
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
        int i4 = EditWidgetsActivity.$r8$clinit;
        editWidgetsActivity.getClass();
        try {
            iWindowManager = editWidgetsActivity.windowManagerService;
        } catch (RemoteException unused) {
            Log.e("EditWidgetsActivity", "Couldn't lock the device as WindowManager is dead.");
        }
        if (iWindowManager == null) {
            throw new IllegalStateException("Required value was null.");
        }
        iWindowManager.lockNow((Bundle) null);
        this.this$0.finish();
        return Unit.INSTANCE;
    }
}
