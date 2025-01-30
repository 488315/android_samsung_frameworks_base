package com.android.systemui.controls.controller;

import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
