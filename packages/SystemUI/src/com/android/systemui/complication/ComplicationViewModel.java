package com.android.systemui.complication;

import androidx.lifecycle.ViewModel;
import com.android.systemui.complication.Complication;

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
