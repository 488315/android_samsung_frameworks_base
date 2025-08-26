package com.android.systemui.volume.dialog.shared.model;

import com.android.systemui.bixby2.actionresult.ActionResults;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public interface VolumeDialogSafetyWarningModel {

    public final class Invisible implements VolumeDialogSafetyWarningModel {
        public static final Invisible INSTANCE = new Invisible();

        private Invisible() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Invisible);
        }

        public final int hashCode() {
            return -599633027;
        }

        public final String toString() {
            return ActionResults.RESULT_LAUNCHER_INVISIBLE;
        }
    }

    public final class Visible implements VolumeDialogSafetyWarningModel {
        public final int flags;

        public Visible(int i) {
            this.flags = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Visible) && this.flags == ((Visible) obj).flags;
        }

        public final int hashCode() {
            return Integer.hashCode(this.flags);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.flags, ")", new StringBuilder("Visible(flags="));
        }
    }
}
