package com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.p016qs.bar.soundcraft.model.ModelProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoiseControlOffViewModel extends NoiseControlIconViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final Context context;
    public final ModelProvider modelProvider;

    public NoiseControlOffViewModel(Context context, ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel
    public final BluetoothDeviceManager getBluetoothDeviceManager() {
        return this.bluetoothDeviceManager;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel
    public final int getDrawableOff() {
        return R.drawable.soundcraft_ic_buds3_anc_off_unselelcted;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel
    public final int getDrawableOn() {
        return R.drawable.soundcraft_ic_buds3_anc_off;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel
    public final String getItemName() {
        return this.context.getResources().getString(R.string.sound_craft_noise_control_off);
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel
    public final ModelProvider getModelProvider() {
        return this.modelProvider;
    }
}
