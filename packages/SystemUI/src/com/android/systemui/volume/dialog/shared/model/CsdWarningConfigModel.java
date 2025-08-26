package com.android.systemui.volume.dialog.shared.model;

import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.volume.CsdWarningAction;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class CsdWarningConfigModel {
    public final List actions;

    public CsdWarningConfigModel(List<CsdWarningAction> list) {
        this.actions = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof CsdWarningConfigModel) && Intrinsics.areEqual(this.actions, ((CsdWarningConfigModel) obj).actions);
    }

    public final int hashCode() {
        return this.actions.hashCode();
    }

    public final String toString() {
        return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("CsdWarningConfigModel(actions=", this.actions, ")");
    }
}
