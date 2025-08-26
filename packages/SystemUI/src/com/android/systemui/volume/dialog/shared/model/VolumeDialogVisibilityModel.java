package com.android.systemui.volume.dialog.shared.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public interface VolumeDialogVisibilityModel {

    public final class Dismissed implements Invisible {
        public final int reason;

        public Dismissed(int i) {
            this.reason = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Dismissed) && this.reason == ((Dismissed) obj).reason;
        }

        public final int hashCode() {
            return Integer.hashCode(this.reason);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.reason, ")", new StringBuilder("Dismissed(reason="));
        }
    }

    public interface Invisible extends VolumeDialogVisibilityModel {
        public static final Companion Companion = Companion.$$INSTANCE;

        public final class Companion implements Invisible {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }

    public final class Visible implements VolumeDialogVisibilityModel {
        public final boolean keyguardLocked;
        public final int lockTaskModeState;
        public final int reason;

        public Visible(int i, boolean z, int i2) {
            this.reason = i;
            this.keyguardLocked = z;
            this.lockTaskModeState = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Visible)) {
                return false;
            }
            Visible visible = (Visible) obj;
            return this.reason == visible.reason && this.keyguardLocked == visible.keyguardLocked && this.lockTaskModeState == visible.lockTaskModeState;
        }

        public final int hashCode() {
            return Integer.hashCode(this.lockTaskModeState) + TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.reason) * 31, 31, this.keyguardLocked);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Visible(reason=");
            sb.append(this.reason);
            sb.append(", keyguardLocked=");
            sb.append(this.keyguardLocked);
            sb.append(", lockTaskModeState=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.lockTaskModeState, ")", sb);
        }
    }
}
