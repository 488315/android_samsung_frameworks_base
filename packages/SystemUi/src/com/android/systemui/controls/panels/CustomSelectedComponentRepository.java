package com.android.systemui.controls.panels;

import android.content.ComponentName;
import com.android.systemui.controls.p005ui.SelectedItem;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface CustomSelectedComponentRepository {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CustomSelectedComponent {
        public final ComponentName componentName;
        public final boolean isPanel;
        public final String name;

        public CustomSelectedComponent(String str, ComponentName componentName, boolean z) {
            this.name = str;
            this.componentName = componentName;
            this.isPanel = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CustomSelectedComponent)) {
                return false;
            }
            CustomSelectedComponent customSelectedComponent = (CustomSelectedComponent) obj;
            return Intrinsics.areEqual(this.name, customSelectedComponent.name) && Intrinsics.areEqual(this.componentName, customSelectedComponent.componentName) && this.isPanel == customSelectedComponent.isPanel;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int hashCode = this.name.hashCode() * 31;
            ComponentName componentName = this.componentName;
            int hashCode2 = (hashCode + (componentName == null ? 0 : componentName.hashCode())) * 31;
            boolean z = this.isPanel;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return hashCode2 + i;
        }

        public final String toString() {
            return "CustomSelectedComponent{" + this.name + ", isPanel = " + this.isPanel + ", componentName = " + this.componentName + "}";
        }

        public CustomSelectedComponent(SelectedItem selectedItem) {
            this(selectedItem.getName().toString(), selectedItem.getComponentName(), selectedItem instanceof SelectedItem.PanelItem);
        }
    }
}
