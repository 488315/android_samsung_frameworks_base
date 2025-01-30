package com.android.systemui.p016qs.bar.soundcraft.viewmodel.actionbar;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseViewModel;
import dagger.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SoundCraftActionBarViewModel extends BaseViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final Lazy qsPanelControllerLazy;
    public final Lazy soundCraftAdapter;
    public final kotlin.Lazy title$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel$title$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            BluetoothDevice activeDevice = SoundCraftActionBarViewModel.this.bluetoothDeviceManager.getActiveDevice();
            String name = activeDevice != null ? activeDevice.getName() : null;
            if (name == null) {
                name = "";
            }
            return new MutableLiveData(name);
        }
    });

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public SoundCraftActionBarViewModel(Context context, Lazy lazy, BluetoothDeviceManager bluetoothDeviceManager, Lazy lazy2) {
        this.qsPanelControllerLazy = lazy;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        this.soundCraftAdapter = lazy2;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseViewModel
    public final void notifyChange() {
        MutableLiveData mutableLiveData = (MutableLiveData) this.title$delegate.getValue();
        BluetoothDevice activeDevice = this.bluetoothDeviceManager.getActiveDevice();
        String name = activeDevice != null ? activeDevice.getName() : null;
        if (name == null) {
            name = "";
        }
        Log.d("SoundCraft.SoundCraftActionBarViewModel", "btName=".concat(name));
        mutableLiveData.setValue(name);
    }
}
