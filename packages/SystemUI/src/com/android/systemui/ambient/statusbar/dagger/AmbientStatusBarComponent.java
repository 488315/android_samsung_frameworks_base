package com.android.systemui.ambient.statusbar.dagger;

import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarView;

/* loaded from: classes.dex */
public interface AmbientStatusBarComponent {

    public interface Factory {
        AmbientStatusBarComponent create(AmbientStatusBarView ambientStatusBarView);
    }
}
