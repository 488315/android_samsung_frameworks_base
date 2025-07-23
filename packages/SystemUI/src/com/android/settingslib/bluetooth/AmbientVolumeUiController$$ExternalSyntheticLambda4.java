package com.android.settingslib.bluetooth;

import android.bluetooth.AudioInputControl;
import android.bluetooth.BluetoothDevice;
import com.android.settingslib.bluetooth.HearingDeviceLocalDataManager;
import com.android.systemui.accessibility.hearingaid.AmbientVolumeLayout;
import com.android.systemui.accessibility.hearingaid.AmbientVolumeSlider;
import com.google.android.material.slider.Slider;
import java.util.List;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AmbientVolumeUiController$$ExternalSyntheticLambda4 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AmbientVolumeUiController f$0;

    public /* synthetic */ AmbientVolumeUiController$$ExternalSyntheticLambda4(AmbientVolumeUiController ambientVolumeUiController, int i) {
        this.$r8$classId = i;
        this.f$0 = ambientVolumeUiController;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        AmbientVolumeUiController ambientVolumeUiController = this.f$0;
        Integer num = (Integer) obj;
        BluetoothDevice bluetoothDevice = (BluetoothDevice) obj2;
        switch (i) {
            case 0:
                AmbientVolumeLayout ambientVolumeLayout = (AmbientVolumeLayout) ambientVolumeUiController.mAmbientLayout;
                boolean z = ambientVolumeLayout.mMuted;
                HearingDeviceLocalDataManager hearingDeviceLocalDataManager = ambientVolumeUiController.mLocalDataManager;
                if (!z) {
                    HearingDeviceLocalDataManager.Data data = hearingDeviceLocalDataManager.get(bluetoothDevice);
                    ambientVolumeUiController.mVolumeController.setAmbient(bluetoothDevice, ambientVolumeLayout.mExpanded ? data.ambient : data.groupAmbient);
                }
                hearingDeviceLocalDataManager.updateAmbientControlExpanded(bluetoothDevice, ambientVolumeLayout.mExpanded);
                break;
            case 1:
                AmbientVolumeController ambientVolumeController = ambientVolumeUiController.mVolumeController;
                List ambientControls = ambientVolumeController.getAmbientControls(bluetoothDevice);
                int i2 = HearingDeviceLocalDataManager.Data.$r8$clinit;
                int gainSettingMax = !ambientControls.isEmpty() ? ((AudioInputControl) ambientControls.getFirst()).getGainSettingMax() : Integer.MIN_VALUE;
                List ambientControls2 = ambientVolumeController.getAmbientControls(bluetoothDevice);
                int gainSettingMin = ambientControls2.isEmpty() ? Integer.MIN_VALUE : ((AudioInputControl) ambientControls2.getFirst()).getGainSettingMin();
                if (gainSettingMin != gainSettingMax) {
                    num.intValue();
                    AmbientVolumeLayout ambientVolumeLayout2 = (AmbientVolumeLayout) ambientVolumeUiController.mAmbientLayout;
                    AmbientVolumeSlider ambientVolumeSlider = (AmbientVolumeSlider) ambientVolumeLayout2.mSideToSliderMap.get(num);
                    if (ambientVolumeSlider != null) {
                        Slider slider = ambientVolumeSlider.mSlider;
                        slider.valueFrom = gainSettingMin;
                        slider.dirtyConfig = true;
                        slider.postInvalidate();
                        Slider slider2 = ambientVolumeSlider.mSlider;
                        slider2.valueTo = gainSettingMax;
                        slider2.dirtyConfig = true;
                        slider2.postInvalidate();
                    }
                    AmbientVolumeSlider ambientVolumeSlider2 = (AmbientVolumeSlider) ambientVolumeLayout2.mSideToSliderMap.get(999);
                    if (ambientVolumeSlider2 != null) {
                        Slider slider3 = ambientVolumeSlider2.mSlider;
                        slider3.valueFrom = gainSettingMin;
                        slider3.dirtyConfig = true;
                        slider3.postInvalidate();
                        Slider slider4 = ambientVolumeSlider2.mSlider;
                        slider4.valueTo = gainSettingMax;
                        slider4.dirtyConfig = true;
                        slider4.postInvalidate();
                        break;
                    }
                }
                break;
            default:
                ambientVolumeUiController.loadLocalDataToUi(bluetoothDevice);
                break;
        }
    }
}
