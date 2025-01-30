package com.android.systemui.user.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.users.UserCreatingDialog;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.animation.ViewDialogLaunchAnimatorController;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.qs.tiles.UserDetailView;
import com.android.systemui.qs.user.UserSwitchDialogController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.user.UserSwitchFullscreenDialog;
import com.android.systemui.user.domain.interactor.UserInteractor;
import com.android.systemui.user.domain.model.ShowDialogRequestModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.ui.dialog.UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1", m277f = "UserSwitcherDialogCoordinator.kt", m278l = {73}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserSwitcherDialogCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1(UserSwitcherDialogCoordinator userSwitcherDialogCoordinator, Continuation<? super UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1> continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherDialogCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(((UserInteractor) this.this$0.interactor.get()).dialogShowRequests);
            final UserSwitcherDialogCoordinator userSwitcherDialogCoordinator = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.dialog.UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Pair pair;
                    ViewDialogLaunchAnimatorController viewDialogLaunchAnimatorController;
                    ShowDialogRequestModel showDialogRequestModel = (ShowDialogRequestModel) obj2;
                    boolean z = showDialogRequestModel instanceof ShowDialogRequestModel.ShowAddUserDialog;
                    UserSwitcherDialogCoordinator userSwitcherDialogCoordinator2 = UserSwitcherDialogCoordinator.this;
                    if (z) {
                        ShowDialogRequestModel.ShowAddUserDialog showAddUserDialog = (ShowDialogRequestModel.ShowAddUserDialog) showDialogRequestModel;
                        pair = new Pair(new AddUserDialog((Context) userSwitcherDialogCoordinator2.context.get(), showAddUserDialog.userHandle, showAddUserDialog.isKeyguardShowing, showAddUserDialog.showEphemeralMessage, (FalsingManager) userSwitcherDialogCoordinator2.falsingManager.get(), (BroadcastSender) userSwitcherDialogCoordinator2.broadcastSender.get(), (DialogLaunchAnimator) userSwitcherDialogCoordinator2.dialogLaunchAnimator.get()), new DialogCuj(59, "add_new_user"));
                    } else if (showDialogRequestModel instanceof ShowDialogRequestModel.ShowUserCreationDialog) {
                        pair = new Pair(new UserCreatingDialog((Context) userSwitcherDialogCoordinator2.context.get(), ((ShowDialogRequestModel.ShowUserCreationDialog) showDialogRequestModel).isGuest), null);
                    } else if (showDialogRequestModel instanceof ShowDialogRequestModel.ShowExitGuestDialog) {
                        ShowDialogRequestModel.ShowExitGuestDialog showExitGuestDialog = (ShowDialogRequestModel.ShowExitGuestDialog) showDialogRequestModel;
                        pair = new Pair(new ExitGuestDialog((Context) userSwitcherDialogCoordinator2.context.get(), showExitGuestDialog.guestUserId, showExitGuestDialog.isGuestEphemeral, showExitGuestDialog.targetUserId, showExitGuestDialog.isKeyguardShowing, (FalsingManager) userSwitcherDialogCoordinator2.falsingManager.get(), (DialogLaunchAnimator) userSwitcherDialogCoordinator2.dialogLaunchAnimator.get(), new C3566x48b13c6a(showExitGuestDialog.onExitGuestUser)), new DialogCuj(59, "exit_guest_mode"));
                    } else if (showDialogRequestModel instanceof ShowDialogRequestModel.ShowUserSwitcherDialog) {
                        pair = new Pair(new UserSwitchDialog((Context) userSwitcherDialogCoordinator2.context.get(), (UserDetailView.Adapter) userSwitcherDialogCoordinator2.userDetailAdapterProvider.get(), (UiEventLogger) userSwitcherDialogCoordinator2.eventLogger.get(), (FalsingManager) userSwitcherDialogCoordinator2.falsingManager.get(), (ActivityStarter) userSwitcherDialogCoordinator2.activityStarter.get(), (DialogLaunchAnimator) userSwitcherDialogCoordinator2.dialogLaunchAnimator.get()), new DialogCuj(59, "exit_guest_mode"));
                    } else {
                        if (!(showDialogRequestModel instanceof ShowDialogRequestModel.ShowUserSwitcherFullscreenDialog)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        pair = new Pair(new UserSwitchFullscreenDialog((Context) userSwitcherDialogCoordinator2.context.get(), (FalsingCollector) userSwitcherDialogCoordinator2.falsingCollector.get(), (UserSwitcherViewModel) userSwitcherDialogCoordinator2.userSwitcherViewModel.get()), null);
                    }
                    AlertDialog alertDialog = (AlertDialog) pair.component1();
                    DialogCuj dialogCuj = (DialogCuj) pair.component2();
                    userSwitcherDialogCoordinator2.currentDialog = alertDialog;
                    Expandable expandable = showDialogRequestModel.getExpandable();
                    if (expandable != null) {
                        DialogLaunchAnimator.Controller.Companion.getClass();
                        viewDialogLaunchAnimatorController = DialogLaunchAnimator.Controller.Companion.fromView(((Expandable$Companion$fromView$1) expandable).$view, dialogCuj);
                    } else {
                        viewDialogLaunchAnimatorController = null;
                    }
                    if (viewDialogLaunchAnimatorController != null) {
                        DialogLaunchAnimator dialogLaunchAnimator = (DialogLaunchAnimator) userSwitcherDialogCoordinator2.dialogLaunchAnimator.get();
                        LaunchAnimator.Timings timings = DialogLaunchAnimator.TIMINGS;
                        dialogLaunchAnimator.show(alertDialog, viewDialogLaunchAnimatorController, false);
                    } else if (showDialogRequestModel.getDialogShower() == null || dialogCuj == null) {
                        alertDialog.show();
                    } else {
                        UserSwitchDialogController.DialogShower dialogShower = showDialogRequestModel.getDialogShower();
                        if (dialogShower != null) {
                            DialogShowerImpl dialogShowerImpl = (DialogShowerImpl) dialogShower;
                            DialogLaunchAnimator dialogLaunchAnimator2 = dialogShowerImpl.dialogLaunchAnimator;
                            Dialog dialog = dialogShowerImpl.animateFrom;
                            LaunchAnimator.Timings timings2 = DialogLaunchAnimator.TIMINGS;
                            dialogLaunchAnimator2.showFromDialog(alertDialog, dialog, dialogCuj, false);
                        }
                    }
                    ((UserInteractor) userSwitcherDialogCoordinator2.interactor.get())._dialogShowRequests.setValue(null);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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
}
