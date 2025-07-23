package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NoiseControlBoxViewModel extends BaseViewModel {
    public final ModelProvider modelProvider;
    public final MutableLiveData showNoiseEffectBoxView = new MutableLiveData(Boolean.FALSE);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public NoiseControlBoxViewModel(Context context, ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager) {
        this.modelProvider = modelProvider;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        this.showNoiseEffectBoxView.setValue(Boolean.TRUE);
    }

    public final String toString() {
        return "[showNoiseEffectBoxView=" + this.showNoiseEffectBoxView.getValue() + ", ]";
    }
}
