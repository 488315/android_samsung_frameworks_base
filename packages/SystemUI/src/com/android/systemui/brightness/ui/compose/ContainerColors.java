package com.android.systemui.brightness.ui.compose;

import androidx.compose.ui.graphics.Color;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ContainerColors {
    public static final Companion Companion = new Companion(null);
    public final long idleColor;
    public final long mirrorColor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ ContainerColors(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ContainerColors)) {
            return false;
        }
        ContainerColors containerColors = (ContainerColors) obj;
        long j = containerColors.idleColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.idleColor, j) && ULong.m3427equalsimpl0(this.mirrorColor, containerColors.mirrorColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.mirrorColor) + (Long.hashCode(this.idleColor) * 31);
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("ContainerColors(idleColor=", Color.m462toStringimpl(this.idleColor), ", mirrorColor=", Color.m462toStringimpl(this.mirrorColor), ")");
    }

    private ContainerColors(long j, long j2) {
        this.idleColor = j;
        this.mirrorColor = j2;
    }
}
