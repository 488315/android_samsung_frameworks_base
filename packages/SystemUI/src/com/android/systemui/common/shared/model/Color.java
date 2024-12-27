package com.android.systemui.common.shared.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

public interface Color {

    public final class Attribute implements Color {
        public final int attribute;

        public Attribute(int i) {
            this.attribute = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Attribute) && this.attribute == ((Attribute) obj).attribute;
        }

        public final int hashCode() {
            return Integer.hashCode(this.attribute);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.attribute, ")", new StringBuilder("Attribute(attribute="));
        }
    }

    public final class Loaded implements Color {
        public final int color;

        public Loaded(int i) {
            this.color = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Loaded) && this.color == ((Loaded) obj).color;
        }

        public final int hashCode() {
            return Integer.hashCode(this.color);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.color, ")", new StringBuilder("Loaded(color="));
        }
    }
}
