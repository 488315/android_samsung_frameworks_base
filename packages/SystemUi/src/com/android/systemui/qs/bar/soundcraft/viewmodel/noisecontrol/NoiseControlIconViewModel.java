package com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol;

import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.qs.bar.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.qs.bar.soundcraft.model.NoiseControl;
import com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NoiseControlIconViewModel extends BaseToggleViewModel {
    public final MutableLiveData background = new MutableLiveData();
    public final MutableLiveData iconColor = new MutableLiveData();

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

    public BluetoothDeviceManager getBluetoothDeviceManager() {
        return null;
    }

    public abstract int getDrawableOff();

    public abstract int getDrawableOn();

    public abstract String getItemName();

    public ModelProvider getModelProvider() {
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0036, code lost:
    
        if (kotlin.text.StringsKt__StringsKt.contains(r3, r2.getName(), true) == true) goto L13;
     */
    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseViewModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void notifyChange() {
        MutableLiveData mutableLiveData = this.name;
        mutableLiveData.setValue(getItemName());
        Set<NoiseControl> noiseControlsList = getModelProvider().budsInfo.getNoiseControlsList();
        if (noiseControlsList != null) {
            for (NoiseControl noiseControl : noiseControlsList) {
                String str = (String) mutableLiveData.getValue();
                boolean z = str != null;
                if (z) {
                    boolean state = noiseControl.getState();
                    MutableLiveData mutableLiveData2 = this.background;
                    MutableLiveData mutableLiveData3 = this.iconColor;
                    MutableLiveData mutableLiveData4 = this.icon;
                    if (state) {
                        mutableLiveData4.setValue(Integer.valueOf(getDrawableOn()));
                        mutableLiveData3.setValue(Integer.valueOf(R.color.soundcraft_selected_icon_color));
                        mutableLiveData2.setValue(Integer.valueOf(R.drawable.soundcraft_icon_background_selected));
                    } else {
                        mutableLiveData4.setValue(Integer.valueOf(getDrawableOff()));
                        mutableLiveData3.setValue(Integer.valueOf(R.color.soundcraft_unselected_icon_color));
                        mutableLiveData2.setValue(Integer.valueOf(R.drawable.soundcraft_icon_background_unselected));
                    }
                    this.isSelected.setValue(Boolean.valueOf(noiseControl.getState()));
                }
            }
        }
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel
    public final void onClick() {
        Set<NoiseControl> noiseControlsList = getModelProvider().budsInfo.getNoiseControlsList();
        if (noiseControlsList != null) {
            for (NoiseControl noiseControl : noiseControlsList) {
                String str = (String) this.name.getValue();
                if (str != null && StringsKt__StringsKt.contains(str, noiseControl.getName(), true)) {
                    if (noiseControl.getState()) {
                        return;
                    }
                    getBluetoothDeviceManager().updateNoiseControlList(new NoiseControl(noiseControl.getName(), true));
                    return;
                }
            }
        }
    }
}
