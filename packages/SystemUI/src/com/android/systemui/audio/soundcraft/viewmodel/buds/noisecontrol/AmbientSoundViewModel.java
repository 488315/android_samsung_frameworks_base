package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.utils.SoundCraftSALogging;
import com.android.systemui.audio.soundcraft.utils.ToastUtil;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        if (isWearingL() || isWearingR()) {
            return true;
        }
        ToastUtil toastUtil = ToastUtil.INSTANCE;
        Context context = this.context;
        String string = context.getResources().getString(R.string.sound_craft_noise_control_none_wearing_warning);
        toastUtil.getClass();
        ToastUtil.makeToast(context, string);
        return false;
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

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel
    public final SoundCraftSALogging.Event getSALoggingEvent() {
        return SoundCraftSALogging.Event.AMBIENT_SOUND;
    }
}
