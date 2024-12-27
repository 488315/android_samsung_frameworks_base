package com.android.systemui.controls.management;

import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerKt$createLoadDataObject$1;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        secControlsFavoritingActivity.loadForComponent(loadData, false);
    }
}
