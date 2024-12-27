package com.android.systemui.qs.tiles.impl.battery.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface BatterySaverTileModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Extreme implements BatterySaverTileModel {
        public final boolean isExtremeSaving;
        public final boolean isPluggedIn;
        public final boolean isPowerSaving;

        public Extreme(boolean z, boolean z2, boolean z3) {
            this.isPluggedIn = z;
            this.isPowerSaving = z2;
            this.isExtremeSaving = z3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Extreme)) {
                return false;
            }
            Extreme extreme = (Extreme) obj;
            return this.isPluggedIn == extreme.isPluggedIn && this.isPowerSaving == extreme.isPowerSaving && this.isExtremeSaving == extreme.isExtremeSaving;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isExtremeSaving) + TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.isPluggedIn) * 31, 31, this.isPowerSaving);
        }

        @Override // com.android.systemui.qs.tiles.impl.battery.domain.model.BatterySaverTileModel
        public final boolean isPluggedIn() {
            return this.isPluggedIn;
        }

        @Override // com.android.systemui.qs.tiles.impl.battery.domain.model.BatterySaverTileModel
        public final boolean isPowerSaving() {
            return this.isPowerSaving;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Extreme(isPluggedIn=");
            sb.append(this.isPluggedIn);
            sb.append(", isPowerSaving=");
            sb.append(this.isPowerSaving);
            sb.append(", isExtremeSaving=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isExtremeSaving, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Standard implements BatterySaverTileModel {
        public final boolean isPluggedIn;
        public final boolean isPowerSaving;

        public Standard(boolean z, boolean z2) {
            this.isPluggedIn = z;
            this.isPowerSaving = z2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Standard)) {
                return false;
            }
            Standard standard = (Standard) obj;
            return this.isPluggedIn == standard.isPluggedIn && this.isPowerSaving == standard.isPowerSaving;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isPowerSaving) + (Boolean.hashCode(this.isPluggedIn) * 31);
        }

        @Override // com.android.systemui.qs.tiles.impl.battery.domain.model.BatterySaverTileModel
        public final boolean isPluggedIn() {
            return this.isPluggedIn;
        }

        @Override // com.android.systemui.qs.tiles.impl.battery.domain.model.BatterySaverTileModel
        public final boolean isPowerSaving() {
            return this.isPowerSaving;
        }

        public final String toString() {
            return "Standard(isPluggedIn=" + this.isPluggedIn + ", isPowerSaving=" + this.isPowerSaving + ")";
        }
    }

    boolean isPluggedIn();

    boolean isPowerSaving();
}
