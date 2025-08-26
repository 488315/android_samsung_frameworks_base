package com.android.systemui.ambient.touch.dagger;

import androidx.lifecycle.LifecycleOwner;
import java.util.Set;

/* loaded from: classes.dex */
public interface AmbientTouchComponent {

    public interface Factory {
        AmbientTouchComponent create(LifecycleOwner lifecycleOwner, Set set, String str);
    }
}
