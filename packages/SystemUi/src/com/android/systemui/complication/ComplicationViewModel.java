package com.android.systemui.complication;

import androidx.lifecycle.ViewModel;
import com.android.systemui.complication.Complication;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComplicationViewModel extends ViewModel {
    public final ComplicationId mId;

    public ComplicationViewModel(Complication complication, ComplicationId complicationId, Complication.Host host) {
        this.mId = complicationId;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mId);
        sb.append("=");
        throw null;
    }
}
