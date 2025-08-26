package com.android.systemui.qs.tiles;

import android.os.UserHandle;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.qs.tiles.dialog.ScreenRecordDetailsViewModel;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class ScreenRecordTile$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenRecordTile f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ScreenRecordTile$$ExternalSyntheticLambda0(ScreenRecordTile screenRecordTile, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = screenRecordTile;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                final ScreenRecordTile screenRecordTile = this.f$0;
                final ScreenRecordTile$$ExternalSyntheticLambda0 screenRecordTile$$ExternalSyntheticLambda0 = new ScreenRecordTile$$ExternalSyntheticLambda0(screenRecordTile, (Consumer) this.f$1, 1);
                screenRecordTile.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda5
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        Runnable runnable = screenRecordTile$$ExternalSyntheticLambda0;
                        ScreenRecordTile screenRecordTile2 = screenRecordTile;
                        screenRecordTile2.getClass();
                        runnable.run();
                        screenRecordTile2.mMediaProjectionMetricsLogger.notifyPermissionRequestDisplayed(((UserTrackerImpl) screenRecordTile2.mUserContextProvider).getUserContext().getUserId());
                        return false;
                    }
                }, false, true);
                break;
            case 1:
                final ScreenRecordTile screenRecordTile2 = this.f$0;
                Consumer consumer = (Consumer) this.f$1;
                RecordingController recordingController = screenRecordTile2.mController;
                if (!((ScreenCaptureDevicePolicyResolver) recordingController.mDevicePolicyResolver.get()).isScreenCaptureCompletelyDisabled(UserHandle.of(UserHandle.myUserId()))) {
                    consumer.accept(new ScreenRecordDetailsViewModel(recordingController, new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ScreenRecordTile screenRecordTile3 = screenRecordTile2;
                            screenRecordTile3.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                            ((PanelInteractorImpl) screenRecordTile3.mPanelInteractor).collapsePanels();
                        }
                    }));
                    break;
                } else {
                    screenRecordTile2.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                    ((PanelInteractorImpl) screenRecordTile2.mPanelInteractor).collapsePanels();
                    Toast.makeText(screenRecordTile2.mContext, R.string.screen_capturing_disabled_by_policy_dialog_description, 0).show();
                    break;
                }
            default:
                final ScreenRecordTile screenRecordTile3 = this.f$0;
                final Expandable expandable = (Expandable) this.f$1;
                screenRecordTile3.getClass();
                final SystemUIDialog systemUIDialogCreateScreenRecordDialog = screenRecordTile3.mController.createScreenRecordDialog(new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScreenRecordTile screenRecordTile32 = screenRecordTile3;
                        screenRecordTile32.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                        ((PanelInteractorImpl) screenRecordTile32.mPanelInteractor).collapsePanels();
                    }
                });
                final Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScreenRecordTile screenRecordTile4 = screenRecordTile3;
                        Expandable expandable2 = expandable;
                        SystemUIDialog systemUIDialog = systemUIDialogCreateScreenRecordDialog;
                        if (expandable2 == null) {
                            screenRecordTile4.getClass();
                        } else if (!((KeyguardStateControllerImpl) screenRecordTile4.mKeyguardStateController).mShowing) {
                            DialogTransitionAnimator.Controller controllerDialogTransitionController = expandable2.dialogTransitionController(new DialogCuj(58, "screen_record"));
                            if (controllerDialogTransitionController != null) {
                                screenRecordTile4.mDialogTransitionAnimator.show(systemUIDialog, controllerDialogTransitionController, true);
                                return;
                            } else {
                                systemUIDialog.show();
                                return;
                            }
                        }
                        systemUIDialog.show();
                    }
                };
                screenRecordTile3.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda5
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        Runnable runnable2 = runnable;
                        ScreenRecordTile screenRecordTile22 = screenRecordTile3;
                        screenRecordTile22.getClass();
                        runnable2.run();
                        screenRecordTile22.mMediaProjectionMetricsLogger.notifyPermissionRequestDisplayed(((UserTrackerImpl) screenRecordTile22.mUserContextProvider).getUserContext().getUserId());
                        return false;
                    }
                }, false, true);
                break;
        }
    }
}
