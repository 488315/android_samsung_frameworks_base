package com.android.systemui.volume.dialog.shared.model;

import com.android.systemui.bixby2.actionresult.ActionResults;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;

/* loaded from: classes3.dex */
public interface VolumeDialogCsdWarningModel {

    public final class Invisible implements VolumeDialogCsdWarningModel {
        public static final Invisible INSTANCE = new Invisible();

        private Invisible() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Invisible);
        }

        public final int hashCode() {
            return -883243775;
        }

        public final String toString() {
            return ActionResults.RESULT_LAUNCHER_INVISIBLE;
        }
    }

    public final class Visible implements VolumeDialogCsdWarningModel {
        public final long duration;
        public final int warning;

        public /* synthetic */ Visible(int i, long j, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, j);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Visible)) {
                return false;
            }
            Visible visible = (Visible) obj;
            if (this.warning != visible.warning) {
                return false;
            }
            Duration.Companion companion = Duration.Companion;
            return this.duration == visible.duration;
        }

        public final int hashCode() {
            int iHashCode = Integer.hashCode(this.warning) * 31;
            Duration.Companion companion = Duration.Companion;
            return Long.hashCode(this.duration) + iHashCode;
        }

        public final String toString() {
            return "Visible(warning=" + this.warning + ", duration=" + Duration.m3465toStringimpl(this.duration) + ")";
        }

        private Visible(int i, long j) {
            this.warning = i;
            this.duration = j;
        }
    }
}
