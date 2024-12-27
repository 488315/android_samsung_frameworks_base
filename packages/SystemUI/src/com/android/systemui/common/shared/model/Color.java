package com.android.systemui.common.shared.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface Color {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
