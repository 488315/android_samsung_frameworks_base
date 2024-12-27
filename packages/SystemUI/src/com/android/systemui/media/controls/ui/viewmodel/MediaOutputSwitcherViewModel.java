package com.android.systemui.media.controls.ui.viewmodel;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaOutputSwitcherViewModel {
    public final float alpha;
    public final Icon deviceIcon;
    public final CharSequence deviceString;
    public final boolean isCurrentBroadcastApp;
    public final boolean isIntentValid;
    public final boolean isTapEnabled;
    public final boolean isVisible;
    public final Function1 onClicked;

    public MediaOutputSwitcherViewModel(boolean z, CharSequence charSequence, Icon icon, boolean z2, boolean z3, float f, boolean z4, Function1 function1) {
        this.isTapEnabled = z;
        this.deviceString = charSequence;
        this.deviceIcon = icon;
        this.isCurrentBroadcastApp = z2;
        this.isIntentValid = z3;
        this.alpha = f;
        this.isVisible = z4;
        this.onClicked = function1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaOutputSwitcherViewModel)) {
            return false;
        }
        MediaOutputSwitcherViewModel mediaOutputSwitcherViewModel = (MediaOutputSwitcherViewModel) obj;
        return this.isTapEnabled == mediaOutputSwitcherViewModel.isTapEnabled && Intrinsics.areEqual(this.deviceString, mediaOutputSwitcherViewModel.deviceString) && Intrinsics.areEqual(this.deviceIcon, mediaOutputSwitcherViewModel.deviceIcon) && this.isCurrentBroadcastApp == mediaOutputSwitcherViewModel.isCurrentBroadcastApp && this.isIntentValid == mediaOutputSwitcherViewModel.isIntentValid && Float.compare(this.alpha, mediaOutputSwitcherViewModel.alpha) == 0 && this.isVisible == mediaOutputSwitcherViewModel.isVisible && Intrinsics.areEqual(this.onClicked, mediaOutputSwitcherViewModel.onClicked);
    }

    public final int hashCode() {
        return this.onClicked.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.alpha, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.deviceIcon.hashCode() + ControlInfo$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.isTapEnabled) * 31, 31, this.deviceString)) * 31, 31, this.isCurrentBroadcastApp), 31, this.isIntentValid), 31), 31, this.isVisible);
    }

    public final String toString() {
        return "MediaOutputSwitcherViewModel(isTapEnabled=" + this.isTapEnabled + ", deviceString=" + ((Object) this.deviceString) + ", deviceIcon=" + this.deviceIcon + ", isCurrentBroadcastApp=" + this.isCurrentBroadcastApp + ", isIntentValid=" + this.isIntentValid + ", alpha=" + this.alpha + ", isVisible=" + this.isVisible + ", onClicked=" + this.onClicked + ")";
    }
}
