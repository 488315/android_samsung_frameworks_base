package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import android.bluetooth.BluetoothDevice;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceExtension;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothStateEnum;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.buds.NoiseControl;
import com.android.systemui.audio.soundcraft.utils.SoundCraftSALogging;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel;
import java.util.Collection;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NoiseControlIconViewModel extends BaseToggleViewModel {
    public final MutableLiveData background = new MutableLiveData();
    public final MutableLiveData iconColor = new MutableLiveData();

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

    public abstract boolean checkWearingOn();

    public BluetoothDeviceManager getBluetoothDeviceManager() {
        return null;
    }

    public abstract String getDisplayItemName();

    public abstract int getDrawableOff();

    public abstract int getDrawableOn();

    public abstract String getItemName();

    public ModelProvider getModelProvider() {
        return null;
    }

    public abstract SoundCraftSALogging.Event getSALoggingEvent();

    public final boolean isWearingL() {
        Set noiseControlsList = getModelProvider().budsModel.getNoiseControlsList();
        if (noiseControlsList == null) {
            return false;
        }
        Set<NoiseControl> set = noiseControlsList;
        if ((set instanceof Collection) && set.isEmpty()) {
            return false;
        }
        for (NoiseControl noiseControl : set) {
            if (Intrinsics.areEqual(noiseControl.getName(), "wearing_l") && noiseControl.getState()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isWearingR() {
        Set noiseControlsList = getModelProvider().budsModel.getNoiseControlsList();
        if (noiseControlsList == null) {
            return false;
        }
        Set<NoiseControl> set = noiseControlsList;
        if ((set instanceof Collection) && set.isEmpty()) {
            return false;
        }
        for (NoiseControl noiseControl : set) {
            if (Intrinsics.areEqual(noiseControl.getName(), "wearing_r") && noiseControl.getState()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        this.name.setValue(getDisplayItemName());
        Set<NoiseControl> noiseControlsList = getModelProvider().budsModel.getNoiseControlsList();
        if (noiseControlsList != null) {
            for (NoiseControl noiseControl : noiseControlsList) {
                if (Intrinsics.areEqual(getItemName(), noiseControl.getName())) {
                    boolean state = noiseControl.getState();
                    MutableLiveData mutableLiveData = this.background;
                    MutableLiveData mutableLiveData2 = this.iconColor;
                    MutableLiveData mutableLiveData3 = this.icon;
                    if (state) {
                        mutableLiveData3.setValue(Integer.valueOf(getDrawableOn()));
                        mutableLiveData2.setValue(Integer.valueOf(R.color.soundcraft_selected_icon_color));
                        mutableLiveData.setValue(Integer.valueOf(R.drawable.soundcraft_icon_background_selected));
                    } else {
                        mutableLiveData3.setValue(Integer.valueOf(getDrawableOff()));
                        mutableLiveData2.setValue(Integer.valueOf(R.color.soundcraft_unselected_icon_color));
                        mutableLiveData.setValue(Integer.valueOf(R.drawable.soundcraft_icon_background_unselected));
                    }
                    this.isSelected.setValue(Boolean.valueOf(noiseControl.getState()));
                }
            }
        }
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel
    public final void onClick() {
        Set<NoiseControl> noiseControlsList;
        if (!checkWearingOn() || (noiseControlsList = getModelProvider().budsModel.getNoiseControlsList()) == null) {
            return;
        }
        for (NoiseControl noiseControl : noiseControlsList) {
            if (Intrinsics.areEqual(getItemName(), noiseControl.getName())) {
                if (noiseControl.getState()) {
                    return;
                }
                SoundCraftSALogging.sendEventLog$default(SoundCraftSALogging.INSTANCE, SoundCraftSALogging.ScreenId.EID_BUDS_DETAIL_SETTING, getSALoggingEvent(), null, 12);
                BluetoothDeviceManager bluetoothDeviceManager = getBluetoothDeviceManager();
                NoiseControl noiseControl2 = new NoiseControl(noiseControl.getName(), true);
                BluetoothDevice activeDevice = bluetoothDeviceManager.getActiveDevice();
                if (activeDevice != null) {
                    String name = noiseControl2.getName();
                    if (Intrinsics.areEqual(name, bluetoothDeviceManager.getActiveNoiseControlTitle())) {
                        BluetoothDeviceExtension.INSTANCE.getClass();
                        BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.ANC, true);
                        bluetoothDeviceManager.ancRequested = true;
                    } else if (Intrinsics.areEqual(name, bluetoothDeviceManager.getAmbientSoundTitle())) {
                        BluetoothDeviceExtension.INSTANCE.getClass();
                        BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.AMBIENT, true);
                        bluetoothDeviceManager.ambientRequested = true;
                    } else if (Intrinsics.areEqual(name, bluetoothDeviceManager.getAdaptiveTitle())) {
                        BluetoothDeviceExtension.INSTANCE.getClass();
                        BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.ADAPTIVE, true);
                        bluetoothDeviceManager.adaptiveRequested = true;
                    } else if (Intrinsics.areEqual(name, bluetoothDeviceManager.context.getResources().getString(R.string.sound_craft_wearable_noise_control_off))) {
                        BluetoothDeviceExtension.INSTANCE.getClass();
                        BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.ANC, false);
                        BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.AMBIENT, false);
                        BluetoothDeviceExtension.setState(activeDevice, BluetoothStateEnum.ADAPTIVE, false);
                        bluetoothDeviceManager.ancRequested = false;
                        bluetoothDeviceManager.ambientRequested = false;
                        bluetoothDeviceManager.adaptiveRequested = false;
                    }
                    bluetoothDeviceManager.isChanged = true;
                    return;
                }
                return;
            }
        }
    }
}
