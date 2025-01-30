package com.android.systemui.complication.dagger;

import com.android.systemui.complication.Complication;
import com.android.systemui.complication.ComplicationId;
import com.android.systemui.complication.ComplicationViewModelProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ComplicationViewModelComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        ComplicationViewModelComponent create(Complication complication, ComplicationId complicationId);
    }

    ComplicationViewModelProvider getViewModelProvider();
}
