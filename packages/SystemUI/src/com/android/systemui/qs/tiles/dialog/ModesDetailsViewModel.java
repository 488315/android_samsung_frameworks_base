package com.android.systemui.qs.tiles.dialog;

import com.android.systemui.plugins.qs.TileDetailsViewModel;
import com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ModesDetailsViewModel implements TileDetailsViewModel {
    public final Function0 onSettingsClick;

    public ModesDetailsViewModel(Function0 function0, ModesDialogViewModel modesDialogViewModel) {
        this.onSettingsClick = function0;
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final void clickOnSettingsButton() {
        this.onSettingsClick.invoke();
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final String getSubTitle() {
        return "Silences interruptions from people and apps in different circumstances";
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final String getTitle() {
        return "Modes";
    }
}
