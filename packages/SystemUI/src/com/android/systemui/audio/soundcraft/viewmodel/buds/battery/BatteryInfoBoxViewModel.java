package com.android.systemui.audio.soundcraft.viewmodel.buds.battery;

import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BatteryInfoBoxViewModel extends BaseViewModel {
    public final ModelProvider modelProvider;
    public final MutableLiveData batteryLevelLeft = new MutableLiveData();
    public final MutableLiveData batteryLevelRight = new MutableLiveData();
    public final MutableLiveData batteryLevelCradle = new MutableLiveData();
    public final MutableLiveData isCoverScreen = new MutableLiveData();

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

    public BatteryInfoBoxViewModel(ModelProvider modelProvider, BluetoothDeviceManager bluetoothDeviceManager) {
        this.modelProvider = modelProvider;
    }

    public static boolean isValid(String str) {
        return (str == null || StringsKt__StringsKt.isBlank(str) || str.equals("-1")) ? false : true;
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
        this.isCoverScreen.setValue(Boolean.valueOf(modelProvider.isFromCover));
    }

    public final String toString() {
        return "[BatteryL=" + this.batteryLevelLeft.getValue() + ", BatteryR=" + this.batteryLevelRight.getValue() + ", BatteryC=" + this.batteryLevelCradle.getValue() + "]";
    }
}
