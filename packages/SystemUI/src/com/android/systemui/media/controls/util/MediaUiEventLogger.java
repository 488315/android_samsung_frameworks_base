package com.android.systemui.media.controls.util;

import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaUiEventLogger {
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
    public final UiEventLogger logger;

    public MediaUiEventLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }

    public final void logActiveMediaAdded(int i, int i2, InstanceId instanceId, String str) {
        MediaUiEvent mediaUiEvent;
        if (i2 == 0) {
            mediaUiEvent = MediaUiEvent.LOCAL_MEDIA_ADDED;
        } else if (i2 == 1) {
            mediaUiEvent = MediaUiEvent.CAST_MEDIA_ADDED;
        } else {
            if (i2 != 2) {
                throw new IllegalArgumentException("Unknown playback location");
            }
            mediaUiEvent = MediaUiEvent.REMOTE_MEDIA_ADDED;
        }
        this.logger.logWithInstanceId(mediaUiEvent, i, str, instanceId);
    }

    public final void logMediaRemoved(int i, String str, InstanceId instanceId) {
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_REMOVED, i, str, instanceId);
    }

    public final void logPlaybackLocationChange(int i, int i2, InstanceId instanceId, String str) {
        MediaUiEvent mediaUiEvent;
        if (i2 == 0) {
            mediaUiEvent = MediaUiEvent.TRANSFER_TO_LOCAL;
        } else if (i2 == 1) {
            mediaUiEvent = MediaUiEvent.TRANSFER_TO_CAST;
        } else {
            if (i2 != 2) {
                throw new IllegalArgumentException("Unknown playback location");
            }
            mediaUiEvent = MediaUiEvent.TRANSFER_TO_REMOTE;
        }
        this.logger.logWithInstanceId(mediaUiEvent, i, str, instanceId);
    }

    public final void logTapAction(int i, int i2, InstanceId instanceId, String str) {
        this.logger.logWithInstanceId(i == R.id.actionPlayPause ? MediaUiEvent.TAP_ACTION_PLAY_PAUSE : i == R.id.actionPrev ? MediaUiEvent.TAP_ACTION_PREV : i == R.id.actionNext ? MediaUiEvent.TAP_ACTION_NEXT : MediaUiEvent.TAP_ACTION_OTHER, i2, str, instanceId);
    }
}
