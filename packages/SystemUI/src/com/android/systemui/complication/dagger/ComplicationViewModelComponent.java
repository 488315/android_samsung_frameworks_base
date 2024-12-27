package com.android.systemui.complication.dagger;

import com.android.systemui.complication.Complication;
import com.android.systemui.complication.ComplicationId;
import com.android.systemui.complication.ComplicationViewModelProvider;

public interface ComplicationViewModelComponent {

    public interface Factory {
        ComplicationViewModelComponent create(Complication complication, ComplicationId complicationId);
    }

    ComplicationViewModelProvider getViewModelProvider();
}
