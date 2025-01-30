package com.android.systemui.controls.panels;

import android.content.ComponentName;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.controls.p005ui.SelectedItem;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface SelectedComponentRepository {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            StringBuilder sb = new StringBuilder("SelectedComponent(name=");
            sb.append(this.name);
            sb.append(", componentName=");
            sb.append(this.componentName);
            sb.append(", isPanel=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isPanel, ")");
        }

        public SelectedComponent(SelectedItem selectedItem) {
            this(selectedItem.getName().toString(), selectedItem.getComponentName(), selectedItem instanceof SelectedItem.PanelItem);
        }
    }
}
