package com.android.systemui.statusbar.pipeline.shared.ui.model;

import com.android.systemui.common.shared.model.Icon;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public interface InternetTileIconModel {

    public final class Cellular implements InternetTileIconModel {
        public final int level;

        public Cellular(int i) {
            this.level = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Cellular) && this.level == ((Cellular) obj).level;
        }

        public final int hashCode() {
            return Integer.hashCode(this.level);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.level, ")", new StringBuilder("Cellular(level="));
        }
    }

    public final class ResourceId implements InternetTileIconModel {
        public final int resId;

        public ResourceId(int i) {
            this.resId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ResourceId) && this.resId == ((ResourceId) obj).resId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.resId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.resId, ")", new StringBuilder("ResourceId(resId="));
        }
    }

    public final class Satellite implements InternetTileIconModel {
        public final Icon.Resource resourceIcon;

        public Satellite(Icon.Resource resource) {
            this.resourceIcon = resource;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Satellite) && Intrinsics.areEqual(this.resourceIcon, ((Satellite) obj).resourceIcon);
        }

        public final int hashCode() {
            return this.resourceIcon.hashCode();
        }

        public final String toString() {
            return "Satellite(resourceIcon=" + this.resourceIcon + ")";
        }
    }
}
