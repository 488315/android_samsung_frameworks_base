package com.android.systemui.qs.tiles.impl.custom.data.entity;

import android.graphics.drawable.Icon;
import kotlin.jvm.internal.Intrinsics;

public interface CustomTileDefaults {

    public final class Error implements CustomTileDefaults {
        public static final Error INSTANCE = new Error();

        private Error() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Error);
        }

        public final int hashCode() {
            return 1602174935;
        }

        public final String toString() {
            return "Error";
        }
    }

    public final class Result implements CustomTileDefaults {
        public final Icon icon;
        public final CharSequence label;

        public Result(Icon icon, CharSequence charSequence) {
            this.icon = icon;
            this.label = charSequence;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Result)) {
                return false;
            }
            Result result = (Result) obj;
            return Intrinsics.areEqual(this.icon, result.icon) && Intrinsics.areEqual(this.label, result.label);
        }

        public final int hashCode() {
            return this.label.hashCode() + (this.icon.hashCode() * 31);
        }

        public final String toString() {
            return "Result(icon=" + this.icon + ", label=" + ((Object) this.label) + ")";
        }
    }
}
