package com.android.systemui.volume.dialog.ringer.ui.viewmodel;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class RingerViewModel {
    public final List availableButtons;
    public final int currentButtonIndex;
    public final RingerDrawerState drawerState;
    public final RingerButtonViewModel selectedButton;

    public RingerViewModel(List<RingerButtonViewModel> list, int i, RingerButtonViewModel ringerButtonViewModel, RingerDrawerState ringerDrawerState) {
        this.availableButtons = list;
        this.currentButtonIndex = i;
        this.selectedButton = ringerButtonViewModel;
        this.drawerState = ringerDrawerState;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RingerViewModel)) {
            return false;
        }
        RingerViewModel ringerViewModel = (RingerViewModel) obj;
        return Intrinsics.areEqual(this.availableButtons, ringerViewModel.availableButtons) && this.currentButtonIndex == ringerViewModel.currentButtonIndex && Intrinsics.areEqual(this.selectedButton, ringerViewModel.selectedButton) && Intrinsics.areEqual(this.drawerState, ringerViewModel.drawerState);
    }

    public final int hashCode() {
        return this.drawerState.hashCode() + ((this.selectedButton.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.currentButtonIndex, this.availableButtons.hashCode() * 31, 31)) * 31);
    }

    public final String toString() {
        return "RingerViewModel(availableButtons=" + this.availableButtons + ", currentButtonIndex=" + this.currentButtonIndex + ", selectedButton=" + this.selectedButton + ", drawerState=" + this.drawerState + ")";
    }
}
