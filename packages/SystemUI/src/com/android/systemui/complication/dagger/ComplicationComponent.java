package com.android.systemui.complication.dagger;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import com.android.systemui.complication.Complication;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.complication.ComplicationLayoutEngine;
import com.android.systemui.touch.TouchInsetManager;

public interface ComplicationComponent {

    public interface Factory {
        ComplicationComponent create(LifecycleOwner lifecycleOwner, Complication.Host host, ViewModelStore viewModelStore, TouchInsetManager touchInsetManager);
    }

    ComplicationHostViewController getComplicationHostViewController();

    ComplicationLayoutEngine getVisibilityController();
}
