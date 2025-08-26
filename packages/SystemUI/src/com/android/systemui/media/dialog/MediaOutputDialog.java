package com.android.systemui.media.dialog;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.os.Bundle;
import androidx.core.graphics.drawable.IconCompat;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class MediaOutputDialog extends MediaOutputBaseDialog {
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

    public MediaOutputDialog(Context context, boolean z, BroadcastSender broadcastSender, MediaSwitchingController mediaSwitchingController, DialogTransitionAnimator dialogTransitionAnimator, UiEventLogger uiEventLogger, Executor executor, Executor executor2, boolean z2) {
        super(context, broadcastSender, mediaSwitchingController, z2);
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mUiEventLogger = uiEventLogger;
        this.mAdapter = new MediaOutputAdapterLegacy(this.mMediaSwitchingController, executor, executor2);
        if (z) {
            return;
        }
        getWindow().setType(2038);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final IconCompat getAppSourceIcon() {
        return this.mMediaSwitchingController.getNotificationSmallIcon();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final IconCompat getHeaderIcon() {
        return this.mMediaSwitchingController.getHeaderIcon();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final CharSequence getHeaderSubtitle() {
        MediaMetadata metadata;
        MediaController mediaController = this.mMediaSwitchingController.mMediaController;
        if (mediaController == null || (metadata = mediaController.getMetadata()) == null) {
            return null;
        }
        return metadata.getDescription().getSubtitle();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final CharSequence getHeaderText() {
        MediaMetadata metadata;
        MediaSwitchingController mediaSwitchingController = this.mMediaSwitchingController;
        MediaController mediaController = mediaSwitchingController.mMediaController;
        return (mediaController == null || (metadata = mediaController.getMetadata()) == null) ? mediaSwitchingController.mContext.getText(R.string.controls_media_title) : metadata.getDescription().getTitle();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final CharSequence getStopButtonText() {
        return ((MediaOutputBaseDialog) this).mContext.getText(R.string.media_output_dialog_button_stop_casting);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final int getStopButtonVisibility() {
        return !(this.mMediaSwitchingController.mLocalMediaManager.getCurrentConnectedDevice() != null ? MediaSwitchingController.isActiveRemoteDevice(this.mMediaSwitchingController.mLocalMediaManager.getCurrentConnectedDevice()) : false) ? 8 : 0;
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
        this.mMediaSwitchingController.releaseSession();
        this.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
        dismiss();
    }
}
