package com.android.systemui.keyboard.shortcut.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public interface ShortcutHelperState {

    public final class Active implements ShortcutHelperState {
        public final int deviceId;

        public Active(int i) {
            this.deviceId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Active) && this.deviceId == ((Active) obj).deviceId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.deviceId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.deviceId, ")", new StringBuilder("Active(deviceId="));
        }
    }

    public final class Inactive implements ShortcutHelperState {
        public static final Inactive INSTANCE = new Inactive();

        private Inactive() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Inactive);
        }

        public final int hashCode() {
            return 1595827114;
        }

        public final String toString() {
            return "Inactive";
        }
    }
}
