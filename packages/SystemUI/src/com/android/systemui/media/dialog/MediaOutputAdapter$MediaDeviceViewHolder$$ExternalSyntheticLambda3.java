package com.android.systemui.media.dialog;

import android.app.KeyguardManager;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.view.View;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1;
import com.android.systemui.media.dialog.MediaOutputAdapter;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.concurrent.CopyOnWriteArrayList;

public final /* synthetic */ class MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        KeyguardManager keyguardManager;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                MediaOutputAdapter.MediaDeviceViewHolder mediaDeviceViewHolder = (MediaOutputAdapter.MediaDeviceViewHolder) obj;
                MediaOutputController mediaOutputController = MediaOutputAdapter.this.mController;
                if (mediaOutputController.mAudioManager.getMutingExpectedDevice() != null) {
                    try {
                        synchronized (mediaOutputController.mMediaDevicesLock) {
                            ((CopyOnWriteArrayList) mediaOutputController.mMediaItemList).removeIf(new MediaOutputController$$ExternalSyntheticLambda0());
                        }
                        AudioManager audioManager = mediaOutputController.mAudioManager;
                        audioManager.cancelMuteAwaitConnection(audioManager.getMutingExpectedDevice());
                    } catch (Exception unused) {
                        Log.d("MediaOutputController", "Unable to cancel mute await connection");
                    }
                }
                MediaOutputAdapter.this.notifyDataSetChanged();
                return;
            case 1:
                ((MediaOutputAdapter.MediaDeviceViewHolder) obj).mCheckBox.performClick();
                return;
            case 2:
                ((MediaOutputAdapter.MediaDeviceViewHolder) obj).mEndClickIcon.performClick();
                return;
            default:
                MediaOutputController mediaOutputController2 = (MediaOutputController) obj;
                DialogTransitionAnimator dialogTransitionAnimator = mediaOutputController2.mDialogTransitionAnimator;
                dialogTransitionAnimator.getClass();
                DialogTransitionAnimator$createActivityTransitionController$1 createActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view);
                if (createActivityTransitionController$default == null || ((keyguardManager = mediaOutputController2.mKeyGuardManager) != null && keyguardManager.isKeyguardLocked())) {
                    ((MediaOutputBaseDialog) mediaOutputController2.mCallback).mBroadcastSender.closeSystemDialogs();
                }
                Intent addFlags = new Intent("android.settings.BLUETOOTH_SETTINGS").addFlags(335544320);
                Intent intent = new Intent("android.settings.SETTINGS_EMBED_DEEP_LINK_ACTIVITY");
                if (intent.resolveActivity(mediaOutputController2.mContext.getPackageManager()) == null) {
                    mediaOutputController2.mActivityStarter.startActivity(addFlags, true, (ActivityTransitionAnimator.Controller) createActivityTransitionController$default);
                    return;
                }
                Log.d("MediaOutputController", "Device support split mode, launch page with deep link");
                intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                intent.putExtra("android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI", addFlags.toUri(1));
                intent.putExtra("android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY", "top_level_connected_devices");
                mediaOutputController2.mActivityStarter.startActivity(intent, true, (ActivityTransitionAnimator.Controller) createActivityTransitionController$default);
                return;
        }
    }
}
