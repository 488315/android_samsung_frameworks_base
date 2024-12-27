package com.android.systemui.audio.soundcraft.viewmodel.buds.battery;

import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BatteryInfoBoxViewModel extends BaseViewModel {
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ModelProvider modelProvider;
    public final MutableLiveData batteryLevelLeft = new MutableLiveData();
    public final MutableLiveData batteryLevelRight = new MutableLiveData();
    public final MutableLiveData batteryLevelCradle = new MutableLiveData();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public BatteryInfoBoxViewModel(ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager) {
        this.modelProvider = modelProvider;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
    }

    public static boolean isValid(String str) {
        return (str == null || StringsKt__StringsJVMKt.isBlank(str) || str.equals("-1")) ? false : true;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        ModelProvider modelProvider = this.modelProvider;
        String batteryLeft = modelProvider.batteryInfo.getBatteryLeft();
        if (batteryLeft != null) {
            this.batteryLevelLeft.setValue(batteryLeft);
        }
        String batteryRight = modelProvider.batteryInfo.getBatteryRight();
        if (batteryRight != null) {
            this.batteryLevelRight.setValue(batteryRight);
        }
        String batteryCradle = modelProvider.batteryInfo.getBatteryCradle();
        if (batteryCradle != null) {
            this.batteryLevelCradle.setValue(batteryCradle);
        }
    }

    public final String toString() {
        return "[BatteryL=" + this.batteryLevelLeft.getValue() + ", BatteryR=" + this.batteryLevelRight.getValue() + ", BatteryC=" + this.batteryLevelCradle.getValue() + "]";
    }
}
