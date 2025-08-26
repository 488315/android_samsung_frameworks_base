package com.android.systemui.user.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.users.UserCreatingDialog;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.tiles.UserDetailView;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.user.UserSwitchFullscreenDialog;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import com.android.systemui.user.domain.model.ShowDialogRequestModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import dagger.Lazy;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* loaded from: classes3.dex */
final class UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserSwitcherDialogCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1(UserSwitcherDialogCoordinator userSwitcherDialogCoordinator, Continuation continuation) {
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
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(((UserSwitcherInteractor) this.this$0.interactor.get()).dialogShowRequests);
            final UserSwitcherDialogCoordinator userSwitcherDialogCoordinator = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.dialog.UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Pair pair;
                    ShowDialogRequestModel showDialogRequestModel = (ShowDialogRequestModel) obj2;
                    UserSwitcherDialogCoordinator userSwitcherDialogCoordinator2 = userSwitcherDialogCoordinator;
                    Context context = ((ShadeDialogContextInteractorImpl) ((ShadeDialogContextInteractor) userSwitcherDialogCoordinator2.shadeDialogContextInteractor.get())).getContext();
                    boolean z = showDialogRequestModel instanceof ShowDialogRequestModel.ShowAddUserDialog;
                    Lazy lazy = userSwitcherDialogCoordinator2.dialogTransitionAnimator;
                    Lazy lazy2 = userSwitcherDialogCoordinator2.falsingManager;
                    if (z) {
                        ShowDialogRequestModel.ShowAddUserDialog showAddUserDialog = (ShowDialogRequestModel.ShowAddUserDialog) showDialogRequestModel;
                        pair = new Pair(new AddUserDialog(context, showAddUserDialog.userHandle, showAddUserDialog.isKeyguardShowing, showAddUserDialog.showEphemeralMessage, (FalsingManager) lazy2.get(), (BroadcastSender) userSwitcherDialogCoordinator2.broadcastSender.get(), (DialogTransitionAnimator) lazy.get()), new DialogCuj(59, "add_new_user"));
                    } else if (showDialogRequestModel instanceof ShowDialogRequestModel.ShowUserCreationDialog) {
                        pair = new Pair(new UserCreatingDialog(context, ((ShowDialogRequestModel.ShowUserCreationDialog) showDialogRequestModel).isGuest), null);
                    } else if (showDialogRequestModel instanceof ShowDialogRequestModel.ShowExitGuestDialog) {
                        ShowDialogRequestModel.ShowExitGuestDialog showExitGuestDialog = (ShowDialogRequestModel.ShowExitGuestDialog) showDialogRequestModel;
                        int i2 = showExitGuestDialog.guestUserId;
                        FalsingManager falsingManager = (FalsingManager) lazy2.get();
                        DialogTransitionAnimator dialogTransitionAnimator = (DialogTransitionAnimator) lazy.get();
                        UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0 userSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0 = new UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0(showExitGuestDialog.onExitGuestUser);
                        pair = new Pair(new ExitGuestDialog(context, i2, showExitGuestDialog.isGuestEphemeral, showExitGuestDialog.targetUserId, showExitGuestDialog.isKeyguardShowing, falsingManager, dialogTransitionAnimator, userSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0), new DialogCuj(59, "exit_guest_mode"));
                    } else if (showDialogRequestModel instanceof ShowDialogRequestModel.ShowUserSwitcherDialog) {
                        pair = new Pair(new UserSwitchDialog(context, (UserDetailView.Adapter) userSwitcherDialogCoordinator2.userDetailAdapterProvider.get(), (UiEventLogger) userSwitcherDialogCoordinator2.eventLogger.get(), (FalsingManager) lazy2.get(), (ActivityStarter) userSwitcherDialogCoordinator2.activityStarter.get(), (DialogTransitionAnimator) lazy.get()), new DialogCuj(59, "exit_guest_mode"));
                    } else {
                        if (!(showDialogRequestModel instanceof ShowDialogRequestModel.ShowUserSwitcherFullscreenDialog)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        pair = new Pair(new UserSwitchFullscreenDialog(context, (FalsingCollector) userSwitcherDialogCoordinator2.falsingCollector.get(), (UserSwitcherViewModel) userSwitcherDialogCoordinator2.userSwitcherViewModel.get()), null);
                    }
                    AlertDialog alertDialog = (AlertDialog) pair.component1();
                    userSwitcherDialogCoordinator2.currentDialog = alertDialog;
                    alertDialog.show();
                    ((UserSwitcherInteractor) userSwitcherDialogCoordinator2.interactor.get())._dialogShowRequests.setValue(null);
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
