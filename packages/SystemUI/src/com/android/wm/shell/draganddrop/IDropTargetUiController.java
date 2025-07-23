package com.android.wm.shell.draganddrop;

import android.media.AudioManager;
import android.util.secutil.Slog;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface IDropTargetUiController {
    static void performDragStartedHapticAndSound(DragAndDropController.PerDisplay perDisplay) {
        DropTargetLayout dropTargetLayout = (DropTargetLayout) perDisplay.dragLayout;
        dropTargetLayout.getClass();
        dropTargetLayout.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
        AudioManager audioManager = (AudioManager) dropTargetLayout.getContext().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        if (audioManager == null) {
            Slog.w("DropTargetLayout", "Couldn't get audio manager");
        } else {
            audioManager.playSoundEffect(106);
        }
    }

    boolean onDrag(DragEvent dragEvent, int i, DragAndDropController.PerDisplay perDisplay);
}
