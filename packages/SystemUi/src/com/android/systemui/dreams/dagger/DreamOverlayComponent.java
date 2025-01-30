package com.android.systemui.dreams.dagger;

import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.dreams.DreamOverlayContainerViewController;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.touch.TouchInsetManager;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface DreamOverlayComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        DreamOverlayComponent create(LifecycleOwner lifecycleOwner, ComplicationHostViewController complicationHostViewController, TouchInsetManager touchInsetManager, Set set);
    }

    DreamOverlayContainerViewController getDreamOverlayContainerViewController();

    DreamOverlayTouchMonitor getDreamOverlayTouchMonitor();
}
