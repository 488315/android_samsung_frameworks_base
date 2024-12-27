package com.android.systemui.volume.panel.ui.viewmodel;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.volume.panel.shared.model.VolumePanelUiComponent;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ComponentState {
    public final VolumePanelUiComponent component;
    public final boolean isVisible;
    public final String key;

    public ComponentState(String str, VolumePanelUiComponent volumePanelUiComponent, boolean z) {
        this.key = str;
        this.component = volumePanelUiComponent;
        this.isVisible = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ComponentState)) {
            return false;
        }
        ComponentState componentState = (ComponentState) obj;
        return Intrinsics.areEqual(this.key, componentState.key) && Intrinsics.areEqual(this.component, componentState.component) && this.isVisible == componentState.isVisible;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isVisible) + ((this.component.hashCode() + (this.key.hashCode() * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ComponentState(key=");
        sb.append(this.key);
        sb.append(", component=");
        sb.append(this.component);
        sb.append(", isVisible=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isVisible, ")");
    }
}
