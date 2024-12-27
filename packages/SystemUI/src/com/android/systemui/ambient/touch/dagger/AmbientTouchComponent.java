package com.android.systemui.ambient.touch.dagger;

import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.ambient.touch.TouchMonitor;
import java.util.Set;

public interface AmbientTouchComponent {

    public interface Factory {
        AmbientTouchComponent create(LifecycleOwner lifecycleOwner, Set set);
    }

    TouchMonitor getTouchMonitor();
}
