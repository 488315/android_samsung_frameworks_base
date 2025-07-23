package com.android.systemui.controls.management;

import com.android.systemui.controls.controller.ControlsController;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SaveWrapper {
    public final ControlsController.LoadData data;

    public SaveWrapper(ControlsController.LoadData loadData) {
        this.data = loadData;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SaveWrapper) && Intrinsics.areEqual(this.data, ((SaveWrapper) obj).data);
    }

    public final int hashCode() {
        ControlsController.LoadData loadData = this.data;
        if (loadData == null) {
            return 0;
        }
        return loadData.hashCode();
    }

    public final String toString() {
        return "SaveWrapper(data=" + this.data + ")";
    }
}
