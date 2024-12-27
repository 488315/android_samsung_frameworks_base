package com.android.systemui.qs.tiles.impl.saver.domain.interactor;

import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.impl.saver.domain.DataSaverDialogDelegate;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class DataSaverTileUserActionInteractor$handleInput$2$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ QSTileInput $this_with;
    int label;
    final /* synthetic */ DataSaverTileUserActionInteractor this$0;

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
        Unit unit;
        DialogTransitionAnimator.Controller dialogTransitionController;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DataSaverTileUserActionInteractor dataSaverTileUserActionInteractor = this.this$0;
        DataSaverDialogDelegate dataSaverDialogDelegate = new DataSaverDialogDelegate(dataSaverTileUserActionInteractor.systemUIDialogFactory, dataSaverTileUserActionInteractor.context, dataSaverTileUserActionInteractor.backgroundContext, dataSaverTileUserActionInteractor.dataSaverController, dataSaverTileUserActionInteractor.sharedPreferences);
        DataSaverTileUserActionInteractor dataSaverTileUserActionInteractor2 = this.this$0;
        SystemUIDialog create = dataSaverTileUserActionInteractor2.systemUIDialogFactory.create(dataSaverDialogDelegate, dataSaverTileUserActionInteractor2.context);
        Expandable expandable = this.$this_with.action.getExpandable();
        if (expandable == null || (dialogTransitionController = expandable.dialogTransitionController(new DialogCuj(58, "start_data_saver"))) == null) {
            unit = null;
        } else {
            DialogTransitionAnimator dialogTransitionAnimator = this.this$0.dialogTransitionAnimator;
            TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
            dialogTransitionAnimator.show(create, dialogTransitionController, false);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            create.show();
        }
        return Unit.INSTANCE;
    }
}
