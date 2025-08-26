package com.android.systemui.media.controls.ui.controller;

import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.media.session.MediaSession;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.ViewDialogTransitionAnimatorController;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda6 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaControlPanel f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda6(MediaControlPanel mediaControlPanel, PendingIntent pendingIntent, String str) {
        this.$r8$classId = 0;
        this.f$0 = mediaControlPanel;
        this.f$1 = pendingIntent;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0065  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onClick(View view) throws PendingIntent.CanceledException {
        ViewDialogTransitionAnimatorController viewDialogTransitionAnimatorControllerFromView;
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                MediaControlPanel mediaControlPanel = this.f$0;
                PendingIntent pendingIntent = (PendingIntent) this.f$1;
                if (!mediaControlPanel.mFalsingManager.isFalseTap(1) && !mediaControlPanel.mMediaViewController.isGutsVisible) {
                    mediaControlPanel.mLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_TAP_CONTENT_VIEW, mediaControlPanel.mUid, mediaControlPanel.mPackageName, mediaControlPanel.mInstanceId);
                    if (((KeyguardStateControllerImpl) mediaControlPanel.mKeyguardStateController).mShowing) {
                        if (mediaControlPanel.mActivityIntentHelper.wouldPendingShowOverLockscreen(((NotificationLockscreenUserManagerImpl) mediaControlPanel.mLockscreenUserManager).mCurrentUserId, pendingIntent)) {
                            mediaControlPanel.mActivityStarter.startPendingIntentMaybeDismissingKeyguard(pendingIntent, true, null, mediaControlPanel.buildLaunchAnimatorController(mediaControlPanel.mMediaViewHolder.player), null, null, null);
                            break;
                        }
                    }
                    mediaControlPanel.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent, mediaControlPanel.buildLaunchAnimatorController(mediaControlPanel.mMediaViewHolder.player));
                    break;
                }
                break;
            case 1:
                MediaControlPanel mediaControlPanel2 = this.f$0;
                MediaDeviceData mediaDeviceData = (MediaDeviceData) this.f$1;
                if (!mediaControlPanel2.mFalsingManager.isFalseTap(2)) {
                    mediaControlPanel2.mLogger.logger.logWithInstanceId(MediaUiEvent.OPEN_OUTPUT_SWITCHER, mediaControlPanel2.mUid, mediaControlPanel2.mPackageName, mediaControlPanel2.mInstanceId);
                    PendingIntent pendingIntent2 = mediaDeviceData.intent;
                    if (pendingIntent2 == null) {
                        String str = mediaControlPanel2.mPackageName;
                        View view2 = mediaControlPanel2.mMediaViewHolder.seamlessButton;
                        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(mediaControlPanel2.mUid);
                        MediaSession.Token token = mediaControlPanel2.mToken;
                        MediaOutputDialogManager mediaOutputDialogManager = mediaControlPanel2.mMediaOutputDialogManager;
                        if (view2 != null) {
                            mediaOutputDialogManager.getClass();
                            DialogTransitionAnimator.Controller.Companion companion = DialogTransitionAnimator.Controller.Companion;
                            DialogCuj dialogCuj = new DialogCuj(58, "media_output");
                            companion.getClass();
                            viewDialogTransitionAnimatorControllerFromView = DialogTransitionAnimator.Controller.Companion.fromView(view2, dialogCuj);
                        } else {
                            viewDialogTransitionAnimatorControllerFromView = null;
                        }
                        mediaOutputDialogManager.createAndShow(str, true, viewDialogTransitionAnimatorControllerFromView, true, userHandleForUid, token);
                        break;
                    } else {
                        if (((KeyguardStateControllerImpl) mediaControlPanel2.mKeyguardStateController).mShowing) {
                            z = mediaControlPanel2.mActivityIntentHelper.wouldPendingShowOverLockscreen(((NotificationLockscreenUserManagerImpl) mediaControlPanel2.mLockscreenUserManager).mCurrentUserId, pendingIntent2);
                        }
                        if (!pendingIntent2.isActivity()) {
                            Log.w("MediaControlPanel", "Device pending intent is not an activity.");
                            break;
                        } else if (!z) {
                            mediaControlPanel2.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent2);
                            break;
                        } else {
                            try {
                                BroadcastOptions broadcastOptionsMakeBasic = BroadcastOptions.makeBasic();
                                broadcastOptionsMakeBasic.setInteractive(true);
                                broadcastOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
                                pendingIntent2.send(broadcastOptionsMakeBasic.toBundle());
                                break;
                            } catch (PendingIntent.CanceledException unused) {
                                Log.e("MediaControlPanel", "Device pending intent was canceled");
                                return;
                            }
                        }
                    }
                }
                break;
            default:
                MediaControlPanel mediaControlPanel3 = this.f$0;
                MediaControlPanel$$ExternalSyntheticLambda7 mediaControlPanel$$ExternalSyntheticLambda7 = (MediaControlPanel$$ExternalSyntheticLambda7) this.f$1;
                if (!mediaControlPanel3.mFalsingManager.isFalseTap(1)) {
                    mediaControlPanel3.mLogger.logger.logWithInstanceId(MediaUiEvent.DISMISS_LONG_PRESS, mediaControlPanel3.mUid, mediaControlPanel3.mPackageName, mediaControlPanel3.mInstanceId);
                    mediaControlPanel$$ExternalSyntheticLambda7.run();
                    break;
                }
                break;
        }
    }

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda6(MediaControlPanel mediaControlPanel, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaControlPanel;
        this.f$1 = obj;
    }
}
