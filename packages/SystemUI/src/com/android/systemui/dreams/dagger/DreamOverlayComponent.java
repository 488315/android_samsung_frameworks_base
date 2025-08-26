package com.android.systemui.dreams.dagger;

import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.touch.TouchInsetManager;

/* loaded from: classes2.dex */
public interface DreamOverlayComponent {

    public interface Factory {
        DreamOverlayComponent create(LifecycleOwner lifecycleOwner, ComplicationHostViewController complicationHostViewController, TouchInsetManager touchInsetManager);
    }
}
