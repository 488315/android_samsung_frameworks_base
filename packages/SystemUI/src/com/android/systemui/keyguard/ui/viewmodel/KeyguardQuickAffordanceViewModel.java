package com.android.systemui.keyguard.ui.viewmodel;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class KeyguardQuickAffordanceViewModel {
    public final boolean animateReveal;
    public final String configKey;
    public final Icon icon;
    public final boolean isActivated;
    public final boolean isClickable;
    public final boolean isDimmed;
    public final boolean isSelected;
    public final boolean isVisible;
    public final Function1 onClicked;
    public final String slotId;
    public final boolean useLongPress;

    public final class OnClickedParameters {
        public final String configKey;
        public final Expandable expandable;
        public final String slotId;

        public OnClickedParameters(String str, Expandable expandable, String str2) {
            this.configKey = str;
            this.expandable = expandable;
            this.slotId = str2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OnClickedParameters)) {
                return false;
            }
            OnClickedParameters onClickedParameters = (OnClickedParameters) obj;
            return Intrinsics.areEqual(this.configKey, onClickedParameters.configKey) && Intrinsics.areEqual(this.expandable, onClickedParameters.expandable) && Intrinsics.areEqual(this.slotId, onClickedParameters.slotId);
        }

        public final int hashCode() {
            int hashCode = this.configKey.hashCode() * 31;
            Expandable expandable = this.expandable;
            return this.slotId.hashCode() + ((hashCode + (expandable == null ? 0 : expandable.hashCode())) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("OnClickedParameters(configKey=");
            sb.append(this.configKey);
            sb.append(", expandable=");
            sb.append(this.expandable);
            sb.append(", slotId=");
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.slotId, ")");
        }
    }

    public KeyguardQuickAffordanceViewModel(String str, boolean z, boolean z2, Icon icon, Function1 function1, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, String str2) {
        this.configKey = str;
        this.isVisible = z;
        this.animateReveal = z2;
        this.icon = icon;
        this.onClicked = function1;
        this.isClickable = z3;
        this.isActivated = z4;
        this.isSelected = z5;
        this.useLongPress = z6;
        this.isDimmed = z7;
        this.slotId = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardQuickAffordanceViewModel)) {
            return false;
        }
        KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel = (KeyguardQuickAffordanceViewModel) obj;
        return Intrinsics.areEqual(this.configKey, keyguardQuickAffordanceViewModel.configKey) && this.isVisible == keyguardQuickAffordanceViewModel.isVisible && this.animateReveal == keyguardQuickAffordanceViewModel.animateReveal && Intrinsics.areEqual(this.icon, keyguardQuickAffordanceViewModel.icon) && Intrinsics.areEqual(this.onClicked, keyguardQuickAffordanceViewModel.onClicked) && this.isClickable == keyguardQuickAffordanceViewModel.isClickable && this.isActivated == keyguardQuickAffordanceViewModel.isActivated && this.isSelected == keyguardQuickAffordanceViewModel.isSelected && this.useLongPress == keyguardQuickAffordanceViewModel.useLongPress && this.isDimmed == keyguardQuickAffordanceViewModel.isDimmed && Intrinsics.areEqual(this.slotId, keyguardQuickAffordanceViewModel.slotId);
    }

    public final int hashCode() {
        String str = this.configKey;
        return this.slotId.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.onClicked.hashCode() + ((this.icon.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((str == null ? 0 : str.hashCode()) * 31, 31, this.isVisible), 31, this.animateReveal)) * 31)) * 31, 31, this.isClickable), 31, this.isActivated), 31, this.isSelected), 31, this.useLongPress), 31, this.isDimmed);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("KeyguardQuickAffordanceViewModel(configKey=");
        sb.append(this.configKey);
        sb.append(", isVisible=");
        sb.append(this.isVisible);
        sb.append(", animateReveal=");
        sb.append(this.animateReveal);
        sb.append(", icon=");
        sb.append(this.icon);
        sb.append(", onClicked=");
        sb.append(this.onClicked);
        sb.append(", isClickable=");
        sb.append(this.isClickable);
        sb.append(", isActivated=");
        sb.append(this.isActivated);
        sb.append(", isSelected=");
        sb.append(this.isSelected);
        sb.append(", useLongPress=");
        sb.append(this.useLongPress);
        sb.append(", isDimmed=");
        sb.append(this.isDimmed);
        sb.append(", slotId=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.slotId, ")");
    }

    public /* synthetic */ KeyguardQuickAffordanceViewModel(String str, boolean z, boolean z2, Icon icon, Function1 function1, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2, (i & 8) != 0 ? new Icon.Resource(0, null) : icon, (i & 16) != 0 ? new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel.1
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Unit.INSTANCE;
            }
        } : function1, (i & 32) != 0 ? false : z3, (i & 64) != 0 ? false : z4, (i & 128) != 0 ? false : z5, (i & 256) != 0 ? false : z6, (i & 512) != 0 ? false : z7, str2);
    }
}
