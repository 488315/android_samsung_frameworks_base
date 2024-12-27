package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.utils.ToastUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class AmbientSoundViewModel extends NoiseControlIconViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final Context context;
    public final ModelProvider modelProvider;

    public AmbientSoundViewModel(Context context, ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final boolean checkWearingOn() {
        boolean z = isWearingL$1() || isWearingR$1();
        if (!z) {
            ToastUtil toastUtil = ToastUtil.INSTANCE;
            Context context = this.context;
            String string = context.getResources().getString(R.string.sound_craft_ambient_wearing_warning);
            toastUtil.getClass();
            ToastUtil.makeToast(context, string);
        }
        return z;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final BluetoothDeviceManager getBluetoothDeviceManager() {
        return this.bluetoothDeviceManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final String getDisplayItemName() {
        return this.bluetoothDeviceManager.getAmbientSoundTitle();
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final int getDrawableOff() {
        return R.drawable.soundcraft_ic_buds3_ambient_sound_unselelcted;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final int getDrawableOn() {
        return R.drawable.soundcraft_ic_buds3_ambient_sound;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final String getItemName() {
        return this.bluetoothDeviceManager.getAmbientSoundTitle();
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final ModelProvider getModelProvider() {
        return this.modelProvider;
    }
}
