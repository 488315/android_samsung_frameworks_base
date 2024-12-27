package com.android.systemui.complication;

import com.android.systemui.complication.ComplicationId;
import com.android.systemui.complication.dagger.ComplicationViewModelComponent;
import java.util.HashMap;

public final class ComplicationViewModelTransformer {
    public final ComplicationId.Factory mComplicationIdFactory = new ComplicationId.Factory();
    public final HashMap mComplicationIdMapping = new HashMap();
    public final ComplicationViewModelComponent.Factory mViewModelComponentFactory;

    public ComplicationViewModelTransformer(ComplicationViewModelComponent.Factory factory) {
        this.mViewModelComponentFactory = factory;
    }
}
