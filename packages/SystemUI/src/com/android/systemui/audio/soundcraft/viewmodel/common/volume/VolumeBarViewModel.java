package com.android.systemui.audio.soundcraft.viewmodel.common.volume;

import android.bluetooth.BluetoothDevice;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.common.VolumeModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import com.android.systemui.volume.util.BluetoothIconUtil;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class VolumeBarViewModel extends BaseViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ModelProvider modelProvider;
    public final VolumeBarViewModel$recheckCallback$1 recheckCallback;
    public final VolumeManager volumeManager;
    public final MutableLiveData progress = new MutableLiveData();
    public final MutableLiveData progressMin = new MutableLiveData();
    public final MutableLiveData progressMax = new MutableLiveData();
    public final MutableLiveData seekBarEnabled = new MutableLiveData();
    public final MutableLiveData isAuraCasting = new MutableLiveData();
    public final MutableLiveData zenModeEnabled = new MutableLiveData();
    public final MutableLiveData allSoundMuteEnabled = new MutableLiveData();
    public final MutableLiveData iconAnimationType = new MutableLiveData();
    public final MutableLiveData isTouching = new MutableLiveData();
    public final MutableLiveData smartViewEnabled = new MutableLiveData();
    public final Lazy mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel$mainThreadHandler$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new Handler(Looper.getMainLooper());
        }
    });

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

    /* JADX WARN: Type inference failed for: r3v14, types: [com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel$recheckCallback$1] */
    public VolumeBarViewModel(ModelProvider modelProvider, VolumeManager volumeManager, BluetoothDeviceManager bluetoothDeviceManager) {
        this.modelProvider = modelProvider;
        this.volumeManager = volumeManager;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        Function2 function2 = new Function2() { // from class: com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                VolumeModel volumeModel = (VolumeModel) obj;
                boolean booleanValue = ((Boolean) obj2).booleanValue();
                Log.d("SoundCraft.VolumeBarViewModel", "updateVolumeModel model: " + volumeModel);
                VolumeBarViewModel volumeBarViewModel = VolumeBarViewModel.this;
                volumeBarViewModel.modelProvider.volumeModel = volumeModel;
                if (!booleanValue) {
                    volumeBarViewModel.seekBarEnabled.postValue(Boolean.valueOf(volumeModel.enable));
                    volumeBarViewModel.isAuraCasting.postValue(Boolean.valueOf(volumeModel.isBroadcast));
                    volumeBarViewModel.zenModeEnabled.postValue(Integer.valueOf(volumeModel.isZenMode));
                    volumeBarViewModel.allSoundMuteEnabled.postValue(Boolean.valueOf(volumeModel.isAllSoundMute));
                    volumeBarViewModel.iconAnimationType.postValue(Boolean.valueOf(volumeBarViewModel.getSupportAnimatedIcon()));
                    volumeBarViewModel.progress.postValue(Integer.valueOf(volumeModel.volume));
                    volumeBarViewModel.progressMin.postValue(Integer.valueOf(volumeModel.minVolume));
                    volumeBarViewModel.progressMax.postValue(Integer.valueOf(volumeModel.maxVolume));
                    volumeBarViewModel.smartViewEnabled.postValue(Boolean.valueOf(volumeModel.isSmartViewEnabled));
                }
                return Unit.INSTANCE;
            }
        };
        if (Trace.isTagEnabled(4096L)) {
            Trace.traceBegin(4096L, "#soundCraft.VolumeManager_updateVolumeModel");
            try {
                volumeManager.volumeModelCallback = function2;
                function2.invoke(volumeManager.getVolumeModel(), Boolean.valueOf(volumeManager.isTracking));
                Unit unit = Unit.INSTANCE;
            } finally {
                Trace.traceEnd(4096L);
            }
        } else {
            volumeManager.volumeModelCallback = function2;
            function2.invoke(volumeManager.getVolumeModel(), Boolean.valueOf(volumeManager.isTracking));
        }
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumeBarViewModel volumeBarViewModel = VolumeBarViewModel.this;
                VolumeManager volumeManager2 = volumeBarViewModel.volumeManager;
                volumeManager2.isTracking = false;
                volumeBarViewModel.modelProvider.volumeModel = volumeManager2.getVolumeModel();
                VolumeBarViewModel.this.notifyChange();
            }
        };
    }

    public final void forceVolumeControlStream(int i) {
        VolumeManager volumeManager = this.volumeManager;
        volumeManager.getClass();
        if (!Trace.isTagEnabled(4096L)) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "forceVolumeControlStream ", "SoundCraft.VolumeManager");
            volumeManager.audioManager.forceVolumeControlStream(i);
            return;
        }
        Trace.traceBegin(4096L, "#soundCraft.VolumeManager_forceVolumeControlStream");
        try {
            Log.d("SoundCraft.VolumeManager", "forceVolumeControlStream " + i);
            volumeManager.audioManager.forceVolumeControlStream(i);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    public final int getIconType() {
        int i = this.modelProvider.volumeModel.device;
        if (i != 128) {
            if (i == 32768) {
                return 8;
            }
            if (i != 67108864) {
                return i != 134217728 ? 1 : 7;
            }
            return 2;
        }
        BluetoothDevice activeDevice = this.bluetoothDeviceManager.getActiveDevice();
        if (activeDevice != null) {
            BluetoothIconUtil.INSTANCE.getClass();
            if (BluetoothIconUtil.isNextBudsModel(activeDevice)) {
                return 9;
            }
            if (BluetoothIconUtil.isBuds3(activeDevice)) {
                return 5;
            }
            if (BluetoothIconUtil.isBuds(activeDevice)) {
                return 4;
            }
            if (BluetoothIconUtil.isHomeMini(activeDevice)) {
                return 6;
            }
        }
        return 3;
    }

    public final boolean getSupportAnimatedIcon() {
        return this.modelProvider.volumeModel.device == 0;
    }

    public final boolean isVolumeShocked(int i) {
        int semGetEarProtectLimit;
        return getIconType() != 1 && !Intrinsics.areEqual(this.seekBarEnabled.getValue(), Boolean.FALSE) && (semGetEarProtectLimit = (AudioManager.semGetEarProtectLimit() - 1) * 10) > 0 && semGetEarProtectLimit < i;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        VolumeModel volumeModel = this.modelProvider.volumeModel;
        this.seekBarEnabled.setValue(Boolean.valueOf(volumeModel.enable));
        this.isAuraCasting.setValue(Boolean.valueOf(volumeModel.isBroadcast));
        this.zenModeEnabled.setValue(Integer.valueOf(volumeModel.isZenMode));
        this.allSoundMuteEnabled.setValue(Boolean.valueOf(volumeModel.isAllSoundMute));
        this.iconAnimationType.setValue(Boolean.valueOf(getSupportAnimatedIcon()));
        this.progress.setValue(Integer.valueOf(volumeModel.volume));
        this.progressMin.setValue(Integer.valueOf(volumeModel.minVolume));
        this.progressMax.setValue(Integer.valueOf(volumeModel.maxVolume));
        this.smartViewEnabled.setValue(Boolean.valueOf(volumeModel.isSmartViewEnabled));
    }
}
