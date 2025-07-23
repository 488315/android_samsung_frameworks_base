package com.android.systemui.controls.management;

import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerKt$createLoadDataObject$1;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecControlsFavoritingActivity$loadControls$1$1 implements Consumer {
    public final /* synthetic */ SecControlsFavoritingActivity this$0;

    public SecControlsFavoritingActivity$loadControls$1$1(SecControlsFavoritingActivity secControlsFavoritingActivity) {
        this.this$0 = secControlsFavoritingActivity;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ControlsController.LoadData loadData = (ControlsController.LoadData) obj;
        if (this.this$0.initialFavoriteIds.isEmpty()) {
            this.this$0.initialFavoriteIds.addAll(((ControlsControllerKt$createLoadDataObject$1) loadData).favoritesIds);
        }
        SecControlsFavoritingActivity secControlsFavoritingActivity = this.this$0;
        ControlsController.LoadData loadData2 = secControlsFavoritingActivity.loadData;
        if (loadData2 != null) {
            loadData = loadData2;
        }
        secControlsFavoritingActivity.loadData = loadData;
        loadData.getClass();
        secControlsFavoritingActivity.loadForComponent(loadData, false);
    }
}
