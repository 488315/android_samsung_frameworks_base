package com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.p016qs.bar.soundcraft.model.ModelProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AmbientSoundViewModel extends NoiseControlIconViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ModelProvider modelProvider;

    public AmbientSoundViewModel(Context context, ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager) {
        this.modelProvider = modelProvider;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel
    public final BluetoothDeviceManager getBluetoothDeviceManager() {
        return this.bluetoothDeviceManager;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel
    public final int getDrawableOff() {
        return R.drawable.soundcraft_ic_buds3_ambient_sound_unselelcted;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel
    public final int getDrawableOn() {
        return R.drawable.soundcraft_ic_buds3_ambient_sound;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel
    public final String getItemName() {
        return this.bluetoothDeviceManager.getAmbientSoundTitle();
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel
    public final ModelProvider getModelProvider() {
        return this.modelProvider;
    }
}
