package com.android.systemui.accessibility.hearingaid;

import android.bluetooth.BluetoothDevice;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.AmbientVolumeUiController;
import com.android.settingslib.bluetooth.AmbientVolumeUiController$$ExternalSyntheticLambda4;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashBiMap.View.AnonymousClass1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AmbientVolumeLayout$$ExternalSyntheticLambda0 {
    public final /* synthetic */ AmbientVolumeLayout f$0;

    public /* synthetic */ AmbientVolumeLayout$$ExternalSyntheticLambda0(AmbientVolumeLayout ambientVolumeLayout) {
        this.f$0 = ambientVolumeLayout;
    }

    public final void onValueChange(AmbientVolumeSlider ambientVolumeSlider, final int i) {
        AmbientVolumeLayout ambientVolumeLayout = this.f$0;
        Integer num = (Integer) ambientVolumeLayout.mSideToSliderMap.inverse().get(ambientVolumeSlider);
        if (num != null) {
            if (ambientVolumeLayout.mUiEventLogger != null) {
                ambientVolumeLayout.mUiEventLogger.log(num.intValue() == 999 ? HearingDevicesUiEvent.HEARING_DEVICES_AMBIENT_CHANGE_UNIFIED : HearingDevicesUiEvent.HEARING_DEVICES_AMBIENT_CHANGE_SEPARATED, ambientVolumeLayout.mLaunchSourceId, null);
            }
            final AmbientVolumeUiController ambientVolumeUiController = ambientVolumeLayout.mListener;
            if (ambientVolumeUiController != null) {
                final int intValue = num.intValue();
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(intValue, i, "onSliderValueChange: side=", ", value=", "AmbientVolumeUiController");
                ambientVolumeUiController.setVolumeIfValid(intValue, i);
                Runnable runnable = new Runnable() { // from class: com.android.settingslib.bluetooth.AmbientVolumeUiController$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        AmbientVolumeUiController ambientVolumeUiController2 = AmbientVolumeUiController.this;
                        int i2 = intValue;
                        int i3 = i;
                        HashBiMap hashBiMap = ambientVolumeUiController2.mSideToDeviceMap;
                        if (i2 == 999) {
                            hashBiMap.forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda14(ambientVolumeUiController2, i3, 1));
                        } else {
                            ambientVolumeUiController2.mVolumeController.setAmbient((BluetoothDevice) hashBiMap.get(Integer.valueOf(i2)), i3);
                        }
                    }
                };
                AmbientVolumeLayout ambientVolumeLayout2 = (AmbientVolumeLayout) ambientVolumeUiController.mAmbientLayout;
                if (!ambientVolumeLayout2.mMuted) {
                    runnable.run();
                    return;
                }
                ambientVolumeLayout2.setMuted(false);
                HashBiMap hashBiMap = ambientVolumeUiController.mSideToDeviceMap;
                HashBiMap.View.AnonymousClass1 anonymousClass1 = ((HashBiMap.View) hashBiMap.values()).new AnonymousClass1();
                while (anonymousClass1.hasNext()) {
                    ambientVolumeUiController.mVolumeController.setMuted((BluetoothDevice) anonymousClass1.next(), false);
                }
                hashBiMap.forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda4(ambientVolumeUiController, 2));
                ambientVolumeUiController.mContext.getMainThreadHandler().postDelayed(runnable, 1000L);
            }
        }
    }
}
