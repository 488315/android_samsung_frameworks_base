package com.android.systemui.display.data;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface DisplayEvent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Added implements DisplayEvent {
        public final int displayId;

        public Added(int i) {
            this.displayId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Added) && this.displayId == ((Added) obj).displayId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.displayId, ")", new StringBuilder("Added(displayId="));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Changed implements DisplayEvent {
        public final int displayId;

        public Changed(int i) {
            this.displayId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Changed) && this.displayId == ((Changed) obj).displayId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.displayId, ")", new StringBuilder("Changed(displayId="));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Removed implements DisplayEvent {
        public final int displayId;

        public Removed(int i) {
            this.displayId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Removed) && this.displayId == ((Removed) obj).displayId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.displayId, ")", new StringBuilder("Removed(displayId="));
        }
    }
}
