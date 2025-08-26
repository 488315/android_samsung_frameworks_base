package com.android.wm.shell.draganddrop;

import android.media.AudioManager;
import android.util.secutil.Slog;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.sec.ims.presence.ServiceTuple;

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
