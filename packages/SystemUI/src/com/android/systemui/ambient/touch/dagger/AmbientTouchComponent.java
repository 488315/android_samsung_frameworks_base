package com.android.systemui.ambient.touch.dagger;

import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.ambient.touch.TouchMonitor;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface AmbientTouchComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        AmbientTouchComponent create(LifecycleOwner lifecycleOwner, Set set);
    }

    TouchMonitor getTouchMonitor();
}
