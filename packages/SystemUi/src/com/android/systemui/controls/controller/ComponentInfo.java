package com.android.systemui.controls.controller;

import android.content.ComponentName;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComponentInfo {
    public static final Companion Companion = new Companion(null);
    public static final ComponentName EMPTY_COMPONENT;
    public static final ComponentInfo EMPTY_COMPONENT_INFO;
    public final ComponentName componentName;
    public final List structureInfos;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
