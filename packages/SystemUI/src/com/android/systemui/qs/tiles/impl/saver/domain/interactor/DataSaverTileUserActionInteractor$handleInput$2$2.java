package com.android.systemui.qs.tiles.impl.saver.domain.interactor;

import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.qs.tiles.impl.saver.domain.DataSaverDialogDelegate;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DataSaverTileUserActionInteractor$handleInput$2$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ QSTileInput $this_with;
    int label;
    final /* synthetic */ DataSaverTileUserActionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataSaverTileUserActionInteractor$handleInput$2$2(DataSaverTileUserActionInteractor dataSaverTileUserActionInteractor, QSTileInput qSTileInput, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataSaverTileUserActionInteractor;
        this.$this_with = qSTileInput;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DataSaverTileUserActionInteractor$handleInput$2$2(this.this$0, this.$this_with, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataSaverTileUserActionInteractor$handleInput$2$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        DialogTransitionAnimator.Controller dialogTransitionController;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DataSaverTileUserActionInteractor dataSaverTileUserActionInteractor = this.this$0;
        DataSaverDialogDelegate dataSaverDialogDelegate = new DataSaverDialogDelegate(dataSaverTileUserActionInteractor.systemUIDialogFactory, dataSaverTileUserActionInteractor.contextInteractor, dataSaverTileUserActionInteractor.backgroundContext, dataSaverTileUserActionInteractor.dataSaverController, dataSaverTileUserActionInteractor.sharedPreferences);
        DataSaverTileUserActionInteractor dataSaverTileUserActionInteractor2 = this.this$0;
        SystemUIDialog create = dataSaverTileUserActionInteractor2.systemUIDialogFactory.create(dataSaverDialogDelegate, ((ShadeDialogContextInteractorImpl) dataSaverTileUserActionInteractor2.contextInteractor).getContext());
        Expandable expandable = ((QSTileUserAction.Click) this.$this_with.action).expandable;
        if (expandable == null || (dialogTransitionController = expandable.dialogTransitionController(new DialogCuj(58, "start_data_saver"))) == null) {
            create.show();
        } else {
            DialogTransitionAnimator dialogTransitionAnimator = this.this$0.dialogTransitionAnimator;
            TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
            dialogTransitionAnimator.show(create, dialogTransitionController, false);
        }
        return Unit.INSTANCE;
    }
}
