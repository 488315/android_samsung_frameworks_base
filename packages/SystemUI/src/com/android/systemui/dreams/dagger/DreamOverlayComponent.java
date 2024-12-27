package com.android.systemui.dreams.dagger;

import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.dreams.DreamOverlayContainerViewController;
import com.android.systemui.dreams.touch.CommunalTouchHandler;
import com.android.systemui.touch.TouchInsetManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface DreamOverlayComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        DreamOverlayComponent create(LifecycleOwner lifecycleOwner, ComplicationHostViewController complicationHostViewController, TouchInsetManager touchInsetManager);
    }

    CommunalTouchHandler getCommunalTouchHandler();

    DreamOverlayContainerViewController getDreamOverlayContainerViewController();
}
