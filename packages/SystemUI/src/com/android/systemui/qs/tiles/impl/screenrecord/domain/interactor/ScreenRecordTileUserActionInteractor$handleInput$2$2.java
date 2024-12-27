package com.android.systemui.qs.tiles.impl.screenrecord.domain.interactor;

import android.app.Dialog;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class ScreenRecordTileUserActionInteractor$handleInput$2$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ QSTileInput $this_with;
    int label;
    final /* synthetic */ ScreenRecordTileUserActionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenRecordTileUserActionInteractor$handleInput$2$2(ScreenRecordTileUserActionInteractor screenRecordTileUserActionInteractor, QSTileInput qSTileInput, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenRecordTileUserActionInteractor;
        this.$this_with = qSTileInput;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenRecordTileUserActionInteractor$handleInput$2$2(this.this$0, this.$this_with, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenRecordTileUserActionInteractor$handleInput$2$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final ScreenRecordTileUserActionInteractor screenRecordTileUserActionInteractor = this.this$0;
        final Expandable expandable = this.$this_with.action.getExpandable();
        final int identifier = this.$this_with.user.getIdentifier();
        int i = ScreenRecordTileUserActionInteractor.$r8$clinit;
        screenRecordTileUserActionInteractor.getClass();
        final SystemUIDialog createScreenRecordDialog = screenRecordTileUserActionInteractor.recordingController.createScreenRecordDialog(screenRecordTileUserActionInteractor.featureFlags, new Runnable() { // from class: com.android.systemui.qs.tiles.impl.screenrecord.domain.interactor.ScreenRecordTileUserActionInteractor$showPrompt$onStartRecordingClicked$1
            @Override // java.lang.Runnable
            public final void run() {
                ScreenRecordTileUserActionInteractor.this.dialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                ScreenRecordTileUserActionInteractor.this.panelInteractor.collapsePanels();
            }
        });
        final boolean z = (expandable == null || screenRecordTileUserActionInteractor.keyguardInteractor.isKeyguardShowing()) ? false : true;
        screenRecordTileUserActionInteractor.keyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.impl.screenrecord.domain.interactor.ScreenRecordTileUserActionInteractor$showPrompt$dismissAction$1
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                boolean z2 = z;
                ScreenRecordTileUserActionInteractor screenRecordTileUserActionInteractor2 = screenRecordTileUserActionInteractor;
                if (z2) {
                    Unit unit = null;
                    Expandable expandable2 = expandable;
                    DialogTransitionAnimator.Controller dialogTransitionController = expandable2 != null ? expandable2.dialogTransitionController(new DialogCuj(58, "screen_record")) : null;
                    if (dialogTransitionController != null) {
                        Dialog dialog = createScreenRecordDialog;
                        DialogTransitionAnimator dialogTransitionAnimator = screenRecordTileUserActionInteractor2.dialogTransitionAnimator;
                        Intrinsics.checkNotNull(dialog);
                        dialogTransitionAnimator.show(dialog, dialogTransitionController, true);
                        unit = Unit.INSTANCE;
                    }
                    if (unit == null) {
                        createScreenRecordDialog.show();
                    }
                } else {
                    createScreenRecordDialog.show();
                }
                screenRecordTileUserActionInteractor2.mediaProjectionMetricsLogger.notifyPermissionRequestDisplayed(identifier);
                return false;
            }
        }, false, true);
        return Unit.INSTANCE;
    }
}
