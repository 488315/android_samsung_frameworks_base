package com.android.systemui.complication.dagger;

import com.android.systemui.complication.Complication;
import com.android.systemui.complication.ComplicationId;

/* loaded from: classes2.dex */
public interface ComplicationViewModelComponent {

    public interface Factory {
        ComplicationViewModelComponent create(Complication complication, ComplicationId complicationId);
    }
}
