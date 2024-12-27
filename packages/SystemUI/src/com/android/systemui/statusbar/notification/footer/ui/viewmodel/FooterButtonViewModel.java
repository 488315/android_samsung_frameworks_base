package com.android.systemui.statusbar.notification.footer.ui.viewmodel;

import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;

public final class FooterButtonViewModel {
    public final Flow accessibilityDescriptionId;
    public final Flow isVisible;
    public final Flow labelId;

    public FooterButtonViewModel(Flow flow, Flow flow2, Flow flow3) {
        this.labelId = flow;
        this.accessibilityDescriptionId = flow2;
        this.isVisible = flow3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FooterButtonViewModel)) {
            return false;
        }
        FooterButtonViewModel footerButtonViewModel = (FooterButtonViewModel) obj;
        return Intrinsics.areEqual(this.labelId, footerButtonViewModel.labelId) && Intrinsics.areEqual(this.accessibilityDescriptionId, footerButtonViewModel.accessibilityDescriptionId) && Intrinsics.areEqual(this.isVisible, footerButtonViewModel.isVisible);
    }

    public final int hashCode() {
        return this.isVisible.hashCode() + ((this.accessibilityDescriptionId.hashCode() + (this.labelId.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "FooterButtonViewModel(labelId=" + this.labelId + ", accessibilityDescriptionId=" + this.accessibilityDescriptionId + ", isVisible=" + this.isVisible + ")";
    }
}
