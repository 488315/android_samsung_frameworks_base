package com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ConnectedDeviceViewModel {
    public final CharSequence deviceName;
    public final CharSequence label;

    public ConnectedDeviceViewModel(CharSequence charSequence, CharSequence charSequence2) {
        this.label = charSequence;
        this.deviceName = charSequence2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConnectedDeviceViewModel)) {
            return false;
        }
        ConnectedDeviceViewModel connectedDeviceViewModel = (ConnectedDeviceViewModel) obj;
        return Intrinsics.areEqual(this.label, connectedDeviceViewModel.label) && Intrinsics.areEqual(this.deviceName, connectedDeviceViewModel.deviceName);
    }

    public final int hashCode() {
        int hashCode = this.label.hashCode() * 31;
        CharSequence charSequence = this.deviceName;
        return hashCode + (charSequence == null ? 0 : charSequence.hashCode());
    }

    public final String toString() {
        return "ConnectedDeviceViewModel(label=" + ((Object) this.label) + ", deviceName=" + ((Object) this.deviceName) + ")";
    }
}
