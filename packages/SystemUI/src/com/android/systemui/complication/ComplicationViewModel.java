package com.android.systemui.complication;

import androidx.lifecycle.ViewModel;
import com.android.systemui.complication.Complication;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ComplicationViewModel extends ViewModel {
    public final Complication mComplication;
    public final ComplicationId mId;

    public ComplicationViewModel(Complication complication, ComplicationId complicationId, Complication.Host host) {
        this.mComplication = complication;
        this.mId = complicationId;
    }

    public final String toString() {
        return this.mId + "=" + this.mComplication.toString();
    }
}
