package com.android.systemui.controls.ui;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.StructureInfo;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SelectedItem {
    public static final Companion Companion = new Companion(null);
    public static final StructureItem EMPTY_SELECTION;
    public static final ComponentItem EMPTY_SELECTION_COMPONENT;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ComponentItem extends SelectedItem {
        public final CharSequence appName;
        public final ComponentInfo componentInfo;
        public final ComponentName componentName;
        public final boolean hasControls;
        public final CharSequence name;

        public ComponentItem(CharSequence charSequence, ComponentInfo componentInfo) {
            super(null);
            this.appName = charSequence;
            this.componentInfo = componentInfo;
            this.name = charSequence;
            List list = componentInfo.structureInfos;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (!((StructureInfo) obj).controls.isEmpty()) {
                    arrayList.add(obj);
                }
            }
            this.hasControls = (componentInfo.structureInfos.isEmpty() || arrayList.isEmpty()) ? false : true;
            this.componentName = this.componentInfo.componentName;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ComponentItem)) {
                return false;
            }
            ComponentItem componentItem = (ComponentItem) obj;
            return Intrinsics.areEqual(this.appName, componentItem.appName) && Intrinsics.areEqual(this.componentInfo, componentItem.componentInfo);
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final ComponentName getComponentName() {
            return this.componentName;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final CharSequence getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.componentInfo.hashCode() + (this.appName.hashCode() * 31);
        }

        public final String toString() {
            return "ComponentItem(appName=" + ((Object) this.appName) + ", componentInfo=" + this.componentInfo + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PanelItem extends SelectedItem {
        public final CharSequence appName;
        public final ComponentName componentName;
        public final CharSequence name;

        public PanelItem(CharSequence charSequence, ComponentName componentName) {
            super(null);
            this.appName = charSequence;
            this.componentName = componentName;
            this.name = charSequence;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PanelItem)) {
                return false;
            }
            PanelItem panelItem = (PanelItem) obj;
            return Intrinsics.areEqual(this.appName, panelItem.appName) && Intrinsics.areEqual(this.componentName, panelItem.componentName);
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final ComponentName getComponentName() {
            return this.componentName;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final CharSequence getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.componentName.hashCode() + (this.appName.hashCode() * 31);
        }

        public final String toString() {
            CharSequence charSequence = this.appName;
            return "PanelItem(appName=" + ((Object) charSequence) + ", componentName=" + this.componentName + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StructureItem extends SelectedItem {
        public final ComponentName componentName;
        public final CharSequence name;
        public final StructureInfo structure;

        public StructureItem(StructureInfo structureInfo) {
            super(null);
            this.structure = structureInfo;
            this.name = structureInfo.structure;
            structureInfo.controls.isEmpty();
            this.componentName = structureInfo.componentName;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof StructureItem) && Intrinsics.areEqual(this.structure, ((StructureItem) obj).structure);
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final ComponentName getComponentName() {
            return this.componentName;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public final CharSequence getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.structure.hashCode();
        }

        public final String toString() {
            return "StructureItem(structure=" + this.structure + ")";
        }
    }

    static {
        StructureInfo.Companion.getClass();
        EMPTY_SELECTION = new StructureItem(StructureInfo.EMPTY_STRUCTURE);
        ComponentInfo.Companion.getClass();
        EMPTY_SELECTION_COMPONENT = new ComponentItem("", ComponentInfo.EMPTY_COMPONENT_INFO);
    }

    public /* synthetic */ SelectedItem(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ComponentName getComponentName();

    public abstract CharSequence getName();

    private SelectedItem() {
    }
}
