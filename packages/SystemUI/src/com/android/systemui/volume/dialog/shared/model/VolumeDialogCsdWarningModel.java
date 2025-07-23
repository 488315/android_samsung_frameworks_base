package com.android.systemui.volume.dialog.shared.model;

import com.android.systemui.bixby2.actionresult.ActionResults;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface VolumeDialogCsdWarningModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode = Integer.hashCode(this.warning) * 31;
            Duration.Companion companion = Duration.Companion;
            return Long.hashCode(this.duration) + hashCode;
        }

        public final String toString() {
            return "Visible(warning=" + this.warning + ", duration=" + Duration.m3446toStringimpl(this.duration) + ")";
        }

        private Visible(int i, long j) {
            this.warning = i;
            this.duration = j;
        }
    }
}
