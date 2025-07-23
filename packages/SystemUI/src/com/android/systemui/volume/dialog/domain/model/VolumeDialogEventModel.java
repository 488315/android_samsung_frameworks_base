package com.android.systemui.volume.dialog.domain.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.VolumeDialogController;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface VolumeDialogEventModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AccessibilityModeChanged implements VolumeDialogEventModel {
        public final boolean showA11yStream;

        public AccessibilityModeChanged(boolean z) {
            this.showA11yStream = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof AccessibilityModeChanged) && this.showA11yStream == ((AccessibilityModeChanged) obj).showA11yStream;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.showA11yStream);
        }

        public final String toString() {
            return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("AccessibilityModeChanged(showA11yStream="), this.showA11yStream, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DismissRequested implements VolumeDialogEventModel {
        public final int reason;

        public DismissRequested(int i) {
            this.reason = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DismissRequested) && this.reason == ((DismissRequested) obj).reason;
        }

        public final int hashCode() {
            return Integer.hashCode(this.reason);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.reason, ")", new StringBuilder("DismissRequested(reason="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LayoutDirectionChanged implements VolumeDialogEventModel {
        public final int layoutDirection;

        public LayoutDirectionChanged(int i) {
            this.layoutDirection = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof LayoutDirectionChanged) && this.layoutDirection == ((LayoutDirectionChanged) obj).layoutDirection;
        }

        public final int hashCode() {
            return Integer.hashCode(this.layoutDirection);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.layoutDirection, ")", new StringBuilder("LayoutDirectionChanged(layoutDirection="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ScreenOff implements VolumeDialogEventModel {
        public static final ScreenOff INSTANCE = new ScreenOff();

        private ScreenOff() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ScreenOff);
        }

        public final int hashCode() {
            return 941304516;
        }

        public final String toString() {
            return "ScreenOff";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ShowCsdWarning implements VolumeDialogEventModel {
        public final int csdWarning;
        public final int durationMs;

        public ShowCsdWarning(int i, int i2) {
            this.csdWarning = i;
            this.durationMs = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ShowCsdWarning)) {
                return false;
            }
            ShowCsdWarning showCsdWarning = (ShowCsdWarning) obj;
            return this.csdWarning == showCsdWarning.csdWarning && this.durationMs == showCsdWarning.durationMs;
        }

        public final int hashCode() {
            return Integer.hashCode(this.durationMs) + (Integer.hashCode(this.csdWarning) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ShowCsdWarning(csdWarning=");
            sb.append(this.csdWarning);
            sb.append(", durationMs=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.durationMs, ")", sb);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ShowRequested implements VolumeDialogEventModel {
        public final boolean keyguardLocked;
        public final int lockTaskModeState;
        public final int reason;

        public ShowRequested(int i, boolean z, int i2) {
            this.reason = i;
            this.keyguardLocked = z;
            this.lockTaskModeState = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ShowRequested)) {
                return false;
            }
            ShowRequested showRequested = (ShowRequested) obj;
            return this.reason == showRequested.reason && this.keyguardLocked == showRequested.keyguardLocked && this.lockTaskModeState == showRequested.lockTaskModeState;
        }

        public final int hashCode() {
            return Integer.hashCode(this.lockTaskModeState) + TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.reason) * 31, 31, this.keyguardLocked);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ShowRequested(reason=");
            sb.append(this.reason);
            sb.append(", keyguardLocked=");
            sb.append(this.keyguardLocked);
            sb.append(", lockTaskModeState=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.lockTaskModeState, ")", sb);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ShowSafetyWarning implements VolumeDialogEventModel {
        public final int flags;

        public ShowSafetyWarning(int i) {
            this.flags = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ShowSafetyWarning) && this.flags == ((ShowSafetyWarning) obj).flags;
        }

        public final int hashCode() {
            return Integer.hashCode(this.flags);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.flags, ")", new StringBuilder("ShowSafetyWarning(flags="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StateChanged implements VolumeDialogEventModel {
        public final VolumeDialogController.State state;

        public StateChanged(VolumeDialogController.State state) {
            this.state = state;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof StateChanged) && Intrinsics.areEqual(this.state, ((StateChanged) obj).state);
        }

        public final int hashCode() {
            return this.state.hashCode();
        }

        public final String toString() {
            return "StateChanged(state=" + this.state + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SubscribedToEvents implements VolumeDialogEventModel {
        public static final SubscribedToEvents INSTANCE = new SubscribedToEvents();

        private SubscribedToEvents() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof SubscribedToEvents);
        }

        public final int hashCode() {
            return 725608877;
        }

        public final String toString() {
            return "SubscribedToEvents";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class VolumeChangedFromKey implements VolumeDialogEventModel {
        public static final VolumeChangedFromKey INSTANCE = new VolumeChangedFromKey();

        private VolumeChangedFromKey() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof VolumeChangedFromKey);
        }

        public final int hashCode() {
            return -1496276294;
        }

        public final String toString() {
            return "VolumeChangedFromKey";
        }
    }
}
