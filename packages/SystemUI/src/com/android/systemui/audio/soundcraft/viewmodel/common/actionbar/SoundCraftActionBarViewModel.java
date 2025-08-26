package com.android.systemui.audio.soundcraft.viewmodel.common.actionbar;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.SoundCraftCoverController;
import com.android.systemui.audio.soundcraft.SoundCraftNowBarController;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.view.SoundCraftQpDetailAdapter;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class SoundCraftActionBarViewModel extends BaseViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ModelProvider modelProvider;
    public final Lazy qsDetailControllerLazy;
    public final SoundCraftCoverController soundCraftCoverController;
    public final SoundCraftNowBarController soundCraftNowBarController;
    public final SoundCraftQpDetailAdapter soundCraftQpDetailAdapter;
    public final MutableLiveData title = new MutableLiveData("");
    public final MutableLiveData isCoverScreen = new MutableLiveData(Boolean.FALSE);

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

    public SoundCraftActionBarViewModel(Context context, ModelProvider modelProvider, Lazy lazy, BluetoothDeviceManager bluetoothDeviceManager, SoundCraftQpDetailAdapter soundCraftQpDetailAdapter, SoundCraftNowBarController soundCraftNowBarController, SoundCraftCoverController soundCraftCoverController) {
        this.modelProvider = modelProvider;
        this.qsDetailControllerLazy = lazy;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        this.soundCraftQpDetailAdapter = soundCraftQpDetailAdapter;
        this.soundCraftNowBarController = soundCraftNowBarController;
        this.soundCraftCoverController = soundCraftCoverController;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        String name;
        MutableLiveData mutableLiveData = this.title;
        BluetoothDevice activeDevice = this.bluetoothDeviceManager.getActiveDevice();
        if (activeDevice == null || (name = activeDevice.getName()) == null) {
            name = "";
        }
        mutableLiveData.setValue(name);
        this.isCoverScreen.setValue(Boolean.valueOf(this.modelProvider.isFromCover));
    }
}
