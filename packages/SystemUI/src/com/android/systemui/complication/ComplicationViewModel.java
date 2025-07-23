package com.android.systemui.complication;

import androidx.lifecycle.ViewModel;
import com.android.systemui.complication.Complication;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ComplicationViewModel extends ViewModel {
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
