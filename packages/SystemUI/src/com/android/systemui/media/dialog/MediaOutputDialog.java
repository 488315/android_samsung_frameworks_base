package com.android.systemui.media.dialog;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.os.Bundle;
import androidx.core.graphics.drawable.IconCompat;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.flags.Flags;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastSender;

public final class MediaOutputDialog extends MediaOutputBaseDialog {
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final UiEventLogger mUiEventLogger;

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

    public MediaOutputDialog(Context context, boolean z, BroadcastSender broadcastSender, MediaOutputController mediaOutputController, DialogTransitionAnimator dialogTransitionAnimator, UiEventLogger uiEventLogger, boolean z2) {
        super(context, broadcastSender, mediaOutputController, z2);
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
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
    public final void getBroadcastIconVisibility() {
        Flags.legacyLeAudioSharing();
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
        MediaMetadata metadata;
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        MediaController mediaController = mediaOutputController.mMediaController;
        return (mediaController == null || (metadata = mediaController.getMetadata()) == null) ? mediaOutputController.mContext.getText(R.string.controls_media_title) : metadata.getDescription().getTitle();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final CharSequence getStopButtonText() {
        Flags.legacyLeAudioSharing();
        return ((MediaOutputBaseDialog) this).mContext.getText(R.string.media_output_dialog_button_stop_casting);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final int getStopButtonVisibility() {
        boolean isActiveRemoteDevice = this.mMediaOutputController.mLocalMediaManager.getCurrentConnectedDevice() != null ? MediaOutputController.isActiveRemoteDevice(this.mMediaOutputController.mLocalMediaManager.getCurrentConnectedDevice()) : false;
        Flags.legacyLeAudioSharing();
        return !isActiveRemoteDevice ? 8 : 0;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void isBroadcastSupported() {
        Flags.legacyLeAudioSharing();
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

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void onStopButtonClick() {
        Flags.legacyLeAudioSharing();
        this.mMediaOutputController.releaseSession();
        this.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
        dismiss();
    }
}
