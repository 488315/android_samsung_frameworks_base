package com.android.systemui.mediaprojection.appselector;

import android.os.UserHandle;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Set;

public interface MediaProjectionAppSelectorComponent {

    public interface Factory {
        MediaProjectionAppSelectorComponent create(UserHandle userHandle, int i, String str, MediaProjectionAppSelectorView mediaProjectionAppSelectorView, MediaProjectionAppSelectorResultHandler mediaProjectionAppSelectorResultHandler, boolean z);
    }

    ConfigurationController getConfigurationController();

    MediaProjectionAppSelectorController getController();

    MediaProjectionBlockerEmptyStateProvider getEmptyStateProvider();

    UserHandle getHostUserHandle();

    Set getLifecycleObservers();

    UserHandle getPersonalProfileUserHandle();

    MediaProjectionRecentsViewController getRecentsViewController();
}
