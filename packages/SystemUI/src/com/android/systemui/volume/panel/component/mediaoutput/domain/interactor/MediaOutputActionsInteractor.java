package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import com.android.systemui.media.dialog.MediaOutputDialogManager;

public final class MediaOutputActionsInteractor {
    public final MediaOutputDialogManager mediaOutputDialogManager;

    public MediaOutputActionsInteractor(MediaOutputDialogManager mediaOutputDialogManager) {
        this.mediaOutputDialogManager = mediaOutputDialogManager;
    }
}
