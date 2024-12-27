package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.buds.NoiseControl;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class NoiseControlBoxViewModel extends BaseViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ModelProvider modelProvider;
    public final MutableLiveData showActiveNoiseCancelingBarOnly;
    public final MutableLiveData showNoiseEffectBoxView;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public NoiseControlBoxViewModel(Context context, ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager) {
        this.modelProvider = modelProvider;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        Boolean bool = Boolean.FALSE;
        this.showActiveNoiseCancelingBarOnly = new MutableLiveData(bool);
        this.showNoiseEffectBoxView = new MutableLiveData(bool);
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        boolean z;
        boolean z2;
        Set noiseControlsList = this.modelProvider.budsModel.getNoiseControlsList();
        boolean z3 = false;
        if (noiseControlsList != null) {
            Iterator it = noiseControlsList.iterator();
            boolean z4 = false;
            z2 = false;
            while (it.hasNext()) {
                String name = ((NoiseControl) it.next()).getName();
                BluetoothDeviceManager bluetoothDeviceManager = this.bluetoothDeviceManager;
                if (Intrinsics.areEqual(name, bluetoothDeviceManager.getAmbientSoundTitle())) {
                    z3 = true;
                } else if (Intrinsics.areEqual(name, bluetoothDeviceManager.getAdaptiveTitle())) {
                    z2 = true;
                } else if (Intrinsics.areEqual(name, bluetoothDeviceManager.getActiveNoiseControlTitle())) {
                    z4 = true;
                }
            }
            z = z3;
            z3 = z4;
        } else {
            z = false;
            z2 = false;
        }
        MutableLiveData mutableLiveData = this.showNoiseEffectBoxView;
        MutableLiveData mutableLiveData2 = this.showActiveNoiseCancelingBarOnly;
        if (!z3 || z || z2) {
            mutableLiveData2.setValue(Boolean.FALSE);
            mutableLiveData.setValue(Boolean.TRUE);
        } else {
            mutableLiveData2.setValue(Boolean.TRUE);
            mutableLiveData.setValue(Boolean.FALSE);
        }
    }

    public final String toString() {
        return "[showActiveNoiseCancelingBarOnly=" + this.showActiveNoiseCancelingBarOnly.getValue() + ", showNoiseEffectBoxView=" + this.showNoiseEffectBoxView.getValue() + ", ]";
    }
}
