package com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.p016qs.bar.soundcraft.model.BudsInfo;
import com.android.systemui.p016qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.p016qs.bar.soundcraft.model.NoiseControl;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseViewModel;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoiseControlEffectBoxViewModel extends BaseViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ModelProvider modelProvider;
    public final MutableLiveData showActiveNoiseCanceling;
    public final MutableLiveData showActiveNoiseCancelingSeekBar;
    public final MutableLiveData showAdaptive;
    public final MutableLiveData showAmbientSound;
    public final MutableLiveData showNoiseControlOff;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    }

    public final MutableLiveData getShowActiveNoiseCanceling() {
        return this.showActiveNoiseCanceling;
    }

    public final MutableLiveData getShowActiveNoiseCancelingSeekBar() {
        return this.showActiveNoiseCancelingSeekBar;
    }

    public final MutableLiveData getShowAdaptive() {
        return this.showAdaptive;
    }

    public final MutableLiveData getShowAmbientSound() {
        return this.showAmbientSound;
    }

    public final MutableLiveData getShowNoiseControlOff() {
        return this.showNoiseControlOff;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseViewModel
    public final void notifyChange() {
        ModelProvider modelProvider = this.modelProvider;
        Log.d("SoundCraft.NoiseControlEffectBoxViewModel", "notifyChange : modelProvider.budsInfo.noiseControlsList=" + modelProvider.budsInfo.getNoiseControlsList());
        BudsInfo budsInfo = modelProvider.budsInfo;
        Set noiseControlsList = budsInfo.getNoiseControlsList();
        MutableLiveData mutableLiveData = this.showActiveNoiseCanceling;
        if (noiseControlsList != null) {
            Iterator it = noiseControlsList.iterator();
            while (it.hasNext()) {
                String name = ((NoiseControl) it.next()).getName();
                BluetoothDeviceManager bluetoothDeviceManager = this.bluetoothDeviceManager;
                if (Intrinsics.areEqual(name, bluetoothDeviceManager.getNoiseControlOffTitle())) {
                    this.showNoiseControlOff.setValue(Boolean.TRUE);
                } else if (Intrinsics.areEqual(name, bluetoothDeviceManager.getActiveNoiseControlTitle())) {
                    mutableLiveData.setValue(Boolean.TRUE);
                } else if (Intrinsics.areEqual(name, bluetoothDeviceManager.getAmbientSoundTitle())) {
                    this.showAmbientSound.setValue(Boolean.TRUE);
                } else if (Intrinsics.areEqual(name, bluetoothDeviceManager.getAdaptiveTitle())) {
                    this.showAdaptive.setValue(Boolean.TRUE);
                }
            }
        }
        Integer noiseCancelingLevel = budsInfo.getNoiseCancelingLevel();
        if (noiseCancelingLevel != null) {
            noiseCancelingLevel.intValue();
            Object value = mutableLiveData.getValue();
            Boolean bool = Boolean.TRUE;
            if (Intrinsics.areEqual(value, bool)) {
                this.showActiveNoiseCancelingSeekBar.setValue(bool);
            }
        }
    }

    public final String toString() {
        return "[showNoiseControlOff=" + this.showNoiseControlOff.getValue() + ", showAmbientSound=" + this.showAmbientSound.getValue() + ", showAdaptive=" + this.showAdaptive.getValue() + ", showActiveNoiseCanceling=" + this.showActiveNoiseCanceling.getValue() + ", showActiveNoiseCancelingSeekBar=" + this.showActiveNoiseCancelingSeekBar.getValue() + "]";
    }
}
