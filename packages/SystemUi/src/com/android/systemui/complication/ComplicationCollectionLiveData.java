package com.android.systemui.complication;

import androidx.lifecycle.LiveData;
import com.android.systemui.dreams.DreamOverlayStateController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComplicationCollectionLiveData extends LiveData {
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final C11641 mStateControllerCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.complication.ComplicationCollectionLiveData.1
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

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.complication.ComplicationCollectionLiveData$1] */
    public ComplicationCollectionLiveData(DreamOverlayStateController dreamOverlayStateController) {
        this.mDreamOverlayStateController = dreamOverlayStateController;
    }

    @Override // androidx.lifecycle.LiveData
    public final void onActive() {
        DreamOverlayStateController dreamOverlayStateController = this.mDreamOverlayStateController;
        dreamOverlayStateController.addCallback((DreamOverlayStateController.Callback) this.mStateControllerCallback);
        setValue(dreamOverlayStateController.getComplications());
    }

    @Override // androidx.lifecycle.LiveData
    public final void onInactive() {
        this.mDreamOverlayStateController.removeCallback((DreamOverlayStateController.Callback) this.mStateControllerCallback);
    }
}
