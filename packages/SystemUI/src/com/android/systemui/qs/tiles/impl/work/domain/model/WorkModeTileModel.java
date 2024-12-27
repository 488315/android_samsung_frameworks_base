package com.android.systemui.qs.tiles.impl.work.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface WorkModeTileModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class HasActiveProfile implements WorkModeTileModel {
        public final boolean isEnabled;

        public HasActiveProfile(boolean z) {
            this.isEnabled = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof HasActiveProfile) && this.isEnabled == ((HasActiveProfile) obj).isEnabled;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isEnabled);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("HasActiveProfile(isEnabled="), this.isEnabled, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NoActiveProfile implements WorkModeTileModel {
        public static final NoActiveProfile INSTANCE = new NoActiveProfile();

        private NoActiveProfile() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NoActiveProfile);
        }

        public final int hashCode() {
            return -872501935;
        }

        public final String toString() {
            return "NoActiveProfile";
        }
    }
}
