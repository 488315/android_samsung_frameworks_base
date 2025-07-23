package com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel;

import com.android.systemui.common.shared.model.Color;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ConnectedDeviceViewModel {
    public final CharSequence deviceName;
    public final Color deviceNameColor;
    public final CharSequence label;
    public final Color labelColor;

    public ConnectedDeviceViewModel(CharSequence charSequence, Color color, CharSequence charSequence2, Color color2) {
        this.label = charSequence;
        this.labelColor = color;
        this.deviceName = charSequence2;
        this.deviceNameColor = color2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConnectedDeviceViewModel)) {
            return false;
        }
        ConnectedDeviceViewModel connectedDeviceViewModel = (ConnectedDeviceViewModel) obj;
        return Intrinsics.areEqual(this.label, connectedDeviceViewModel.label) && Intrinsics.areEqual(this.labelColor, connectedDeviceViewModel.labelColor) && Intrinsics.areEqual(this.deviceName, connectedDeviceViewModel.deviceName) && Intrinsics.areEqual(this.deviceNameColor, connectedDeviceViewModel.deviceNameColor);
    }

    public final int hashCode() {
        int hashCode = (this.labelColor.hashCode() + (this.label.hashCode() * 31)) * 31;
        CharSequence charSequence = this.deviceName;
        return this.deviceNameColor.hashCode() + ((hashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31);
    }

    public final String toString() {
        return "ConnectedDeviceViewModel(label=" + ((Object) this.label) + ", labelColor=" + this.labelColor + ", deviceName=" + ((Object) this.deviceName) + ", deviceNameColor=" + this.deviceNameColor + ")";
    }
}
