package com.android.systemui.complication;

import com.android.systemui.complication.ComplicationId;
import com.android.systemui.complication.dagger.ComplicationViewModelComponent;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComplicationViewModelTransformer {
    public final ComplicationId.Factory mComplicationIdFactory = new ComplicationId.Factory();
    public final HashMap mComplicationIdMapping = new HashMap();
    public final ComplicationViewModelComponent.Factory mViewModelComponentFactory;

    public ComplicationViewModelTransformer(ComplicationViewModelComponent.Factory factory) {
        this.mViewModelComponentFactory = factory;
    }
}
