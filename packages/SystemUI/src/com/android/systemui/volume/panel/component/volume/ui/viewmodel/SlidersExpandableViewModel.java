package com.android.systemui.volume.panel.component.volume.ui.viewmodel;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface SlidersExpandableViewModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Expandable implements SlidersExpandableViewModel {
        public final boolean isExpanded;

        public Expandable(boolean z) {
            this.isExpanded = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Expandable) && this.isExpanded == ((Expandable) obj).isExpanded;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isExpanded);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("Expandable(isExpanded="), this.isExpanded, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Fixed implements SlidersExpandableViewModel {
        public static final Fixed INSTANCE = new Fixed();

        private Fixed() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Fixed);
        }

        public final int hashCode() {
            return -1830679804;
        }

        public final String toString() {
            return "Fixed";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Unavailable implements SlidersExpandableViewModel {
        public static final Unavailable INSTANCE = new Unavailable();

        private Unavailable() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Unavailable);
        }

        public final int hashCode() {
            return 1187254240;
        }

        public final String toString() {
            return "Unavailable";
        }
    }
}
