package com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.qs.bar.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.qs.bar.soundcraft.model.NoiseControl;
import com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoiseCancelingSwitchBarViewModel extends BaseToggleViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ModelProvider modelProvider;

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

    public NoiseCancelingSwitchBarViewModel(Context context, ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager) {
        this.modelProvider = modelProvider;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseViewModel
    public final void notifyChange() {
        Set noiseControlsList = this.modelProvider.budsInfo.getNoiseControlsList();
        if (noiseControlsList != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : noiseControlsList) {
                if (Intrinsics.areEqual(((NoiseControl) obj).getName(), this.bluetoothDeviceManager.getActiveNoiseControlTitle())) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.isSelected.setValue(Boolean.valueOf(((NoiseControl) it.next()).getState()));
            }
        }
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel
    public final void onClick() {
        boolean z;
        Set noiseControlsList = this.modelProvider.budsInfo.getNoiseControlsList();
        MutableLiveData mutableLiveData = this.isSelected;
        BluetoothDeviceManager bluetoothDeviceManager = this.bluetoothDeviceManager;
        boolean z2 = false;
        if (noiseControlsList != null) {
            ArrayList<NoiseControl> arrayList = new ArrayList();
            for (Object obj : noiseControlsList) {
                if (Intrinsics.areEqual(((NoiseControl) obj).getName(), bluetoothDeviceManager.getActiveNoiseControlTitle())) {
                    arrayList.add(obj);
                }
            }
            loop1: while (true) {
                for (NoiseControl noiseControl : arrayList) {
                    Boolean bool = (Boolean) mutableLiveData.getValue();
                    if (bool != null) {
                        z = bool.booleanValue() ? false : true;
                    } else if (!noiseControl.getState()) {
                    }
                }
            }
            z2 = z;
        }
        bluetoothDeviceManager.updateNoiseControlList(new NoiseControl(z2 ? bluetoothDeviceManager.getActiveNoiseControlTitle() : bluetoothDeviceManager.getNoiseControlOffTitle(), true));
        mutableLiveData.setValue(Boolean.valueOf(z2));
    }
}
