package com.android.systemui.qs.bar.soundcraft.viewmodel.wearable;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.qs.bar.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseViewModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WearableLinkBoxViewModel extends BaseViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final Context context;
    public final LocalBluetoothManager localBtManager;

    public WearableLinkBoxViewModel(Context context, ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager, LocalBluetoothManager localBluetoothManager) {
        this.context = context;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        this.localBtManager = localBluetoothManager;
        new MutableLiveData();
    }
}
