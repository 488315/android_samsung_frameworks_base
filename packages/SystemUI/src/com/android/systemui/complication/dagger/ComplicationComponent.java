package com.android.systemui.complication.dagger;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import com.android.systemui.complication.Complication;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.complication.ComplicationLayoutEngine;
import com.android.systemui.touch.TouchInsetManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ComplicationComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        ComplicationComponent create(LifecycleOwner lifecycleOwner, Complication.Host host, ViewModelStore viewModelStore, TouchInsetManager touchInsetManager);
    }

    ComplicationHostViewController getComplicationHostViewController();

    ComplicationLayoutEngine getVisibilityController();
}
