package com.android.systemui.mediaprojection.appselector;

import android.os.UserHandle;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface MediaProjectionAppSelectorComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
