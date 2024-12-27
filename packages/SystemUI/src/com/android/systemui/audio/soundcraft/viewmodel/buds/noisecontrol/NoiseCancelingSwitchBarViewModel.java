package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.buds.NoiseControl;
import com.android.systemui.audio.soundcraft.utils.ToastUtil;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NoiseCancelingSwitchBarViewModel extends BaseToggleViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final Context context;
    public final ModelProvider modelProvider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public NoiseCancelingSwitchBarViewModel(Context context, ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
    }

    public final boolean checkWearingOn() {
        boolean z = (isWearingL() && isWearingR()) || (Intrinsics.areEqual(this.modelProvider.budsModel.getOneEarbudNoiseControls(), Boolean.TRUE) && (isWearingL() || isWearingR()));
        if (!z) {
            ToastUtil toastUtil = ToastUtil.INSTANCE;
            Context context = this.context;
            String string = context.getResources().getString(R.string.sound_craft_noise_control_wearing_warning);
            toastUtil.getClass();
            ToastUtil.makeToast(context, string);
        }
        return z;
    }

    public final boolean isWearingL() {
        Set noiseControlsList = this.modelProvider.budsModel.getNoiseControlsList();
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
        Set noiseControlsList = this.modelProvider.budsModel.getNoiseControlsList();
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
        Set noiseControlsList = this.modelProvider.budsModel.getNoiseControlsList();
        if (noiseControlsList != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : noiseControlsList) {
                if (Intrinsics.areEqual(((NoiseControl) obj).getName(), this.bluetoothDeviceManager.getActiveNoiseControlTitle())) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.isSelected.setValue(Boolean.valueOf(((NoiseControl) it.next()).getState()));
            }
        }
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel
    public final void onClick() {
        boolean z;
        Set noiseControlsList = this.modelProvider.budsModel.getNoiseControlsList();
        MutableLiveData mutableLiveData = this.isSelected;
        BluetoothDeviceManager bluetoothDeviceManager = this.bluetoothDeviceManager;
        boolean z2 = false;
        if (noiseControlsList != null) {
            ArrayList<NoiseControl> arrayList = new ArrayList();
            for (Object obj : noiseControlsList) {
                if (Intrinsics.areEqual(((NoiseControl) obj).getName(), bluetoothDeviceManager.getActiveNoiseControlTitle())) {
                    arrayList.add(obj);
                }
            }
            loop1: while (true) {
                for (NoiseControl noiseControl : arrayList) {
                    Boolean bool = (Boolean) mutableLiveData.getValue();
                    if (bool != null) {
                        z = bool.booleanValue() ? false : true;
                    } else if (!noiseControl.getState()) {
                    }
                }
            }
            z2 = z;
        }
        bluetoothDeviceManager.updateNoiseControlList(new NoiseControl(z2 ? bluetoothDeviceManager.getActiveNoiseControlTitle() : bluetoothDeviceManager.getNoiseControlOffTitle(), true));
        mutableLiveData.setValue(Boolean.valueOf(z2));
    }
}
