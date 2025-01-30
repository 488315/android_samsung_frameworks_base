package com.android.systemui.complication.dagger;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import com.android.systemui.complication.Complication;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.complication.ComplicationLayoutEngine;
import com.android.systemui.touch.TouchInsetManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ComplicationComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        ComplicationComponent create(LifecycleOwner lifecycleOwner, Complication.Host host, ViewModelStore viewModelStore, TouchInsetManager touchInsetManager);
    }

    ComplicationHostViewController getComplicationHostViewController();

    ComplicationLayoutEngine getVisibilityController();
}
