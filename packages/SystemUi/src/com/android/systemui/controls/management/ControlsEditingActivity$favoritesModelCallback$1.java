package com.android.systemui.controls.management;

import android.view.View;
import com.android.systemui.controls.management.FavoritesModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsEditingActivity$favoritesModelCallback$1 implements FavoritesModel.FavoritesModelCallback {
    public final /* synthetic */ ControlsEditingActivity this$0;

    public ControlsEditingActivity$favoritesModelCallback$1(ControlsEditingActivity controlsEditingActivity) {
        this.this$0 = controlsEditingActivity;
    }

    @Override // com.android.systemui.controls.management.ControlsModel.ControlsModelCallback
    public final void onFirstChange() {
        View view = this.this$0.saveButton;
        if (view == null) {
            view = null;
        }
        view.setEnabled(true);
    }

    @Override // com.android.systemui.controls.management.ControlsModel.ControlsModelCallback
    public final void onChange() {
    }
}
