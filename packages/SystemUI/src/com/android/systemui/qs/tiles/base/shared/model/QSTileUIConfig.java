package com.android.systemui.qs.tiles.base.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface QSTileUIConfig {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Empty implements QSTileUIConfig {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Empty);
        }

        @Override // com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig
        public final int getIconRes() {
            return 0;
        }

        @Override // com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig
        public final int getLabelRes() {
            return 0;
        }

        public final int hashCode() {
            return 968923756;
        }

        public final String toString() {
            return "Empty";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Resource implements QSTileUIConfig {
        public final int iconRes;
        public final int labelRes;

        public Resource(int i, int i2) {
            this.iconRes = i;
            this.labelRes = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Resource)) {
                return false;
            }
            Resource resource = (Resource) obj;
            return this.iconRes == resource.iconRes && this.labelRes == resource.labelRes;
        }

        @Override // com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig
        public final int getIconRes() {
            return this.iconRes;
        }

        @Override // com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig
        public final int getLabelRes() {
            return this.labelRes;
        }

        public final int hashCode() {
            return Integer.hashCode(this.labelRes) + (Integer.hashCode(this.iconRes) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Resource(iconRes=");
            sb.append(this.iconRes);
            sb.append(", labelRes=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.labelRes, ")", sb);
        }
    }

    int getIconRes();

    int getLabelRes();
}
