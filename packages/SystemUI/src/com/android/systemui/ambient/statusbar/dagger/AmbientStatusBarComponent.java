package com.android.systemui.ambient.statusbar.dagger;

import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarView;
import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface AmbientStatusBarComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        AmbientStatusBarComponent create(AmbientStatusBarView ambientStatusBarView);
    }

    AmbientStatusBarViewController getController();
}
