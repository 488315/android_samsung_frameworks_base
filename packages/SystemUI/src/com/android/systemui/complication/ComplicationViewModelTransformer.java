package com.android.systemui.complication;

import com.android.systemui.complication.ComplicationId;
import com.android.systemui.complication.dagger.ComplicationViewModelComponent;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ComplicationViewModelTransformer {
    public final ComplicationId.Factory mComplicationIdFactory = new ComplicationId.Factory();
    public final HashMap mComplicationIdMapping = new HashMap();
    public final ComplicationViewModelComponent.Factory mViewModelComponentFactory;

    public ComplicationViewModelTransformer(ComplicationViewModelComponent.Factory factory) {
        this.mViewModelComponentFactory = factory;
    }
}
