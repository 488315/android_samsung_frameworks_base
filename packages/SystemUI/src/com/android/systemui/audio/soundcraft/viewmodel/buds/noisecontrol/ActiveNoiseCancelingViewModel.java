package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.utils.ToastUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ActiveNoiseCancelingViewModel extends NoiseControlIconViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final Context context;
    public final ModelProvider modelProvider;

    public ActiveNoiseCancelingViewModel(Context context, ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final boolean checkWearingOn() {
        boolean isWearingBoth$1 = isWearingBoth$1();
        if (!isWearingBoth$1) {
            ToastUtil toastUtil = ToastUtil.INSTANCE;
            Context context = this.context;
            String string = context.getResources().getString(R.string.sound_craft_noise_control_wearing_warning);
            toastUtil.getClass();
            ToastUtil.makeToast(context, string);
        }
        return isWearingBoth$1;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final BluetoothDeviceManager getBluetoothDeviceManager() {
        return this.bluetoothDeviceManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final String getDisplayItemName() {
        return this.bluetoothDeviceManager.getActiveNoiseControlTitle();
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final int getDrawableOff() {
        return R.drawable.soundcraft_ic_buds3_anc_on_unselelcted;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final int getDrawableOn() {
        return R.drawable.soundcraft_ic_buds3_anc_on;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final String getItemName() {
        return this.bluetoothDeviceManager.getActiveNoiseControlTitle();
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final ModelProvider getModelProvider() {
        return this.modelProvider;
    }
}
