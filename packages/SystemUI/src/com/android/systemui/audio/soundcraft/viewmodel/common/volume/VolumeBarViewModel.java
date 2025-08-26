package com.android.systemui.audio.soundcraft.viewmodel.common.volume;

import android.bluetooth.BluetoothDevice;
import android.media.AudioManager;
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
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class VolumeBarViewModel extends BaseViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ModelProvider modelProvider;
    public final VolumeBarViewModel$recheckCallback$1 recheckCallback;
    public final VolumeManager volumeManager;
    public final MutableLiveData progress = new MutableLiveData();
    public final MutableLiveData progressMin = new MutableLiveData();
    public final MutableLiveData progressMax = new MutableLiveData();
    public final MutableLiveData seekBarEnabled = new MutableLiveData();
    public final MutableLiveData iconAnimationType = new MutableLiveData();
    public final MutableLiveData isTouching = new MutableLiveData();
    public final MutableLiveData smartViewEnabled = new MutableLiveData();
    public final Lazy mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(new VolumeBarViewModel$$ExternalSyntheticLambda0());

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

    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel$recheckCallback$1] */
    public VolumeBarViewModel(ModelProvider modelProvider, VolumeManager volumeManager, BluetoothDeviceManager bluetoothDeviceManager) {
        this.modelProvider = modelProvider;
        this.volumeManager = volumeManager;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        VolumeBarViewModel$$ExternalSyntheticLambda1 volumeBarViewModel$$ExternalSyntheticLambda1 = new VolumeBarViewModel$$ExternalSyntheticLambda1(this);
        volumeManager.getClass();
        if (Trace.isTagEnabled(4096L)) {
            Trace.traceBegin(4096L, "#soundCraft.VolumeManager_updateVolumeModel");
            try {
                volumeManager.volumeModelCallback = volumeBarViewModel$$ExternalSyntheticLambda1;
                volumeBarViewModel$$ExternalSyntheticLambda1.invoke(volumeManager.getVolumeModel(), Boolean.valueOf(volumeManager.isTracking));
                Unit unit = Unit.INSTANCE;
            } finally {
                Trace.traceEnd(4096L);
            }
        } else {
            volumeManager.volumeModelCallback = volumeBarViewModel$$ExternalSyntheticLambda1;
            volumeBarViewModel$$ExternalSyntheticLambda1.invoke(volumeManager.getVolumeModel(), Boolean.valueOf(volumeManager.isTracking));
        }
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumeBarViewModel volumeBarViewModel = this.this$0;
                VolumeManager volumeManager2 = volumeBarViewModel.volumeManager;
                volumeManager2.isTracking = false;
                volumeBarViewModel.modelProvider.volumeModel = volumeManager2.getVolumeModel();
                this.this$0.notifyChange();
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
        if (i == 128) {
            BluetoothDevice activeDevice = this.bluetoothDeviceManager.getActiveDevice();
            if (activeDevice == null) {
                return 3;
            }
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
            return BluetoothIconUtil.isMusicFrame(activeDevice) ? 10 : 3;
        }
        if (i == 32768) {
            return 8;
        }
        if (i == 67108864) {
            return 2;
        }
        if (i == 134217728) {
            return 7;
        }
        if (i != 536870912) {
            return 1;
        }
        BluetoothDevice bluetoothDevice = (BluetoothDevice) CollectionsKt___CollectionsKt.firstOrNull(this.volumeManager.bluetoothAdapterWrapper.getConnectedLeDevices());
        if (bluetoothDevice == null) {
            return 3;
        }
        BluetoothIconUtil.INSTANCE.getClass();
        if (BluetoothIconUtil.isNextBudsModel(bluetoothDevice)) {
            return 9;
        }
        if (BluetoothIconUtil.isBuds3(bluetoothDevice)) {
            return 5;
        }
        return BluetoothIconUtil.isBuds(bluetoothDevice) ? 4 : 3;
    }

    public final boolean getSupportAnimatedIcon() {
        return this.modelProvider.volumeModel.device == 0;
    }

    public final boolean isVolumeShocked(int i) {
        int iSemGetEarProtectLimit;
        return getIconType() != 1 && !Intrinsics.areEqual(this.seekBarEnabled.getValue(), Boolean.FALSE) && (iSemGetEarProtectLimit = (AudioManager.semGetEarProtectLimit() - 1) * 10) > 0 && iSemGetEarProtectLimit < i;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        VolumeModel volumeModel = this.modelProvider.volumeModel;
        this.seekBarEnabled.setValue(Boolean.valueOf(volumeModel.enable));
        this.iconAnimationType.setValue(Boolean.valueOf(getSupportAnimatedIcon()));
        this.progress.setValue(Integer.valueOf(volumeModel.volume));
        this.progressMin.setValue(Integer.valueOf(volumeModel.minVolume));
        this.progressMax.setValue(Integer.valueOf(volumeModel.maxVolume));
        this.smartViewEnabled.setValue(Boolean.valueOf(volumeModel.isSmartViewEnabled));
    }
}
