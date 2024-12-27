package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.buds.BudsModel;
import com.android.systemui.audio.soundcraft.model.buds.NoiseControl;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class NoiseControlEffectBoxViewModel extends BaseViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ModelProvider modelProvider;
    public final MutableLiveData showActiveNoiseCanceling;
    public final MutableLiveData showActiveNoiseCancelingSeekBar;
    public final MutableLiveData showAdaptive;
    public final MutableLiveData showAmbientSound;
    public final MutableLiveData showAmbientVolumeSeekBar;
    public final MutableLiveData showNoiseControlOff;

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

    public NoiseControlEffectBoxViewModel(Context context, ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager) {
        this.modelProvider = modelProvider;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        Boolean bool = Boolean.FALSE;
        this.showNoiseControlOff = new MutableLiveData(bool);
        this.showAmbientSound = new MutableLiveData(bool);
        this.showAdaptive = new MutableLiveData(bool);
        this.showActiveNoiseCanceling = new MutableLiveData(bool);
        this.showActiveNoiseCancelingSeekBar = new MutableLiveData(bool);
        this.showAmbientVolumeSeekBar = new MutableLiveData(bool);
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        boolean z;
        boolean z2;
        ModelProvider modelProvider = this.modelProvider;
        BudsModel budsModel = modelProvider.budsModel;
        Log.d("SoundCraft.NoiseControlEffectBoxViewModel", "notifyChange : modelProvider.budsInfo.noiseControlsList=" + budsModel.getNoiseControlsList() + ", noiseCancelingLevel: " + budsModel.getNoiseCancelingLevel() + ", ambientSoundVolume: " + budsModel.getAmbientSoundVolume());
        Set<NoiseControl> noiseControlsList = budsModel.getNoiseControlsList();
        MutableLiveData mutableLiveData = this.showAmbientSound;
        MutableLiveData mutableLiveData2 = this.showActiveNoiseCanceling;
        boolean z3 = false;
        if (noiseControlsList != null) {
            z = false;
            z2 = false;
            for (NoiseControl noiseControl : noiseControlsList) {
                String name = noiseControl.getName();
                BluetoothDeviceManager bluetoothDeviceManager = this.bluetoothDeviceManager;
                if (Intrinsics.areEqual(name, bluetoothDeviceManager.getNoiseControlOffTitle())) {
                    this.showNoiseControlOff.setValue(Boolean.TRUE);
                } else if (Intrinsics.areEqual(name, bluetoothDeviceManager.getActiveNoiseControlTitle())) {
                    mutableLiveData2.setValue(Boolean.TRUE);
                    z = noiseControl.getState();
                } else if (Intrinsics.areEqual(name, bluetoothDeviceManager.getAmbientSoundTitle())) {
                    mutableLiveData.setValue(Boolean.TRUE);
                    z2 = noiseControl.getState();
                } else if (Intrinsics.areEqual(name, bluetoothDeviceManager.getAdaptiveTitle())) {
                    this.showAdaptive.setValue(Boolean.TRUE);
                }
            }
        } else {
            z = false;
            z2 = false;
        }
        MutableLiveData mutableLiveData3 = this.showActiveNoiseCancelingSeekBar;
        Boolean connectionState = modelProvider.budsModel.getConnectionState();
        Boolean bool = Boolean.TRUE;
        mutableLiveData3.setValue(Boolean.valueOf(Intrinsics.areEqual(connectionState, bool) && budsModel.getNoiseCancelingLevel() != null && Intrinsics.areEqual(mutableLiveData2.getValue(), bool) && z));
        MutableLiveData mutableLiveData4 = this.showAmbientVolumeSeekBar;
        if (Intrinsics.areEqual(modelProvider.budsModel.getConnectionState(), bool) && budsModel.getAmbientSoundVolume() != null && Intrinsics.areEqual(mutableLiveData.getValue(), bool) && z2 && Intrinsics.areEqual(modelProvider.budsModel.getHearingEnhancement(), Boolean.FALSE)) {
            z3 = true;
        }
        mutableLiveData4.setValue(Boolean.valueOf(z3));
    }

    public final String toString() {
        return "[showNoiseControlOff=" + this.showNoiseControlOff.getValue() + ", showAmbientSound=" + this.showAmbientSound.getValue() + ", showAdaptive=" + this.showAdaptive.getValue() + ", showActiveNoiseCanceling=" + this.showActiveNoiseCanceling.getValue() + ", showActiveNoiseCancelingSeekBar=" + this.showActiveNoiseCancelingSeekBar.getValue() + ", showAmbientVolumeSeekBar=" + this.showAmbientVolumeSeekBar.getValue() + "]";
    }
}
