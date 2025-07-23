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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        String str;
        MutableLiveData mutableLiveData = this.title;
        BluetoothDevice activeDevice = this.bluetoothDeviceManager.getActiveDevice();
        if (activeDevice == null || (str = activeDevice.getName()) == null) {
            str = "";
        }
        mutableLiveData.setValue(str);
        this.isCoverScreen.setValue(Boolean.valueOf(this.modelProvider.isFromCover));
    }
}
