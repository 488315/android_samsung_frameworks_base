package com.android.p038wm.shell.draganddrop;

import android.media.AudioManager;
import android.util.secutil.Slog;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import com.android.p038wm.shell.draganddrop.DragAndDropController;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
