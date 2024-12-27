package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.buds.NoiseControl;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel;
import java.util.Collection;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public abstract class NoiseControlIconViewModel extends BaseToggleViewModel {
    public final MutableLiveData background = new MutableLiveData();
    public final MutableLiveData iconColor = new MutableLiveData();

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

    public final boolean isWearingBoth$1() {
        return (isWearingL$1() && isWearingR$1()) || (Intrinsics.areEqual(getModelProvider().budsModel.getOneEarbudNoiseControls(), Boolean.TRUE) && (isWearingL$1() || isWearingR$1()));
    }

    public final boolean isWearingL$1() {
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

    public final boolean isWearingR$1() {
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
                if (getItemName().equals(noiseControl.getName())) {
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
            if (getItemName().equals(noiseControl.getName())) {
                if (noiseControl.getState()) {
                    return;
                }
                getBluetoothDeviceManager().updateNoiseControlList(new NoiseControl(noiseControl.getName(), true));
                return;
            }
        }
    }
}
