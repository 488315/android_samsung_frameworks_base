package com.android.settingslib.volume.data.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public interface VolumeControllerEvent {

    public final class Dismiss implements VolumeControllerEvent {
        public static final Dismiss INSTANCE = new Dismiss();

        private Dismiss() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Dismiss);
        }

        public final int hashCode() {
            return 524319987;
        }

        public final String toString() {
            return "Dismiss";
        }
    }

    public final class DisplayCsdWarning implements VolumeControllerEvent {
        public final int csdWarning;
        public final int displayDurationMs;

        public DisplayCsdWarning(int i, int i2) {
            this.csdWarning = i;
            this.displayDurationMs = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DisplayCsdWarning)) {
                return false;
            }
            DisplayCsdWarning displayCsdWarning = (DisplayCsdWarning) obj;
            return this.csdWarning == displayCsdWarning.csdWarning && this.displayDurationMs == displayCsdWarning.displayDurationMs;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayDurationMs) + (Integer.hashCode(this.csdWarning) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DisplayCsdWarning(csdWarning=");
            sb.append(this.csdWarning);
            sb.append(", displayDurationMs=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.displayDurationMs, ")", sb);
        }
    }

    public final class DisplaySafeVolumeWarning implements VolumeControllerEvent {
        public final int flags;

        public DisplaySafeVolumeWarning(int i) {
            this.flags = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DisplaySafeVolumeWarning) && this.flags == ((DisplaySafeVolumeWarning) obj).flags;
        }

        public final int hashCode() {
            return Integer.hashCode(this.flags);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.flags, ")", new StringBuilder("DisplaySafeVolumeWarning(flags="));
        }
    }

    public final class MasterMuteChanged implements VolumeControllerEvent {
        public final int flags;

        public MasterMuteChanged(int i) {
            this.flags = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof MasterMuteChanged) && this.flags == ((MasterMuteChanged) obj).flags;
        }

        public final int hashCode() {
            return Integer.hashCode(this.flags);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.flags, ")", new StringBuilder("MasterMuteChanged(flags="));
        }
    }

    public final class SetA11yMode implements VolumeControllerEvent {
        public final int mode;

        public SetA11yMode(int i) {
            this.mode = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SetA11yMode) && this.mode == ((SetA11yMode) obj).mode;
        }

        public final int hashCode() {
            return Integer.hashCode(this.mode);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.mode, ")", new StringBuilder("SetA11yMode(mode="));
        }
    }

    public final class SetLayoutDirection implements VolumeControllerEvent {
        public final int layoutDirection;

        public SetLayoutDirection(int i) {
            this.layoutDirection = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SetLayoutDirection) && this.layoutDirection == ((SetLayoutDirection) obj).layoutDirection;
        }

        public final int hashCode() {
            return Integer.hashCode(this.layoutDirection);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.layoutDirection, ")", new StringBuilder("SetLayoutDirection(layoutDirection="));
        }
    }

    public final class VolumeChanged implements VolumeControllerEvent {
        public final int flags;
        public final int streamType;

        public VolumeChanged(int i, int i2) {
            this.streamType = i;
            this.flags = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof VolumeChanged)) {
                return false;
            }
            VolumeChanged volumeChanged = (VolumeChanged) obj;
            return this.streamType == volumeChanged.streamType && this.flags == volumeChanged.flags;
        }

        public final int hashCode() {
            return Integer.hashCode(this.flags) + (Integer.hashCode(this.streamType) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("VolumeChanged(streamType=");
            sb.append(this.streamType);
            sb.append(", flags=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.flags, ")", sb);
        }
    }

    public final class displayVolumeLimiterToast implements VolumeControllerEvent {
        public static final displayVolumeLimiterToast INSTANCE = new displayVolumeLimiterToast();

        private displayVolumeLimiterToast() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof displayVolumeLimiterToast);
        }

        public final int hashCode() {
            return -1072895932;
        }

        public final String toString() {
            return "displayVolumeLimiterToast";
        }
    }
}
