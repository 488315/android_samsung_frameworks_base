package com.android.systemui.volume.util;

import android.hardware.display.SemDisplayVolumeListener;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.basic.util.LogWrapper;
import java.util.function.Consumer;

public final class DisplayManagerWrapper$registerDisplayVolumeListener$1 implements SemDisplayVolumeListener {
    public final /* synthetic */ Consumer $isMute;
    public final /* synthetic */ DisplayManagerWrapper this$0;

    public DisplayManagerWrapper$registerDisplayVolumeListener$1(DisplayManagerWrapper displayManagerWrapper, Consumer<Boolean> consumer) {
        this.this$0 = displayManagerWrapper;
        this.$isMute = consumer;
    }

    public final void onVolumeChanged(int i, int i2, int i3, boolean z) {
        DisplayManagerWrapper displayManagerWrapper = this.this$0;
        displayManagerWrapper.displayCurrentVolume = i3;
        displayManagerWrapper.minSmartViewVol = i;
        displayManagerWrapper.maxSmartViewVol = i2;
        LogWrapper logWrapper = displayManagerWrapper.logWrapper;
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i3, i, "onDisplayVolumeChanged : curVol = ", ", minVol = ", ", maxVol = ");
        m.append(i2);
        m.append(", mute=");
        m.append(z);
        logWrapper.d("DisplayManagerWrapper", m.toString());
        this.$isMute.accept(Boolean.valueOf(z));
        ((VolumeManager) this.this$0.volumeManager$delegate.getValue()).updateCurrentVolume();
    }
}
