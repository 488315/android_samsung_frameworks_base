package com.android.systemui.communal.ui.compose;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.ui.viewmodel.CommunalUserActionsViewModel;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.communal.util.CommunalColors;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.Scene;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class CommunalScene extends ExclusiveActivatable implements Scene {
    public final CommunalUserActionsViewModel actionsViewModel;
    public final CommunalColors communalColors;
    public final CommunalContent communalContent;
    public final CommunalViewModel contentViewModel;
    public final SceneKey key = Scenes.Communal;
    public final ReadonlyStateFlow userActions;

    /* renamed from: com.android.systemui.communal.ui.compose.CommunalScene$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CommunalScene.this.onActivated(this);
        }
    }

    public CommunalScene(CommunalViewModel communalViewModel, CommunalUserActionsViewModel.Factory factory, CommunalColors communalColors, CommunalContent communalContent) {
        this.contentViewModel = communalViewModel;
        this.communalColors = communalColors;
        this.communalContent = communalContent;
        CommunalUserActionsViewModel communalUserActionsViewModelCreate = factory.create();
        this.actionsViewModel = communalUserActionsViewModelCreate;
        this.userActions = communalUserActionsViewModelCreate.actions;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.label = 1;
            if (this.actionsViewModel.activate(anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
