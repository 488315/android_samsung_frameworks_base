package com.android.systemui.controls.panels;

import android.content.ComponentName;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.controls.ui.SelectedItem;
import kotlin.jvm.internal.Intrinsics;

public interface SelectedComponentRepository {

    public final class SelectedComponent {
        public final ComponentName componentName;
        public final boolean isPanel;
        public final String name;

        public SelectedComponent(String str, ComponentName componentName, boolean z) {
            this.name = str;
            this.componentName = componentName;
            this.isPanel = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SelectedComponent)) {
                return false;
            }
            SelectedComponent selectedComponent = (SelectedComponent) obj;
            return Intrinsics.areEqual(this.name, selectedComponent.name) && Intrinsics.areEqual(this.componentName, selectedComponent.componentName) && this.isPanel == selectedComponent.isPanel;
        }

        public final int hashCode() {
            int hashCode = this.name.hashCode() * 31;
            ComponentName componentName = this.componentName;
            return Boolean.hashCode(this.isPanel) + ((hashCode + (componentName == null ? 0 : componentName.hashCode())) * 31);
        }

        public final String toString() {
            ComponentName componentName = this.componentName;
            StringBuilder sb = new StringBuilder("SelectedComponent(name=");
            sb.append(this.name);
            sb.append(", componentName=");
            sb.append(componentName);
            sb.append(", isPanel=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isPanel, ")");
        }

        public SelectedComponent(SelectedItem selectedItem) {
            this(selectedItem.getName().toString(), selectedItem.getComponentName(), selectedItem instanceof SelectedItem.PanelItem);
        }
    }
}
