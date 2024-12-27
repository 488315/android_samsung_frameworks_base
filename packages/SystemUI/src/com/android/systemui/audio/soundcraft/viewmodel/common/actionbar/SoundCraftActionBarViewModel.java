package com.android.systemui.audio.soundcraft.viewmodel.common.actionbar;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.SoundCraftNowBarController;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.view.SoundCraftQpDetailAdapter;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SoundCraftActionBarViewModel extends BaseViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final Lazy qsDetailControllerLazy;
    public final SoundCraftNowBarController soundCraftController;
    public final SoundCraftQpDetailAdapter soundCraftQpDetailAdapter;
    public final MutableLiveData title = new MutableLiveData("");

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

    public SoundCraftActionBarViewModel(Context context, Lazy lazy, BluetoothDeviceManager bluetoothDeviceManager, SoundCraftQpDetailAdapter soundCraftQpDetailAdapter, SoundCraftNowBarController soundCraftNowBarController) {
        this.qsDetailControllerLazy = lazy;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        this.soundCraftQpDetailAdapter = soundCraftQpDetailAdapter;
        this.soundCraftController = soundCraftNowBarController;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        MutableLiveData mutableLiveData = this.title;
        BluetoothDevice activeDevice = this.bluetoothDeviceManager.getActiveDevice();
        String name = activeDevice != null ? activeDevice.getName() : null;
        if (name == null) {
            name = "";
        }
        mutableLiveData.setValue(name);
    }
}
