package com.android.systemui.complication;

import androidx.lifecycle.LiveData;
import com.android.systemui.dreams.DreamOverlayStateController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ComplicationCollectionLiveData extends LiveData {
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final DreamOverlayStateController.Callback mStateControllerCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.complication.ComplicationCollectionLiveData.1
        @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
        public final void onAvailableComplicationTypesChanged() {
            ComplicationCollectionLiveData complicationCollectionLiveData = ComplicationCollectionLiveData.this;
            complicationCollectionLiveData.setValue(complicationCollectionLiveData.mDreamOverlayStateController.getComplications());
        }

        @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
        public final void onComplicationsChanged() {
            ComplicationCollectionLiveData complicationCollectionLiveData = ComplicationCollectionLiveData.this;
            complicationCollectionLiveData.setValue(complicationCollectionLiveData.mDreamOverlayStateController.getComplications());
        }
    };

    public ComplicationCollectionLiveData(DreamOverlayStateController dreamOverlayStateController) {
        this.mDreamOverlayStateController = dreamOverlayStateController;
    }

    @Override // androidx.lifecycle.LiveData
    public final void onActive() {
        DreamOverlayStateController.Callback callback = this.mStateControllerCallback;
        DreamOverlayStateController dreamOverlayStateController = this.mDreamOverlayStateController;
        dreamOverlayStateController.addCallback(callback);
        setValue(dreamOverlayStateController.getComplications());
    }

    @Override // androidx.lifecycle.LiveData
    public final void onInactive() {
        this.mDreamOverlayStateController.removeCallback(this.mStateControllerCallback);
    }
}
