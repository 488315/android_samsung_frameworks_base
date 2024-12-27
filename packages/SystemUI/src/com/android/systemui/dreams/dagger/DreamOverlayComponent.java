package com.android.systemui.dreams.dagger;

import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.dreams.DreamOverlayContainerViewController;
import com.android.systemui.dreams.touch.CommunalTouchHandler;
import com.android.systemui.touch.TouchInsetManager;

public interface DreamOverlayComponent {

    public interface Factory {
        DreamOverlayComponent create(LifecycleOwner lifecycleOwner, ComplicationHostViewController complicationHostViewController, TouchInsetManager touchInsetManager);
    }

    CommunalTouchHandler getCommunalTouchHandler();

    DreamOverlayContainerViewController getDreamOverlayContainerViewController();
}
