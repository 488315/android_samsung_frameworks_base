package com.android.systemui.ambient.statusbar.dagger;

import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarView;
import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController;

public interface AmbientStatusBarComponent {

    public interface Factory {
        AmbientStatusBarComponent create(AmbientStatusBarView ambientStatusBarView);
    }

    AmbientStatusBarViewController getController();
}
