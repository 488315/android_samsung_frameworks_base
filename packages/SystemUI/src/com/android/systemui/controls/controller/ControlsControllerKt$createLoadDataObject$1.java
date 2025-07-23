package com.android.systemui.controls.controller;

import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
