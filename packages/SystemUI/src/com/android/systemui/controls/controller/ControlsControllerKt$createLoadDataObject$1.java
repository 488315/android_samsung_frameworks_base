package com.android.systemui.controls.controller;

import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import java.util.List;

public final class ControlsControllerKt$createLoadDataObject$1 implements ControlsController.LoadData {
    public final List allControls;
    public final boolean errorOnLoad;
    public final List favoritesIds;

    public ControlsControllerKt$createLoadDataObject$1(List<ControlStatus> list, List<String> list2, boolean z) {
        this.allControls = list;
        this.favoritesIds = list2;
        this.errorOnLoad = z;
    }
}
