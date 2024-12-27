package com.android.systemui.controls.management;

import com.android.systemui.controls.controller.ControlsController;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
