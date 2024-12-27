package com.android.systemui.controls.controller;

import android.content.ComponentName;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ComponentInfo {
    public static final Companion Companion = new Companion(null);
    public static final ComponentName EMPTY_COMPONENT;
    public static final ComponentInfo EMPTY_COMPONENT_INFO;
    public final ComponentName componentName;
    public final List structureInfos;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        ComponentName componentName = new ComponentName("", "");
        EMPTY_COMPONENT = componentName;
        EMPTY_COMPONENT_INFO = new ComponentInfo(componentName, new ArrayList());
    }

    public ComponentInfo(ComponentName componentName, List<StructureInfo> list) {
        this.componentName = componentName;
        this.structureInfos = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ComponentInfo)) {
            return false;
        }
        ComponentInfo componentInfo = (ComponentInfo) obj;
        return Intrinsics.areEqual(this.componentName, componentInfo.componentName) && Intrinsics.areEqual(this.structureInfos, componentInfo.structureInfos);
    }

    public final int hashCode() {
        return this.structureInfos.hashCode() + (this.componentName.hashCode() * 31);
    }

    public final String toString() {
        return "ComponentInfo(componentName=" + this.componentName + ", structureInfos=" + this.structureInfos + ")";
    }
}
