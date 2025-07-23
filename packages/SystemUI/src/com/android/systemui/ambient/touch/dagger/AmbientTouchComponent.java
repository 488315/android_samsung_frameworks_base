package com.android.systemui.ambient.touch.dagger;

import androidx.lifecycle.LifecycleOwner;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface AmbientTouchComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        AmbientTouchComponent create(LifecycleOwner lifecycleOwner, Set set, String str);
    }
}
