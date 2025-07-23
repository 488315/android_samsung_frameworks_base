package com.android.systemui.complication;

import com.android.systemui.complication.ComplicationId;
import com.android.systemui.complication.dagger.ComplicationViewModelComponent;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ComplicationViewModelTransformer {
    public final ComplicationId.Factory mComplicationIdFactory = new ComplicationId.Factory();
    public final HashMap mComplicationIdMapping = new HashMap();
    public final ComplicationViewModelComponent.Factory mViewModelComponentFactory;

    public ComplicationViewModelTransformer(ComplicationViewModelComponent.Factory factory) {
        this.mViewModelComponentFactory = factory;
    }
}
