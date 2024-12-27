package com.android.systemui.volume.panel.ui.layout;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.volume.panel.ui.viewmodel.ComponentState;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ComponentsLayout {
    public final ComponentState bottomBarComponent;
    public final List contentComponents;
    public final List footerComponents;
    public final List headerComponents;

    public ComponentsLayout(List<ComponentState> list, List<ComponentState> list2, List<ComponentState> list3, ComponentState componentState) {
        this.headerComponents = list;
        this.contentComponents = list2;
        this.footerComponents = list3;
        this.bottomBarComponent = componentState;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ComponentsLayout)) {
            return false;
        }
        ComponentsLayout componentsLayout = (ComponentsLayout) obj;
        return Intrinsics.areEqual(this.headerComponents, componentsLayout.headerComponents) && Intrinsics.areEqual(this.contentComponents, componentsLayout.contentComponents) && Intrinsics.areEqual(this.footerComponents, componentsLayout.footerComponents) && Intrinsics.areEqual(this.bottomBarComponent, componentsLayout.bottomBarComponent);
    }

    public final int hashCode() {
        return this.bottomBarComponent.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.footerComponents, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contentComponents, this.headerComponents.hashCode() * 31, 31), 31);
    }

    public final String toString() {
        return "ComponentsLayout(headerComponents=" + this.headerComponents + ", contentComponents=" + this.contentComponents + ", footerComponents=" + this.footerComponents + ", bottomBarComponent=" + this.bottomBarComponent + ")";
    }
}
