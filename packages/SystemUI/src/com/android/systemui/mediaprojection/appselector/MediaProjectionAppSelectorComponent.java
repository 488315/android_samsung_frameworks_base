package com.android.systemui.mediaprojection.appselector;

import android.os.UserHandle;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface MediaProjectionAppSelectorComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
