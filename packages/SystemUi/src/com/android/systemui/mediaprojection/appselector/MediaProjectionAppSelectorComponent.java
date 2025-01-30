package com.android.systemui.mediaprojection.appselector;

import android.os.UserHandle;
import com.android.systemui.media.MediaProjectionAppSelectorActivity;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface MediaProjectionAppSelectorComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        MediaProjectionAppSelectorComponent create(MediaProjectionAppSelectorActivity mediaProjectionAppSelectorActivity, MediaProjectionAppSelectorView mediaProjectionAppSelectorView, MediaProjectionAppSelectorResultHandler mediaProjectionAppSelectorResultHandler);
    }

    ConfigurationController getConfigurationController();

    MediaProjectionAppSelectorController getController();

    MediaProjectionBlockerEmptyStateProvider getEmptyStateProvider();

    UserHandle getHostUserHandle();

    UserHandle getPersonalProfileUserHandle();

    MediaProjectionRecentsViewController getRecentsViewController();
}
