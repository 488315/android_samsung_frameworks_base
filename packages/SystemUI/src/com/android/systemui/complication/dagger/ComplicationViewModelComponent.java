package com.android.systemui.complication.dagger;

import com.android.systemui.complication.Complication;
import com.android.systemui.complication.ComplicationId;
import com.android.systemui.complication.ComplicationViewModelProvider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface ComplicationViewModelComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        ComplicationViewModelComponent create(Complication complication, ComplicationId complicationId);
    }

    ComplicationViewModelProvider getViewModelProvider();
}
