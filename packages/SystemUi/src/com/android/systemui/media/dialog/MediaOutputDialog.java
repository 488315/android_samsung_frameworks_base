package com.android.systemui.media.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.os.Bundle;
import android.util.FeatureFlagUtils;
import android.util.Log;
import androidx.core.graphics.drawable.IconCompat;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.systemui.R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.dialog.MediaOutputController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaOutputDialog extends MediaOutputBaseDialog {
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final UiEventLogger mUiEventLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum MediaOutputEvent implements UiEventLogger.UiEventEnum {
        MEDIA_OUTPUT_DIALOG_SHOW(655);

        private final int mId;

        MediaOutputEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public MediaOutputDialog(Context context, boolean z, BroadcastSender broadcastSender, MediaOutputController mediaOutputController, DialogLaunchAnimator dialogLaunchAnimator, UiEventLogger uiEventLogger) {
        super(context, broadcastSender, mediaOutputController);
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mUiEventLogger = uiEventLogger;
        this.mAdapter = new MediaOutputAdapter(this.mMediaOutputController);
        if (z) {
            return;
        }
        getWindow().setType(2038);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final IconCompat getAppSourceIcon() {
        return this.mMediaOutputController.getNotificationSmallIcon();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final int getBroadcastIconVisibility() {
        if (isBroadcastSupported()) {
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
            if (localBluetoothLeBroadcast == null ? false : localBluetoothLeBroadcast.isEnabled()) {
                return 0;
            }
        }
        return 8;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final IconCompat getHeaderIcon() {
        return this.mMediaOutputController.getHeaderIcon();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final int getHeaderIconSize() {
        return ((MediaOutputBaseDialog) this).mContext.getResources().getDimensionPixelSize(R.dimen.media_output_dialog_header_album_icon_size);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final CharSequence getHeaderSubtitle() {
        MediaMetadata metadata;
        MediaController mediaController = this.mMediaOutputController.mMediaController;
        if (mediaController == null || (metadata = mediaController.getMetadata()) == null) {
            return null;
        }
        return metadata.getDescription().getSubtitle();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final CharSequence getHeaderText() {
        return this.mMediaOutputController.getHeaderTitle();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final CharSequence getStopButtonText() {
        int i;
        if (isBroadcastSupported() && this.mMediaOutputController.isPlaying()) {
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
            if (!(localBluetoothLeBroadcast == null ? false : localBluetoothLeBroadcast.isEnabled())) {
                i = R.string.media_output_broadcast;
                return ((MediaOutputBaseDialog) this).mContext.getText(i);
            }
        }
        i = R.string.media_output_dialog_button_stop_casting;
        return ((MediaOutputBaseDialog) this).mContext.getText(i);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final int getStopButtonVisibility() {
        return ((this.mMediaOutputController.getCurrentConnectedMediaDevice() != null ? MediaOutputController.isActiveRemoteDevice(this.mMediaOutputController.getCurrentConnectedMediaDevice()) : false) || (isBroadcastSupported() && this.mMediaOutputController.isPlaying())) ? 0 : 8;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final boolean isBroadcastSupported() {
        return (this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast != null) && (FeatureFlagUtils.isEnabled(((MediaOutputBaseDialog) this).mContext, "settings_need_connected_ble_device_for_broadcast") ? this.mMediaOutputController.getCurrentConnectedMediaDevice() != null ? this.mMediaOutputController.getCurrentConnectedMediaDevice().isBLEDevice() : false : true);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void onBroadcastIconClick() {
        startLeBroadcastDialog();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog, com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUiEventLogger.log(MediaOutputEvent.MEDIA_OUTPUT_DIALOG_SHOW);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0053 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0054  */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda4] */
    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onStopButtonClick() {
        boolean z;
        if (!isBroadcastSupported() || !this.mMediaOutputController.isPlaying()) {
            this.mMediaOutputController.releaseSession();
            this.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
            dismiss();
            return;
        }
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null ? false : localBluetoothLeBroadcast.isEnabled()) {
            this.mStopButton.setEnabled(false);
            if (this.mMediaOutputController.stopBluetoothLeBroadcast()) {
                return;
            }
            this.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda1(this, 3));
            return;
        }
        SharedPreferences sharedPreferences = ((MediaOutputBaseDialog) this).mContext.getSharedPreferences("MediaOutputDialog", 0);
        if (sharedPreferences != null) {
            z = true;
            if (sharedPreferences.getBoolean("PrefIsLeBroadcastFirstLaunch", true)) {
                Log.d("MediaOutputDialog", "PREF_IS_LE_BROADCAST_FIRST_LAUNCH: true");
                this.mMediaOutputController.launchLeBroadcastNotifyDialog(MediaOutputController.BroadcastNotifyDialog.ACTION_FIRST_LAUNCH, new DialogInterface.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda4
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        MediaOutputBaseDialog mediaOutputBaseDialog = MediaOutputBaseDialog.this;
                        mediaOutputBaseDialog.mStopButton.setText(R.string.media_output_broadcast_starting);
                        mediaOutputBaseDialog.mStopButton.setEnabled(false);
                        if (mediaOutputBaseDialog.mMediaOutputController.startBluetoothLeBroadcast()) {
                            return;
                        }
                        mediaOutputBaseDialog.handleLeBroadcastStartFailed();
                    }
                });
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean("PrefIsLeBroadcastFirstLaunch", false);
                edit.apply();
                if (z) {
                    this.mStopButton.setText(R.string.media_output_broadcast_starting);
                    this.mStopButton.setEnabled(false);
                    if (this.mMediaOutputController.startBluetoothLeBroadcast()) {
                        return;
                    }
                    handleLeBroadcastStartFailed();
                    return;
                }
                return;
            }
        }
        z = false;
        if (z) {
        }
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void getHeaderIconRes() {
    }
}
