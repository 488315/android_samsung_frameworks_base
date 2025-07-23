package com.android.systemui.statusbar.policy.ui.dialog;

import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ModesDialogDelegate$showDialog$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Expandable $expandable;
    int label;
    final /* synthetic */ ModesDialogDelegate this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ModesDialogDelegate$showDialog$2(ModesDialogDelegate modesDialogDelegate, Expandable expandable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = modesDialogDelegate;
        this.$expandable = expandable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ModesDialogDelegate$showDialog$2(this.this$0, this.$expandable, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ModesDialogDelegate$showDialog$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        DialogTransitionAnimator.Controller dialogTransitionController;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ModesDialogDelegate modesDialogDelegate = this.this$0;
        if (modesDialogDelegate.currentDialog == null) {
            modesDialogDelegate.createDialog();
        }
        Expandable expandable = this.$expandable;
        if (expandable == null || (dialogTransitionController = expandable.dialogTransitionController(new DialogCuj(58, "configure_priority_modes"))) == null) {
            ComponentSystemUIDialog componentSystemUIDialog = this.this$0.currentDialog;
            componentSystemUIDialog.getClass();
            componentSystemUIDialog.show();
        } else {
            ModesDialogDelegate modesDialogDelegate2 = this.this$0;
            DialogTransitionAnimator dialogTransitionAnimator = modesDialogDelegate2.dialogTransitionAnimator;
            ComponentSystemUIDialog componentSystemUIDialog2 = modesDialogDelegate2.currentDialog;
            componentSystemUIDialog2.getClass();
            TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
            dialogTransitionAnimator.show(componentSystemUIDialog2, dialogTransitionController, false);
        }
        return Unit.INSTANCE;
    }
}
